/*
 * Copyright 2018-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ktorm.ksp.compiler.generator

import com.google.devtools.ksp.isAbstract
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import org.ktorm.entity.Entity
import org.ktorm.ksp.annotation.Undefined
import org.ktorm.ksp.compiler.util.Reflections
import org.ktorm.ksp.compiler.util._type
import org.ktorm.ksp.compiler.util.isInline
import org.ktorm.ksp.spi.TableMetadata

internal object PseudoConstructorFunctionGenerator {

    fun generate(table: TableMetadata): FunSpec {
        val kdoc = "" +
            "Create an entity of [%L] and specify the initial values for each property, " +
            "properties that doesn't have an initial value will leave unassigned. "

        return FunSpec.builder(table.entityClass.simpleName.asString())
            .addKdoc(kdoc, table.entityClass.simpleName.asString())
            .addParameters(buildParameters(table).asIterable())
            .returns(table.entityClass.toClassName())
            .addCode(buildFunctionBody(table))
            .build()
    }

    internal fun buildParameters(table: TableMetadata): Sequence<ParameterSpec> {
        // 使用硬编码的成员名集合代替 Entity::class.memberProperties，避免 Kotlin 反射 API 在 KSP 处理器中的类加载器冲突问题
        val skipNames = Reflections.ENTITY_MEMBERS
        return table.entityClass.getAllProperties()
            .filter { it.isAbstract() }
            .filter { it.simpleName.asString() !in skipNames }
            .map { prop ->
                val propName = prop.simpleName.asString()
                val propType = prop._type.makeNullable().toTypeName()

                ParameterSpec.builder(propName, propType)
                    .defaultValue("%T.of()", Undefined::class.asClassName())
                    .build()
            }
    }

    internal fun buildFunctionBody(table: TableMetadata, isCopy: Boolean = false): CodeBlock = buildCodeBlock {
        if (isCopy) {
            addStatement("val·entity·=·this.copy()")
        } else {
            addStatement("val·entity·=·%T.create<%T>()", Entity::class.asClassName(), table.entityClass.toClassName())
        }

        val skipNames = Reflections.ENTITY_MEMBERS
        for (prop in table.entityClass.getAllProperties()) {
            if (!prop.isAbstract() || prop.simpleName.asString() in skipNames) {
                continue
            }

            val condition: String
            if (prop._type.isInline()) {
                condition = "if·((%N·as·Any?)·!==·(%T.of<%T>()·as·Any?))"
            } else {
                condition = "if·(%N·!==·%T.of<%T>())"
            }

            beginControlFlow(condition,
                prop.simpleName.asString(), Undefined::class.asClassName(), prop._type.makeNotNullable().toTypeName()
            )

            var statement: String
            if (prop.isMutable) {
                statement = "entity.%1N·=·%1N"
            } else {
                statement = "entity[%1S]·=·%1N"
            }

            if (!prop._type.isMarkedNullable) {
                statement += "·?:·error(\"`%1L`·should·not·be·null.\")"
            }

            addStatement(statement, prop.simpleName.asString())
            endControlFlow()
        }

        addStatement("return entity")
    }
}

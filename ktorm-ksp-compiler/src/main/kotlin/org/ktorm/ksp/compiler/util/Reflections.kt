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

package org.ktorm.ksp.compiler.util

/**
 * Ktorm KSP 编译器中使用的反射相关常量
 * 
 * 为了避免在 KSP 处理器中使用 Kotlin 反射 API 导致类加载器冲突，
 * 我们使用硬编码的字符串代替反射 API 调用。
 * 
 * @see org.ktorm.ksp.compiler.KtormProcessorProvider
 * @see org.ktorm.ksp.compiler.parser.MetadataParser
 * @see org.ktorm.ksp.compiler.generator.ComponentFunctionGenerator
 * @see org.ktorm.ksp.compiler.generator.PseudoConstructorFunctionGenerator
 */
internal object Reflections {

    /**
     * Ktorm 注解的全限定名
     */
    const val TABLE_ANNOTATION = "org.ktorm.ksp.annotation.Table"
    const val COLUMN_ANNOTATION = "org.ktorm.ksp.annotation.Column"

    /**
     * Kotlin 标准类的全限定名
     */
    const val NOTHING_CLASS = "kotlin.Nothing"

    /**
     * Ktorm 模式类的全限定名
     */
    const val TYPE_REFERENCE_CLASS = "org.ktorm.schema.TypeReference"

    /**
     * Entity 接口的成员名称集合（包括属性和方法）
     * 
     * 这些成员是 org.ktorm.entity.Entity 接口中定义的，
     * 在生成实体类的代码时需要跳过这些成员。
     * 
     * 包括：
     * - 属性：entityClass, properties, changedProperties
     * - 方法：flushChanges, discardChanges, delete
     * 
     * 注意：get, set, copy, equals, hashCode, toString 等成员不在此集合中，
     * 因为它们需要被生成到实体类中。
     */
    val ENTITY_MEMBERS = setOf(
        "entityClass",
        "properties",
        "changedProperties",
        "flushChanges",
        "discardChanges",
        "delete"
    )
}
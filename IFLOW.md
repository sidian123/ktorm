# Ktorm 项目概述

Ktorm 是一个基于纯 JDBC 的轻量级、高效的 Kotlin ORM 框架。它提供了强类型和灵活的 SQL DSL，以及方便的序列 API
来减少数据库操作中的重复工作。所有 SQL 语句都是自动生成的。

## 项目结构

这是一个使用 Gradle 构建的多模块 Kotlin 项目。主要模块包括：

- `ktorm-core`: 核心 ORM 功能
- `ktorm-global`: 全局对象支持
- `ktorm-jackson`: Jackson 集成
- `ktorm-ksp-*`: Kotlin Symbol Processing 相关模块
- `ktorm-support-*`: 各种数据库支持（MySQL, PostgreSQL, Oracle, SQLite, SQL Server）

## 主要特性

1. **无配置文件**: 不需要 XML 或注解，轻量且易于使用。
2. **强类型 SQL DSL**: 在编译时暴露低级错误。
3. **灵活查询**: 可以精细控制生成的 SQL。
4. **实体序列 API**: 使用类似 Kotlin 原生集合和序列的函数（如 `filter`, `map`, `sortedBy`）编写查询。
5. **可扩展设计**: 可以编写自己的扩展来支持更多操作符、数据类型、SQL 函数和数据库方言。

## 核心组件

### Database

`org.ktorm.database.Database` 是 Ktorm 的入口类，代表一个物理数据库，用于管理连接和事务。

### Query

`org.ktorm.dsl.Query` 是查询操作的抽象，是 Ktorm 查询 DSL 的核心类。

### Entity

`org.ktorm.entity.Entity` 是所有实体类的超接口，为实体注入了许多有用的功能。

## 快速开始

1. 添加依赖：
   ```kotlin
   compile "org.ktorm:ktorm-core:${ktorm.version}"
   ```

2. 定义表结构和实体：
   ```kotlin
   object Departments : Table<Department>("t_department") {
       val id = int("id").primaryKey().bindTo { it.id }
       val name = varchar("name").bindTo { it.name }
       val location = varchar("location").bindTo { it.location }
   }

   interface Department : Entity<Department> {
       companion object : Entity.Factory<Department>()
       val id: Int
       var name: String
       var location: String
   }
   ```

3. 连接数据库并执行查询：
   ```kotlin
   val database = Database.connect("jdbc:mysql://localhost:3306/ktorm", user = "root", password = "***")
   
   // 使用 SQL DSL
   database.from(Employees).select().forEach { row ->
       println(row[Employees.name])
   }
   
   // 使用实体序列 API
   val employees = database.sequenceOf(Employees).toList()
   ```

## 构建和运行

该项目使用 Gradle 进行构建。主要命令包括：

- `./gradlew build`: 构建项目
- `./gradlew test`: 运行测试
- `./gradlew publishToMavenLocal`: 将项目发布到本地 Maven 仓库

## 开发约定

- 实体类使用单数名词命名（如 `Employee`）
- 表对象使用复数名词命名（如 `Employees`）
- 遵循 Kotlin 编码规范
- 使用强类型的 SQL DSL 而不是字符串拼接
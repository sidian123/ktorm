
plugins {
    id("ktorm.base")
    id("ktorm.modularity")
    id("ktorm.publish")
    id("ktorm.source-header-check")
}

dependencies {
    api(project(":ktorm-core"))
    testImplementation(project(":ktorm-core", configuration = "testOutput"))
    testImplementation("org.testcontainers:mssqlserver:1.20.4")
    testImplementation("com.microsoft.sqlserver:mssql-jdbc:12.10.0.jre11")
}


tasks.test {
    enabled = false
}
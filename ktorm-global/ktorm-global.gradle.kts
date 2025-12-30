
plugins {
    id("ktorm.base")
    id("ktorm.modularity")
    id("ktorm.publish")
    id("ktorm.source-header-check")
}

dependencies {
    api(project(":ktorm-core"))
    compileOnly("org.springframework:spring-jdbc:6.2.1")
    compileOnly("org.springframework:spring-tx:6.2.1")
    testImplementation(project(":ktorm-core", configuration = "testOutput"))
    testImplementation("com.h2database:h2:2.3.232")
}

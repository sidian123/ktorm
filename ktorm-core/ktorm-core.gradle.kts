
plugins {
    id("ktorm.base")
    id("ktorm.modularity")
    id("ktorm.publish")
    id("ktorm.source-header-check")
    id("ktorm.tuples-codegen")
}

dependencies {
    compileOnly("org.springframework:spring-jdbc:6.2.1")
    compileOnly("org.springframework:spring-tx:6.2.1")
    testImplementation("com.h2database:h2:2.3.232")
    testImplementation("org.slf4j:slf4j-simple:2.0.16")
}

val testOutput by configurations.creating {
    extendsFrom(configurations["testImplementation"])
}

val testJar by tasks.registering(Jar::class) {
    dependsOn(tasks.testClasses)
    from(sourceSets.test.get().output)
    archiveClassifier.set("test")
}

artifacts {
    add(testOutput.name, testJar)
}

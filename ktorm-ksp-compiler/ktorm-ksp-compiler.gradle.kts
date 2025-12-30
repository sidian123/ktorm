
plugins {
    id("ktorm.base")
    id("ktorm.publish")
    id("ktorm.source-header-check")
}

dependencies {
    implementation(project(":ktorm-core"))
    implementation(project(":ktorm-ksp-annotations"))
    implementation(project(":ktorm-ksp-spi"))
    implementation("com.google.devtools.ksp:symbol-processing-api:2.3.4")
    implementation("com.squareup:kotlinpoet-ksp:2.0.0")
    implementation("org.atteo:evo-inflector:1.3")
//    implementation("com.pinterest.ktlint:ktlint-rule-engine:1.3.0") {
//        exclude(group = "org.jetbrains.kotlin", module = "kotlin-compiler-embeddable")
//    }
//    implementation("com.pinterest.ktlint:ktlint-ruleset-standard:1.3.0") {
//        exclude(group = "org.jetbrains.kotlin", module = "kotlin-compiler-embeddable")
//    }

    testImplementation("dev.zacsweers.kctfork:core:0.12.0")
    testImplementation("dev.zacsweers.kctfork:ksp:0.12.0")
    testImplementation("com.h2database:h2:2.3.232")
    testImplementation("org.slf4j:slf4j-simple:2.0.16")
}



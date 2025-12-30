pluginManagement {
    repositories {
        // 新增的
        maven("https://maven.aliyun.com/repository/google/")
        maven("https://maven.aliyun.com/repository/public/")
        maven("https://maven.aliyun.com/repository/gradle-plugin/")
        // 原来的
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }

        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        // 新增的
        maven("https://maven.aliyun.com/repository/google/")
        maven("https://maven.aliyun.com/repository/public/")
        // 原来的
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        mavenLocal() // 可选，从本地拿包
    }
}

plugins {
    id("com.gradle.enterprise") version "3.18.2"
}

include("ktorm-core")
include("ktorm-global")
include("ktorm-jackson")
include("ktorm-ksp-annotations")
include("ktorm-ksp-compiler")
include("ktorm-ksp-compiler-maven-plugin")
include("ktorm-ksp-spi")
include("ktorm-support-mysql")
include("ktorm-support-oracle")
include("ktorm-support-postgresql")
include("ktorm-support-sqlite")
include("ktorm-support-sqlserver")

rootProject.name = "ktorm"
rootProject.children.forEach { project ->
    project.buildFileName = "${project.name}.gradle.kts"
}

develocity {
    buildScan {
        publishing.onlyIf { System.getenv("CI") == "true" }
    }
}

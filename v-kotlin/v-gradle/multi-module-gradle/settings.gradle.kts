pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

dependencyResolutionManagement {
    versionCatalogs {
        // test
        create("libs") {
            val test = version("test", "1.9.24")
            library("kotlin-test", "org.jetbrains.kotlin", "kotlin-test").versionRef(test)
        }
    }
}

rootProject.name = "multi-module-gradle"
include("mmg-foo")
include("mmg-bar")
include("mmg-common")
include("mmg-app")

includeBuild("gradle/license-plugin")

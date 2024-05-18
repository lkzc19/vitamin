pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "multi-module-gradle"
include("mmg-foo")
include("mmg-bar")
include("mmg-common")
include("mmg-app")

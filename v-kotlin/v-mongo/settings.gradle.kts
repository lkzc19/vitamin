rootProject.name = "v-mongo"

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
        // mongo
        create("mongo") {
            val mongo = version("mongo", "5.1.0")
            library("driver", "org.mongodb", "mongodb-driver-kotlin-coroutine").versionRef(mongo)
            library("bson", "org.mongodb", "bson-kotlinx").versionRef(mongo)
        }
        
        // log
        create("log") {
            val slf4j = version("slf4j", "1.7.30")
            library("slf4j", "org.slf4j", "slf4j-api").versionRef(slf4j)
            
            val logback = version("ch.qos.logback", "1.2.11")
            library("logback", "ch.qos.logback", "logback-classic").versionRef(logback)
        }
    }
}


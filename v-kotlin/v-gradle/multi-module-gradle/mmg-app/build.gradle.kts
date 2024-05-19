plugins {
    kotlin("jvm") version "1.9.22"
    application
    id("org.example.greeting")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":mmg-foo"))
    implementation(project(":mmg-bar"))
    testImplementation(libs.kotlin.test)

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}
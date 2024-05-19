plugins {
  kotlin("jvm") version "1.9.22"
  id("org.example.greeting")
  id("org.example.license")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation(project(":mmg-common"))
  testImplementation(libs.kotlin.test)
}

tasks.test {
  useJUnitPlatform()
}

kotlin {
  jvmToolchain(11)
}
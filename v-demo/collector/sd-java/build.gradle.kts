plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.sensorsdata.analytics.javasdk:SensorsAnalyticsSDK:3.4.3")
    implementation("org.slf4j:slf4j-api:2.0.0-alpha5") // 请根据最新的稳定版本调整
    implementation("ch.qos.logback:logback-classic:1.4.5") // 同样，请根据最新的稳定版本调整
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
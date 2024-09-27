plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven {
        name = "aliyunmaven"
        url = uri("https://maven.aliyun.com/repository/public")
    }
}

dependencies {
    // kafka服务端&客户端
//    implementation("org.apache.kafka:kafka_2.13:3.8.0")
    // kafka客户端
    implementation("org.apache.kafka:kafka-clients:3.8.0")
    implementation("org.slf4j:slf4j-api:2.0.12")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
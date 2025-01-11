plugins {
    id("java")
    `maven-publish`
    signing
}

group = "io.github.lkzc19"
version = "0.1.0"

repositories {
    mavenLocal()
    maven("https://maven.aliyun.com/repository/public/")
    maven("https://maven.aliyun.com/repository/gradle-plugin")
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Jar>("sourceJar") {
    from(sourceSets["main"].allSource)
    archiveClassifier.set("sources")
}

tasks.register<Jar>("javadocJar") {
    from(tasks["javadoc"])
    archiveClassifier.set("javadoc")
}

tasks.withType<GenerateModuleMetadata> {
    enabled = false
}


publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"]) // 如果是 Java 项目

            artifact(tasks["sourceJar"])
            artifact(tasks["javadocJar"])

            pom {
                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("lkzc19")
                        name.set("lkzc19")
                        email.set("lkzc19@foxmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/lkzc19/vitamin.git")
                    developerConnection.set("scm:git:ssh://github.com:lkzc19/vitamin.git")
                    url.set("https://github.com/lkzc19/vitamin")
                }
            }
        }
    }
    repositories {
        maven {
            name = "mavenCentral"
            val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = System.getProperty("SONATYPE_NEXUS_USERNAME")
                password = System.getProperty("SONATYPE_NEXUS_PASSWORD")
            }
        }
    }
    signing {
        sign(publishing.publications["mavenJava"])
    }
}
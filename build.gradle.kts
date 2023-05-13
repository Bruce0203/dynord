plugins {
    kotlin("jvm") version "1.8.21"
    application
    id("me.champeau.jmh") version "0.7.1"
}

group = "io.github.bruce0203"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(kotlin("reflect"))

    implementation("com.google.guava:guava:31.1-jre")

    jmh("org.openjdk.jmh:jmh-core:1.35")
    jmh("org.openjdk.jmh:jmh-generator-annprocess:1.35")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}


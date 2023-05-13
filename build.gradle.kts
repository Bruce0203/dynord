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

val koin_version: String by project

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(kotlin("reflect"))
    compileOnly("io.insert-koin:koin-core:$koin_version")
    testCompileOnly("io.insert-koin:koin-core:$koin_version")

    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.googlecode.concurrentlinkedhashmap:concurrentlinkedhashmap-lru:1.4.2")

    jmh("commons-io:commons-io:2.11.0")
    jmh("org.openjdk.jmh:jmh-core:0.9")
    jmh("org.openjdk.jmh:jmh-generator-annprocess:0.9")
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


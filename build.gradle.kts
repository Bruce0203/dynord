plugins {
    kotlin("jvm") version "1.8.21"
    application
    id("me.champeau.jmh") version "0.7.1"
    `maven-publish`
}

group = "io.github.bruce0203"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.codemc.org/repository/maven-public") //bstats
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

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            val githubRepository = System.getenv("GITHUB_REPOSITORY")
            url = uri("https://maven.pkg.github.com/$githubRepository")
            credentials {
                username = System.getenv("GITHUB_REPOSITORY")?.split("/")?.first()
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

tasks {
    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }

    val javadocJar by creating(Jar::class) {
        dependsOn.add(javadoc)
        archiveClassifier.set("javadoc")
        from(javadoc)
    }

    artifacts {
        archives(sourcesJar)
        archives(javadocJar)
        archives(jar)
    }
}

publishing {
    publications {
        register("gprRelease", MavenPublication::class) {
            groupId = group as String
            artifactId = project.name
            version = project.version as String

            from(components["java"])

            pom {
                packaging = "jar"
                name.set(project.name)
                description.set(project.name)
            }

        }
    }
}

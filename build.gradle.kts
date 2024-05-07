import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

plugins {
    id("java")
    id("xyz.jpenilla.run-paper") version "2.2.4"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dev.josemc"
version = "${project.property("version")}"

repositories {
    mavenCentral()

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:${project.property("minecraftVersion")}-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(21)

    }

    processResources {
        filesMatching("**/plugin.yml") {
            expand(project.properties)
        }
    }

    runServer {
        minecraftVersion("${project.property("minecraftVersion")}")
    }

    shadowJar {

        manifest {
            attributes(
                mapOf(
                    "Built-By" to System.getProperty("user.name"),
                    "Version" to version,
                    "Build-Timestamp" to SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSSZ").format(Date.from(Instant.now())),
                    "Created-By" to "Gradle ${gradle.gradleVersion}",
                    "Build-Jdk" to "${System.getProperty("java.version")} ${System.getProperty("java.vendor")} ${
                        System.getProperty(
                            "java.vm.version"
                        )
                    }",
                    "Build-OS" to "${System.getProperty("os.name")} ${System.getProperty("os.arch")} ${
                        System.getProperty(
                            "os.version"
                        )
                    }"
                )
            )
        }
        archiveFileName.set("NightCore-${version}.jar")
        archiveClassifier.set("")
    }

    compileJava.get().dependsOn(clean)
    build.get().dependsOn(shadowJar)
}
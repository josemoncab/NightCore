plugins {
    id("java")
    id("xyz.jpenilla.run-paper") version "2.2.2"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "${project.property("group")}"
version = "${project.property("pluginVersion")}"

repositories {
    mavenCentral()

    // PaperMC Repo
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    // PaperMC
    compileOnly("io.papermc.paper:paper-api:${project.property("mcVersion")}-${project.property("paperApi")}")

}

// Replace tokens in the files
tasks.processResources {
    filesMatching("**/paper-plugin.yml") {
        expand(project.properties)
    }
}

// Run a test server
tasks.runServer {
    minecraftVersion("1.20.2")
}
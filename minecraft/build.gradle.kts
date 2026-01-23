plugins {
    id("java")
    id("base")
    alias(libs.plugins.gradle.shadow)
}

base {
    archivesName.set("scaffold-minecraft")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":discord"))

    compileOnly(libs.paper.api)
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
plugins {
    id("java")
    alias(libs.plugins.gradle.shadow)
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
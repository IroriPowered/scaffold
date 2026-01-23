plugins {
    id("java")
    alias(libs.plugins.gradle.shadow)
}

repositories {
    maven("https://maven.hytale.com/release")
    maven("https://maven.hytale.com/pre-release")
}

dependencies {
    implementation(project(":discord"))

    implementation(libs.guava)
    implementation(libs.lang3)
    // TODO: don't include SLF4j, HytaleServer does not log it correctly
    implementation(libs.slf4j.api)

    compileOnly(libs.hytale)
    compileOnly(libs.shodo)
}
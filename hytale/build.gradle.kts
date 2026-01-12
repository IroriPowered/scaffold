plugins {
    id("java")
    alias(libs.plugins.gradle.shadow)
}

dependencies {
    implementation(project(":discord"))
}
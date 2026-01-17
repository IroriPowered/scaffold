plugins {
    id("java")
    alias(libs.plugins.gradle.shadow)
}

dependencies {
    implementation(project(":discord"))

    compileOnly(files("libs/HytaleServer.jar"))

    implementation(libs.guava)
    implementation(libs.lang3)
    // TODO: don't include SLF4j, HytaleServer does not log it correctly
    implementation(libs.slf4j.api)

    compileOnly(libs.shodo)
}
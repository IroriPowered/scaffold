plugins {
    id("java")
    alias(libs.plugins.gradle.shadow)
}

dependencies {
    implementation(libs.jda)

    compileOnly(libs.guava)
    compileOnly(libs.lang3)
    compileOnly(libs.gson)
    compileOnly(libs.slf4j.api)
    compileOnly(libs.shodo)
}
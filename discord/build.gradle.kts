plugins {
    id("java")
    alias(libs.plugins.gradle.shadow)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.jda)

    compileOnly(libs.guava)
    compileOnly(libs.lang3)
    compileOnly(libs.gson)
    compileOnly(libs.slf4j.api)
}
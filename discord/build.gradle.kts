plugins {
    id("java")
    alias(libs.plugins.gradle.shadow)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.jda)

    compileOnly(libs.slf4j.api)
}
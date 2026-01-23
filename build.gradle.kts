plugins {
    id("java")
    alias(libs.plugins.gradle.shadow) apply false
}

allprojects {
    group = "cc.irori.scaffold"
    version = "1.0-SNAPSHOT"
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://maven.irori.cc/repository/public/")
    }
}

dependencies {
}

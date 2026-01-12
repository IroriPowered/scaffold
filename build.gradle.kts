plugins {
    id("java")
    alias(libs.plugins.gradle.shadow) apply false
}

group = "cc.irori"
version = "1.0-SNAPSHOT"

subprojects {
    repositories {
        mavenCentral()
    }
}

dependencies {
}

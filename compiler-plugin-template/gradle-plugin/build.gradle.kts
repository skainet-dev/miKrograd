plugins {
    kotlin("jvm") version "2.0.0"
    `java-gradle-plugin`
}

group = "org.mikrograd"
version = "0.1-SNAPSHOT"

dependencies {
    implementation(kotlin("gradle-plugin-api"))
    implementation(project(":plugin"))
}

gradlePlugin {
    plugins {
        create("simplePlugin") {
            id = "org.mikrograd.simpleplugin"
            implementationClass = "org.mikrograd.simpleplugin.SimpleGradlePlugin"
        }
    }
}

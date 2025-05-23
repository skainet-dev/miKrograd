@file:Suppress("OPT_IN_USAGE")

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    id("org.mikrograd.simpleplugin")
}


group = "org.mikrograd"

kotlin {

    compilerOptions {
        // Common compiler options applied to all Kotlin source sets
        freeCompilerArgs.add("-Xexpect-actual-classes")
        freeCompilerArgs.add("-Xmulti-platform")
    }

    jvmToolchain(17)

    jvm()


    sourceSets {
        commonMain.dependencies {
            implementation(kotlin("stdlib-common"))
            implementation(project(":dot-poet"))
        }

        commonTest.dependencies {
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
        }


        jvmTest.dependencies {
            implementation(kotlin("test-junit"))
        }
    }
}

plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    applyDefaultHierarchyTemplate()
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.io)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

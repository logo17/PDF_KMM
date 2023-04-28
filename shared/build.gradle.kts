plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
}

kotlin {
    android()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("androidx.core:core:1.9.0")
                implementation("io.ktor:ktor-client-core:2.0.0-beta-1")
                implementation("io.ktor:ktor-client-serialization:2.0.0-beta-1")
                implementation("io.ktor:ktor-client-content-negotiation:2.0.0-beta-1")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.0-beta-1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
                implementation("io.ktor:ktor-client-logging:2.0.0-beta-1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-ios:2.0.0-beta-1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt") {
                    version {
                        strictly("1.6.0-native-mt")
                    }
                }
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.loguito.pdftool"
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src\\androidMain\\AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
    dependencies {
        implementation("com.tom-roush:pdfbox-android:2.0.27.0")
        implementation("io.ktor:ktor-client-android:2.0.0-beta-1")
    }
}

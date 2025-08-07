import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.sooq.appintro"
    compileSdk = 36

    defaultConfig {
        minSdk = 28
    }

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin.compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2025.07.00"))
    implementation("androidx.compose.ui:ui:1.9.0-rc01")
    implementation("androidx.compose.ui:ui-tooling:1.9.0-rc01")
    implementation("androidx.compose.ui:ui-tooling-preview:1.9.0-rc01")
    implementation("androidx.activity:activity-compose:1.12.0-alpha05")
    implementation("androidx.compose.runtime:runtime:1.9.0-rc01")
    implementation("androidx.compose.material3:material3:1.5.0-alpha01")
    implementation("androidx.navigation:navigation-compose:2.9.2")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.14.0-alpha03")
    implementation("androidx.activity:activity-ktx:1.12.0-alpha05")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0-alpha01")
    implementation("androidx.compose.material:material-icons-core:1.7.8")
    implementation("androidx.core:core-ktx:1.16.0")
}

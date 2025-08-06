plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.sooq.prices"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.sooq.prices"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    signingConfigs {
        create("release") {
            storeFile = file("sign.p12")
            storePassword = "74554"
            keyAlias = "sign"
            keyPassword = "74554"
            storeType = "pkcs12"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    compileOptions {
        jvmTarget.set(JavaVersion.VERSION_19)
    }
}

kotlin {
    jvmToolchain(19)
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2025.07.00"))
    implementation("androidx.compose.ui:ui:1.9.0-rc01")
//    implementation("androidx.compose.ui:ui-tooling-preview:1.9.0-rc01")
    implementation("androidx.activity:activity-compose:1.12.0-alpha05")
//    implementation("androidx.compose.runtime:runtime:1.9.0-rc01")
    implementation("androidx.compose.material3:material3:1.5.0-alpha01")
//    implementation("androidx.navigation:navigation-compose:2.9.2")
//    implementation("androidx.appcompat:appcompat:1.7.1")
//    implementation("com.google.android.material:material:1.14.0-alpha03")
//    implementation("androidx.activity:activity-ktx:1.12.0-alpha05")
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0-alpha01")
}
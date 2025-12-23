/*
 * Copyright 2024 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin") // Dependency Injection framework
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") // Safely handles API keys
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.20" // Compiler plugin for Jetpack Compose
}

android {
    lint {
        // Output lint results to a standardized format for CI/CD ingestion
        sarifOutput = file("$buildDir/reports/lint-results.sarif")
    }

    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.google.places.android.ktx.demo.newapi"
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        
        // MultiDex needed because the app methods exceed the 65k limit (common with GMS)
        multiDexEnabled = true
    }

    buildFeatures {
        // Generates BuildConfig class to access secrets (API Key)
        buildConfig = true
        // Enables Jetpack Compose functionality
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        jvmTarget = "17"
    }

    namespace = "com.google.places.android.ktx.demo.newapi"
}

dependencies {
    // -----------------------------------------------------------------------------------------
    // Jetpack Compose
    // The modern UI toolkit for Android.
    // -----------------------------------------------------------------------------------------
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3) // Material Design 3 components
    implementation(libs.androidx.activity.compose) // Integration with Activity
    implementation(libs.runtime) // Compose runtime

    // Debug tools for Compose
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // -----------------------------------------------------------------------------------------
    // Android Core & Lifecycle
    // Essential libraries for backwards compatibility and app architecture.
    // -----------------------------------------------------------------------------------------
    implementation(libs.core.ktx) // Kotlin extensions for core Android APIs
    implementation(libs.appcompat) // Backwards compatible Activity/Fragment
    implementation(libs.fragment.ktx) // Kotlin extensions for Fragments
    implementation(libs.lifecycle.runtime.ktx) // LifecycleScope for Coroutines
    implementation(libs.multidex) // Support for apps with >64k methods
    implementation(libs.material) // Material Design 2 (Legacy/View-based) components

    // -----------------------------------------------------------------------------------------
    // Google Maps Platform
    // -----------------------------------------------------------------------------------------
    implementation(libs.play.services.maps) // Google Maps SDK for Android
    implementation(project(":places-ktx")) // LOCAL DEPENDENCY: The library being demonstrated!

    // -----------------------------------------------------------------------------------------
    // Dependency Injection (Hilt)
    // Manages dependency creation and lifetime manually (Boilerplate reduction).
    // -----------------------------------------------------------------------------------------
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // -----------------------------------------------------------------------------------------
    // Networking & Utilities
    // -----------------------------------------------------------------------------------------
    implementation(libs.volley) // Legacy networking (consider migrating to Retrofit/OkHttp)
    implementation(libs.kotlin.stdlib)
}

secrets {
    // Reads secrets from this file (not checked into git)
    propertiesFileName = "secrets.properties"
    // defaults for public/CI builds
    defaultPropertiesFileName = "local.defaults.properties"
}

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
 *
 */

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    lint {
        sarifOutput = file("$buildDir/reports/lint-results.sarif")
    }

    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        multiDexEnabled = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += "-Xexplicit-api=strict"
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
    namespace = "com.google.android.libraries.places.ktx"
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.volley)
    implementation(libs.multidex)
    
    // API dependencies are transitive
    api(libs.kotlinx.coroutines.play.services)
    api(libs.places)

    // Tests
    testImplementation(libs.junit.ext)
    testImplementation(libs.test.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.truth)
}

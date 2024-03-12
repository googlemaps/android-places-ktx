plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
  namespace = "com.example.new_places_client"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.example.new_places_client"
    minSdk = 26
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.9"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  implementation("androidx.core:core-ktx:1.12.0")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
  implementation("androidx.activity:activity-compose:1.8.2")
  implementation(platform("androidx.compose:compose-bom:2024.01.00"))
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.ui:ui-graphics")
  implementation("androidx.compose.ui:ui-tooling-preview")
  implementation("androidx.compose.material3:material3-android:1.2.1")

  testImplementation("junit:junit:4.13.2")
  testImplementation("com.google.truth:truth:1.4.2")

  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
  androidTestImplementation(platform("androidx.compose:compose-bom:2024.01.00"))
  androidTestImplementation("androidx.compose.ui:ui-test-junit4")

  debugImplementation("androidx.compose.ui:ui-tooling")
  debugImplementation("androidx.compose.ui:ui-test-manifest")

  implementation("com.google.android.libraries.places:places:3.3.0")
  implementation("com.google.android.gms:play-services-maps:18.2.0")
  implementation(project(":places-ktx"))

  // Jetpack navigation
  val navVersion = "2.7.7"
  // Jetpack Compose Integration
  implementation("androidx.navigation:navigation-compose:$navVersion")

  // Only needed for Google Maps compose
  implementation("com.google.maps.android:maps-compose:4.3.3")
}

secrets {
  // To add your Maps API key to this project:
  // 1. Create a file ./secrets.properties in the root folder of the project
  // 2. Add this line, where YOUR_API_KEY is your API key:
  //        PLACES_API_KEY=YOUR_API_KEY
  propertiesFileName = "secrets.properties"
  defaultPropertiesFileName = "local.defaults.properties"
}

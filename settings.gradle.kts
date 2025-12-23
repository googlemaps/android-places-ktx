pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

include(":app")
include(":places-ktx")
include(":demo-app-new-api")

rootProject.name = "android-places-ktx"

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

import com.vanniktech.maven.publish.MavenPublishBaseExtension

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(libs.gradle)
        classpath(libs.dokka.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.hilt.android.gradle.plugin)
        classpath(libs.secrets.gradle.plugin)
        classpath(libs.jacoco.android)
    }
}

plugins {
    id("org.jetbrains.dokka") version "2.0.0"
    id("com.vanniktech.maven.publish") version "0.34.0" apply false
}

// Logic to determine artifact ID based on project name
// In Kotlin DSL, we can use a simple function or just inline it in subprojects
fun getProjectArtifactId(project: Project): String? {
    return if (project.name == "places-ktx") {
        project.name
    } else {
        null
    }
}

allprojects {
    group = "com.google.maps.android"
    // {x-release-please-start-version}
    version = "3.6.0"
    // {x-release-please-end}

    // Set artifact ID in extra properties for usage in subprojects
    extra.set("artifactId", getProjectArtifactId(project))

    repositories {
        google()
        mavenCentral()
        flatDir {
            dirs("libs")
        }
    }
}

subprojects {
    val artifactId = extra.get("artifactId") as? String ?: return@subprojects

    apply(plugin = "com.android.library")
    apply(plugin = "maven-publish")
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "com.mxalbert.gradle.jacoco-android")
    apply(plugin = "com.vanniktech.maven.publish")

    configure<JacocoPluginExtension> {
        toolVersion = "0.8.7"
    }

    tasks.withType<Test>().configureEach {
        // Jacoco configuration usually goes into the jacocoAndroid extension or task config
        // In the original groovy: jacoco.includeNoLocationClasses = true
        // In KTS for unit tests:
        extensions.configure<JacocoTaskExtension> {
            isIncludeNoLocationClasses = true
            excludes = listOf("jdk.internal.*")
        }
    }

    // Vanniktech Maven Publish Configuration
    configure<com.vanniktech.maven.publish.MavenPublishBaseExtension> {
        publishToMavenCentral()
        signAllPublications()

        pom {
            name.set(project.name)
            description.set("Kotlin extensions (KTX) for Places SDK for Android")
            url.set("https://github.com/googlemaps/android-places-ktx")

            licenses {
                license {
                    name.set("The Apache Software License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    distribution.set("repo")
                }
            }

            scm {
                connection.set("scm:git@github.com:googlemaps/android-places-ktx.git")
                developerConnection.set("scm:git@github.com:googlemaps/android-places-ktx.git")
                url.set("https://github.com/googlemaps/android-places-ktx")
            }

            organization {
                name.set("Google Inc")
                url.set("http://developers.google.com/maps")
            }

            developers {
                developer {
                    id.set("google")
                    name.set("Google Inc.")
                }
            }
        }
    }
}

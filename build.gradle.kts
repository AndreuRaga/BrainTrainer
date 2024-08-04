// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}

buildscript {
    repositories {
        // Make sure that you have the following two repositories
        google()  // Google's Maven repository
        mavenCentral()  // Maven Central repository
    }

    dependencies {
        classpath(libs.gradle)
        // Make sure that you have the Google services Gradle plugin dependency
        classpath(libs.google.services)
        // Add the dependency for the Crashlytics Gradle plugin
        classpath(libs.firebase.crashlytics.gradle)
    }
}
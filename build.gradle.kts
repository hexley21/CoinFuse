buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.1")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id(Config.Plugins.android) version Versions.androidPluginVersion apply false
    id(Config.Plugins.androidLibrary) version Versions.androidPluginVersion apply false
    id(Config.Plugins.dagger) version Versions.hiltVersion apply false
}
@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.hxl.presentation"
    compileSdk = 33

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    // Modules
    implementation(project(":domain"))

    // Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.rxjava3)
    // Hilt
    implementation(libs.dagger.hilt.android)
    annotationProcessor(libs.dagger.hilt.compiler)
    // LifeCycle
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    // Reactive
    implementation(libs.reactivex.rxjava3.rxjava)
    implementation(libs.reactivex.rxjava3.rxandroid)
    // Test
    testImplementation(libs.testing.androidx.arch.core)
    testImplementation(libs.testing.junit)
    testImplementation(libs.testing.extJunit)
    testImplementation(libs.testing.robolectric)
    testImplementation(libs.testing.mockito.core)
    testImplementation(libs.testing.dagger.hilt.android)
    testAnnotationProcessor(libs.dagger.hilt.compiler)
    testImplementation(libs.testing.androidx.room)
    testImplementation(libs.testing.paging.common)
    testImplementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.test)
    // Desugar
    coreLibraryDesugaring(libs.desugar)
}
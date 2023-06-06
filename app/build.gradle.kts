@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.hxl.coinfuse"
    compileSdk = 33

    testOptions.unitTests.isIncludeAndroidResources = true
    defaultConfig {
        applicationId = "com.hxl.coinfuse"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "com.hxl.coinfuse.conf.AppTestRunner"
        testInstrumentationRunnerArguments.putAll(
            mapOf(
                "clearPackageData" to "true"
            )
        )
        proguardFiles(getDefaultProguardFile("proguard-android.txt"))
        signingConfig = signingConfigs.getByName("debug")

        buildConfigField("String", "API_URL", "\"" + "https://api.coincap.io/v2/" +"\"")
        buildConfigField("String", "ASSET_URL", "\"" + "https://assets.coincap.io/assets/icons/%s@2x.png" +"\"")
    }
    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        animationsDisabled = true
        unitTests {
            isIncludeAndroidResources = true
        }
    }


    buildFeatures {
        dataBinding = true
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
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
}

dependencies {
    // Modules
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":local"))
    implementation(project(":remote"))
    implementation(project(":presentation"))

    // Android Core
    implementation(libs.androidx.appCompat)
    implementation(libs.material.ui)
    implementation(libs.androidx.constraint)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.splash)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.navigationUi)
    implementation(libs.androidx.navigationFeatures)
    implementation(libs.androidx.swipeRefreshLayout)
    // Hilt
    implementation(libs.dagger.hilt.android)
    annotationProcessor(libs.dagger.hilt.compiler)
    // LifeCycle
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    // Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.rxjava3)

    // UI
    implementation(libs.ui.circleindicator)
    implementation(libs.ui.glide)
    implementation(libs.ui.lottie)
    implementation(libs.ui.chart)
    annotationProcessor(libs.ui.glide.compiler)
    implementation(libs.ui.shimmer)

    // Reactive
    implementation(libs.reactivex.rxjava3.rxjava)
    implementation(libs.reactivex.rxjava3.rxandroid)
    // Retrofit
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.adapter.rxjava3)
    implementation(libs.retrofit2.converter.moshi)
    // Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.rxjava3)
    implementation(libs.androidx.room.paging)

    // Test
    testImplementation(libs.testing.junit)
    testImplementation(libs.testing.mockito.core)
    // Android test
    androidTestImplementation(libs.testing.extJunit)
    androidTestImplementation(libs.testing.testRunner)
    androidTestUtil(libs.testing.testOrchestrator)
    androidTestImplementation(libs.testing.mockito.android)
    androidTestImplementation(libs.testing.dagger.hilt.android)
    androidTestAnnotationProcessor(libs.dagger.hilt.compiler)
    // UI test
    androidTestImplementation(libs.testing.espresso.core)
    androidTestImplementation(libs.testing.espresso.contrib)
    implementation(libs.testing.espresso.idling)
    androidTestImplementation(libs.testing.androidx.navigation)
    androidTestImplementation(libs.testing.androidx.fragment)

    // Debug
    debugImplementation(libs.testing.androidx.fragment.manifest)

    // Desugar
    coreLibraryDesugaring(libs.desugar)
}

@file:Suppress("UnstableApiUsage")

plugins {
    id(Config.Plugins.androidLibrary)
    id(Config.Plugins.dagger)
}

android {
    namespace = Modules.NameSpaces.presentation
    compileSdk = Config.Android.compileSdk

    defaultConfig {
        minSdk = Config.Android.minSdk

        testInstrumentationRunner = Config.Android.testRunner
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
}

dependencies {
    // Modules
    implementation(project(Modules.domain))

    // Hilt
    implementation(Deps.Hilt.hilt)
    annotationProcessor(Deps.Hilt.hiltCompiler)
    // LifeCycle
    implementation(Deps.LifeCycle.viewModel)
    implementation(Deps.LifeCycle.liveData)
    // Reactive
    implementation(Deps.Reactive.rxJava)
    implementation(Deps.Reactive.rxAndroid)
    // Test
    testImplementation(Deps.Test.junit)
    testImplementation(Deps.Test.extJunit)
    testImplementation(Deps.Test.roboelectric)
    testImplementation(Deps.Test.mockito)
    testImplementation(Deps.Test.hilt)
    testAnnotationProcessor(Deps.Test.hiltCompiler)
    testImplementation(Deps.Room.roomTesting)
    androidTestImplementation(Deps.Test.espresso)

    // Desugar
    coreLibraryDesugaring(Deps.Others.desugar)
}
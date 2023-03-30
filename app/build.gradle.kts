@file:Suppress("UnstableApiUsage")

plugins {
    id(Config.Plugins.android)
    id(Config.Plugins.dagger)
}

android {
    namespace = Modules.NameSpaces.app
    compileSdk = Config.Android.compileSdk


    defaultConfig {
        applicationId = Environments.appId
        minSdk = Config.Android.minSdk
        targetSdk = Config.Android.targetSdk
        versionCode = Environments.versionCode
        versionName = Environments.versionName

        testInstrumentationRunner = Config.Android.testRunner
        proguardFiles(getDefaultProguardFile("proguard-android.txt"))
        signingConfig = signingConfigs.getByName("debug")

        buildConfigField("String", "API_URL", "\"" + Environments.apiUrl +"\"")
        buildConfigField("String", "ASSET_URL", "\"" + Environments.assetUrl +"\"")
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
    implementation(project(Modules.domain))
    implementation(project(Modules.data))
    implementation(project(Modules.local))
    implementation(project(Modules.remote))
    implementation(project(Modules.presentation))

    // Android Core
    implementation(Deps.Core.appCompat)
    implementation(Deps.Core.material)
    implementation(Deps.Core.constraint)
    implementation(Deps.Core.fragment)
    implementation(Deps.Core.splash)
    implementation(Deps.Core.navigation)
    implementation(Deps.Core.navigationUi)
    implementation(Deps.Core.navigationFeatures)
    // Hilt
    implementation(Deps.Hilt.hilt)
    annotationProcessor(Deps.Hilt.hiltCompiler)
    // LifeCycle
    implementation(Deps.LifeCycle.viewModel)
    implementation(Deps.LifeCycle.liveData)

    // UI
    implementation(Deps.UI.circleIndicator)
    implementation(Deps.UI.glide)
    annotationProcessor(Deps.UI.glideCompiler)

    // Reactive
    implementation(Deps.Reactive.rxJava)
    implementation(Deps.Reactive.rxAndroid)
    // Retrofit
    implementation(Deps.Retrofit.retrofit)
    implementation(Deps.Retrofit.retrofitRxJavaAdapter)
    implementation(Deps.Retrofit.retrofitMoshiConverter)
    // Room
    implementation(Deps.Room.room)
    annotationProcessor(Deps.Room.roomCompiler)
    implementation(Deps.Room.roomRxJava)
    implementation(Deps.Room.roomPaging)

    // Test
    testImplementation(Deps.Test.junit)
    testImplementation(Deps.Test.extJunit)
    testImplementation(Deps.Test.roboelectric)
    testImplementation(Deps.Test.mockito)
    testImplementation(Deps.Test.hilt)
    testAnnotationProcessor(Deps.Test.hiltCompiler)
    testImplementation(Deps.Room.roomTesting)
    androidTestImplementation(Deps.Test.espresso)
    androidTestImplementation(Deps.Test.navigation)

    // Desugar
    coreLibraryDesugaring(Deps.Others.desugar)
}

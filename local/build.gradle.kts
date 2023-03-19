@file:Suppress("UnstableApiUsage")

plugins {
    id(Config.Plugins.androidLibrary)
}

android {
    namespace = Modules.NameSpaces.local
    compileSdk = Config.Android.compileSdk

    defaultConfig {
        minSdk = Config.Android.minSdk

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
}

dependencies {
    // Module
    implementation(project(Modules.data))
    implementation(project(Modules.domain))
    
    // Room
    implementation(Deps.Room.room)
    annotationProcessor(Deps.Room.roomCompiler)
    implementation(Deps.Room.roomRxJava)
    implementation(Deps.Room.roomPaging)
    testImplementation(Deps.Room.roomTesting)

    // Test
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.extJunit)
    // Desugar
    coreLibraryDesugaring(Deps.Others.desugar)
}
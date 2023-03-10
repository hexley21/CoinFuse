@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.hxl.cryptonumismatist"
    compileSdk = 33

    fun CONFIG(i: String): String {
        return "\"${project.properties[i]}\""
    }

    defaultConfig {
        applicationId = "com.hxl.cryptonumismatist"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        proguardFiles(getDefaultProguardFile("proguard-android.txt"))

        buildConfigField("String", "API_URL", CONFIG("api.url"))
    }

    buildFeatures {
        dataBinding = true
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    val hiltVersion = "2.45"
    val lifecycleVersion = "2.6.0-alpha04"
    val retrofitVersion = "2.9.0"
    val rxjavaVersion = "3.1.6"
    val rxandroidVersion = "3.0.2"
    val navVersion = "2.5.3"
    val fragmentVersion = "1.5.5"

    // Module implementation
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":local"))

    /* ---- Android implementations ---- */
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    //Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.0")
    // Circle Tab Indicator
    implementation("me.relex:circleindicator:2.1.6")

    /* ----- Third party Libraries ----- */
    // RxJava
    implementation("io.reactivex.rxjava3:rxjava:$rxjavaVersion")
    implementation("io.reactivex.rxjava3:rxandroid:$rxandroidVersion")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:adapter-rxjava3:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")


    /* ---- Architecture Components ---- */
    // Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    annotationProcessor("com.google.dagger:hilt-compiler:$hiltVersion")
    // ViewModel
    //noinspection GradleDependency
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycleVersion")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycleVersion")
    // Fragment
    implementation("androidx.fragment:fragment:$fragmentVersion")
    // Navigation Components
    implementation("androidx.navigation:navigation-fragment:$navVersion")
    implementation("androidx.navigation:navigation-ui:$navVersion")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$navVersion")
    // Feature module Support


    /* ------------------------------------------ Test ------------------------------------------ */

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    /* ---- Architecture Components ---- */
    // Hilt For local unit tests
    testImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    testAnnotationProcessor("com.google.dagger:hilt-compiler:$hiltVersion")
    // Hilt For instrumentation tests
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    androidTestAnnotationProcessor("com.google.dagger:hilt-compiler:$hiltVersion")
    // Navigation Test
    androidTestImplementation("androidx.navigation:navigation-testing:$navVersion")

}

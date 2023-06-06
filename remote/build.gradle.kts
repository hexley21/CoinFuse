plugins {
    id("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // Module
    implementation(project(":data"))
    implementation(project(":domain"))
    // RxJava
    implementation(libs.reactivex.rxjava3.rxjava)
    // Retrofit
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.adapter.rxjava3)
    implementation(libs.retrofit2.converter.moshi)
    implementation(libs.okhttp3.logging)
    // Test
    testImplementation(libs.testing.junit)
    testImplementation(libs.testing.mockito.core)
}
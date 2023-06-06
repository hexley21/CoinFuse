plugins {
    id("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // Module
    implementation(project(":domain"))

    implementation(libs.javax.inject)
    // Reactive
    implementation(libs.reactivex.rxjava3.rxjava)
    // Test
    testImplementation(libs.testing.junit)
    testImplementation(libs.testing.mockito.core)
    // IDE
    implementation(libs.jetbrains.annotations)
}
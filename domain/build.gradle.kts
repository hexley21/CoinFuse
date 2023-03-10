plugins {
    id("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
dependencies {

    val rxjavaVersion = "3.1.6"

    implementation("org.jetbrains:annotations:24.0.0")

    // RxJava
    implementation("io.reactivex.rxjava3:rxjava:$rxjavaVersion")

    // Test
    testImplementation("junit:junit:4.13.2")
}
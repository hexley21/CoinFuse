plugins {
    id("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    val retrofitVersion = "2.9.0"
    val rxjavaVersion = "3.1.6"

    /* ----- Third party Libraries ----- */
    // RxJava
    implementation("io.reactivex.rxjava3:rxjava:$rxjavaVersion")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:adapter-rxjava3:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
}
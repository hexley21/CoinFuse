plugins {
    id(Config.Plugins.javaLibrary)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // Module
    implementation(project(Modules.data))
    implementation(project(Modules.domain))
    // RxJava
    implementation(Deps.Reactive.rxJava)
    // Retrofit
    implementation(Deps.Retrofit.retrofit)
    implementation(Deps.Retrofit.retrofitRxJavaAdapter)
    implementation(Deps.Retrofit.retrofitMoshiConverter)
    implementation(Deps.Retrofit.okhttpLoggingInterceptor)
    // Test
    testImplementation(Deps.Test.junit)
    testImplementation(Deps.Test.mockito)
}
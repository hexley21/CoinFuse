plugins {
    id(Config.Plugins.javaLibrary)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // RxJava
    implementation(Deps.Reactive.rxJava)
    // Retrofit
    implementation(Deps.Retrofit.retrofit)
    implementation(Deps.Retrofit.retrofitRxJavaAdapter)
    implementation(Deps.Retrofit.retrofitGsonConverter)
}
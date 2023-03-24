plugins {
    id(Config.Plugins.javaLibrary)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
dependencies {
    implementation(Deps.Java.javaxInject)
    // RxJava
    implementation(Deps.Reactive.rxJava)
    // Test
    testImplementation(Deps.Test.junit)
    // IDE
    implementation(Deps.Others.jetbrainsAnno)
}
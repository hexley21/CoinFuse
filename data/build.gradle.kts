plugins {
    id(Config.Plugins.javaLibrary)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // Module
    implementation(project(Modules.domain))
    // Reactive
    implementation(Deps.Reactive.rxJava)
    // Test
    testImplementation(Deps.Test.junit)
    testImplementation(Deps.Test.mockito)
    // IDE
    implementation(Deps.Others.jetbrainsAnno)
}
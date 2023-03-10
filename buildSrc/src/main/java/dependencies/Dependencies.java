public final class Dependencies {
    public static final class Core {
        public static final String appCompat = "androidx.appcompat:appcompat:" + Versions.appCompatVersion;
        public static final String material = "com.google.android.material:material:" + Versions.materialVersion;
        public static final String constraint = "androidx.constraintlayout:constraintlayout:" + Versions.constraintLayoutVersion;
        public static final String fragment = "androidx.fragment:fragment:" + Versions.fragmentVersion;
        public static final String navigation = "androidx.navigation:navigation-fragment:" + Versions.navVersion;
        public static final String navigationUi = "androidx.navigation:navigation-ui:" + Versions.navVersion;
        public static final String navigationFeatures = "androidx.navigation:navigation-dynamic-features-fragment:" + Versions.navVersion;
        public static final String splash = "androidx.core:core-splashscreen:" + Versions.splashScreenVersion;
    }

    public static final class LifeCycle {
        public static final String viewModel = "androidx.lifecycle:lifecycle-viewmodel:" + Versions.lifecycleVersion;
        public static final String liveData = "androidx.lifecycle:lifecycle-livedata:" + Versions.lifecycleVersion;
    }

    public static final class UI {
        public static final String circleIndicator = "me.relex:circleindicator:" + Versions.circleIndicatorVersion;
    }

    public static final class Reactive {
        public static final String rxJava = "io.reactivex.rxjava3:rxjava:" + Versions.rxJavaVersion;
        public static final String rxAndroid = "io.reactivex.rxjava3:rxandroid:" + Versions.rxAndroidVersion;
    }

    public static final class Retrofit {
        public static final String retrofit = "com.squareup.retrofit2:retrofit:" + Versions.retrofitVersion;
        public static final String retrofitRxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava3:" + Versions.retrofitVersion;
        public static final String retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:" + Versions.retrofitVersion;
    }

    public static final class Hilt {
        public static final String hilt = "com.google.dagger:hilt-android:" + Versions.hiltVersion;
        public static final String hiltCompiler = "com.google.dagger:hilt-compiler:" + Versions.hiltVersion;
    }

    public static final class Test {
        public static final String junit = "junit:junit:" + Versions.junitVersion;
    }

}

//    /* ------------------------------------------ Test ------------------------------------------ */
//
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//
//    /* ---- Architecture Components ---- */
//    // Hilt For local unit tests
//    testImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
//    testAnnotationProcessor("com.google.dagger:hilt-compiler:$hiltVersion")
//    // Hilt For instrumentation tests
//    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
//    androidTestAnnotationProcessor("com.google.dagger:hilt-compiler:$hiltVersion")
//    // Navigation Test
//    androidTestImplementation("androidx.navigation:navigation-testing:$navVersion")
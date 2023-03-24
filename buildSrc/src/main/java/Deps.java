public final class Deps {
    public static final class Java {
        public static final String javaxInject = "javax.inject:javax.inject:" + Versions.javaxVersion;
    }
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
        public static final String glide = "com.github.bumptech.glide:glide:" + Versions.glideVersion;
        public static final String glideCompiler = "com.github.bumptech.glide:compiler:" + Versions.glideVersion;
    }

    public static final class Reactive {
        public static final String rxJava = "io.reactivex.rxjava3:rxjava:" + Versions.rxJavaVersion;
        public static final String rxAndroid = "io.reactivex.rxjava3:rxandroid:" + Versions.rxAndroidVersion;
    }

    public static final class Retrofit {
        public static final String retrofit = "com.squareup.retrofit2:retrofit:" + Versions.retrofitVersion;
        public static final String retrofitRxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava3:" + Versions.retrofitVersion;
        public static final String retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:" + Versions.retrofitVersion;
        public static final String okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:" + Versions.okHttpVersion;
    }

    public static final class Room {
        public static final String room = "androidx.room:room-runtime:" + Versions.roomVersion;
        public static final String roomCompiler = "androidx.room:room-compiler:" + Versions.roomVersion;
        public static final String roomRxJava = "androidx.room:room-rxjava3:" + Versions.roomVersion;
        public static final String roomPaging = "androidx.room:room-paging:" + Versions.roomVersion;
        public static final String roomTesting = "androidx.room:room-testing:" + Versions.roomVersion;
    }

    public static final class Hilt {
        public static final String hilt = "com.google.dagger:hilt-android:" + Versions.hiltVersion;
        public static final String hiltCompiler = "com.google.dagger:hilt-compiler:" + Versions.hiltVersion;
    }

    public static final class Test {
        public static final String junit = "junit:junit:" + Versions.junitVersion;
        public static final String extJunit = "androidx.test.ext:junit:" + Versions.extJunitVersion;
        public static final String mockito = "org.mockito:mockito-core:" + Versions.mockitoVersion;
        public static final String espresso = "androidx.test.espresso:espresso-core:" + Versions.espressoVersion;
        public static final String navigation = "androidx.navigation:navigation-testing:" + Versions.navVersion;
        public static final String hilt = "com.google.dagger:hilt-android-testing:" + Versions.hiltVersion;
        public static final String hiltCompiler = "com.google.dagger:hilt-compiler:" + Versions.hiltVersion;
    }

    public static final class Others {
        public static final String jetbrainsAnno  = "org.jetbrains:annotations:" + Versions.jetbrainsAnnoVersion;
        public static final String desugar = "com.android.tools:desugar_jdk_libs:" + Versions.desugarVersion;
    }
}

public final class Config {
    public static final class Android {
        public static final int minSdk = 21;
        public static final int targetSdk = 33;
        public static final int compileSdk = 33;

        public static final String testRunner = "androidx.test.runner.AndroidJUnitRunner";
    }

    public static final class Plugins {
        public static final String android = "com.android.application";
        public static final String androidLibrary = "com.android.library";
        public static final String javaLibrary = "java-library";
        public static final String dagger = "com.google.dagger.hilt.android";
    }
}
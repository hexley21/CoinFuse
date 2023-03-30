package com.hxl.presentation.fakes;

import java.util.Random;
import java.util.UUID;

public class AppTestConstants {
    // Coins
    public static final int SIZE = 5;
    public static final String ID = "bitcoin";
    // Prefs

    // Defaults
    public static final int DEF_INT = 321;
    public static final boolean DEF_BOOL = false;
    public static final String DEF_STR = "default";
    // Values
    public static final int VAL_INT = 123;
    public static final boolean VAL_BOOL = true;
    public static final String VAL_STR = "test-str";
    // Randoms
    private static final Random random = new Random();
    public static String randomName() {
        return UUID.randomUUID().toString();
    }
    public static int randomInt() {
        return random.nextInt();
    }
    public static Double randomDouble() {
        return random.nextDouble();
    }
    public static float randomFloat() {
        return random.nextFloat();
    }

}

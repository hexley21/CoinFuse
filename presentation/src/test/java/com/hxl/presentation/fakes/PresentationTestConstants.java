package com.hxl.presentation.fakes;

import java.util.Random;
import java.util.UUID;

public class PresentationTestConstants {
    // Coins
    public static final int SIZE = 5;
    public static final String ID = "bitcoin";
    // Prefs

    // Defaults
    public static final boolean DEF_BOOL = false;
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

    public static long randomLong() {
        return random.nextLong();
    }
    public static boolean randomBoolean() {
        return random.nextBoolean();
    }

}

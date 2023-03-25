package com.hxl.remote.fake;

import java.util.Random;
import java.util.UUID;

public class TestConstants {
    public static final String ASSET_URL = "https://assets/%s.png";
    public static final String ID = "bitcoin";
    public static final int SIZE = 5;
    public static final int LIMIT = 3;
    public static final int OFFSET = 12;
    public static final long TIMESTAMP = 8L;

    private static final Random random = new Random();
    public static String randomName() {
        return UUID.randomUUID().toString();
    }
    public static String randomInt() {
        return String.valueOf(random.nextInt());
    }
    public static String randomDouble() {
        return String.valueOf(random.nextDouble());
    }
    public static String randomFloat() {
        return String.valueOf(random.nextFloat());
    }

}

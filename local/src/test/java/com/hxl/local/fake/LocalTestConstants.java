package com.hxl.local.fake;

import java.util.Random;
import java.util.UUID;

public class LocalTestConstants {
    public static final String ASSET_URL = "https://assets/%s.png";
    public static final String ID = "bitcoin";
    public static final String[] KEYS = new String[] {"bitcoin", "ethereum", "monero", "cardano", "solana"};
    public static final int SIZE = 5;
    public static final int LIMIT = 3;
    public static final int OFFSET = 2;
    public static final long TIMESTAMP = 8L;


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
    public static Float randomFloat() {
        return random.nextFloat();
    }
}

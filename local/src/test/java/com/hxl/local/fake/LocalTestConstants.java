package com.hxl.local.fake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class LocalTestConstants {
    // Coin Local
    public static final int LIMIT = 3;
    public static final int OFFSET = 2;
    public static final long TIMESTAMP = 8L;
    public static final String ASSET_URL = "https://assets/%s.png";
    public static final String ID = "bitcoin";
    public static final String[] KEYS = new String[] {"bitcoin", "bitcoin-gold", "ethereum", "monero", "cardano", "solana"};
    public static final List<String> IDS = Arrays.stream(Arrays.copyOfRange(KEYS, OFFSET, OFFSET+LIMIT)).collect(Collectors.toList());
    public static final int IDS_LENGTH = IDS.size();

    // Preference Local
    public static final String SHARED_PREF_NAME = "test-prefs";
    // Keys
    public static final String KEY_INT = "int";
    public static final String KEY_BOOL = "bool";
    public static final String KEY_STR = "str";
    // Values
    public static final int VAL_INT = 123;
    public static final boolean VAL_BOOL = true;
    public static final String VAL_STR = "test-str";
    // Default values
    public static final int DEF_INT = 321;
    public static final boolean DEF_BOOL = false;
    public static final String DEF_STR = "default";

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

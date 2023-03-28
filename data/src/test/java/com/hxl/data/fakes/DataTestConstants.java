package com.hxl.data.fakes;

import com.hxl.domain.model.Coin;

import java.util.Random;
import java.util.UUID;

public class DataTestConstants {
    // Coin Repository
    public static final String ID =  "bitcoin";
    public static final String ASSET_URL = "https://assets/%s.png";
    public static final int SIZE = 5;
    public static final Long TIMESTAMP =  8L;
    public static final Coin COIN = new Coin(
            ID,
            1,
            "BTC",
            "Bitcoin",
            1D,
            2D,
            3D,
            4D,
            5D,
            6F,
            7D,
            "bitcoin.com",
            TIMESTAMP,
            String.format(ASSET_URL, "btc")
    );
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

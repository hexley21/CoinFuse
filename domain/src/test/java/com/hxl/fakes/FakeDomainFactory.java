package com.hxl.fakes;

import com.hxl.domain.model.Coin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class FakeDomainFactory {

    public static List<Coin> getFakeCoins(int size) {
        List<Coin> fakeCoins = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fakeCoins.add(getCoin());
        }
        return fakeCoins;
    }

    public static Coin getCoin(String id) {
        return new Coin(
                id,             // id
                randomInt(),    // rank
                randomName(),   // symbol
                randomName(),   // name
                randomDouble(), // supply
                randomDouble(), // maxSupply
                randomDouble(), // marketCapUsd
                randomDouble(), // volumeUsd24Hr
                randomDouble(), // priceUsd
                randomFloat(),  // changePercent24Hr
                randomDouble(), // vwap24Hr
                randomName(),   // explorer
                randomLong(),   // timestamp
                randomName()    // img
        );
    }

    public static Coin getCoin() {
        return getCoin(randomName());
    }

    private static final Random random = new Random();

    private static String randomName() {
        return UUID.randomUUID().toString();
    }
    private static int randomInt() {
        return random.nextInt();
    }
    private static long randomLong() {
        return random.nextLong();
    }
    private static double randomDouble() {
        return random.nextDouble();
    }
    private static float randomFloat() {
        return random.nextFloat();
    }
}

package com.hxl.cryptonumismatist.conf.fake;

import com.hxl.domain.model.Coin;
import com.hxl.domain.model.History;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class FakeDataFactory {

    public static List<Coin> getFakeCoins(int size) {
        List<Coin> fakeCoins = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fakeCoins.add(getCoin());
        }
        return fakeCoins;
    }

    public static List<History> getFakeHistory(int size) {
        List<History> fakeHistory = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fakeHistory.add(
                    new History(
                            randomDouble(),
                            randomDouble().longValue()
                    )
            );
        }
        return fakeHistory;
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
                8L,             // timestamp
                randomName()    // img
        );
    }

    public static final Coin COIN = new Coin(
            "bitcoin",
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
            8L,
            String.format("https://assets/%s.png", "btc")
    );

    public static final Coin NULL_COIN = new Coin(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    );

    public static Coin getCoin() {
        return getCoin(randomName());
    }

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


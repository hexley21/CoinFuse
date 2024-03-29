package com.hxl.fakes;

import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.domain.model.Exchange;
import com.hxl.domain.model.Trade;

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

    public static List<CoinPriceHistory> getFakeHistory(int size) {
        List<CoinPriceHistory> fakeCoinPriceHistory = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fakeCoinPriceHistory.add(
                    new CoinPriceHistory(
                            randomDouble(),
                            randomLong()
                    )
            );
        }
        return fakeCoinPriceHistory;
    }

    public static List<Exchange> getFakeExchanges(int size) {
        List<Exchange> fakeExchanges = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fakeExchanges.add(
                    getExchange()
            );
        }

        return fakeExchanges;
    }

    public static List<Trade> getFakeTrades(int size) {
        List<Trade> fakeTrades = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fakeTrades.add(
                    getTrade()
            );
        }

        return fakeTrades;
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

    public static Exchange getExchange() {
        return new Exchange(
                randomName(),
                randomName(),
                randomInt(),
                randomDouble(),
                randomDouble(),
                randomInt(),
                randomBoolean(),
                randomName(),
                randomLong()
        );
    }

    private static final Random random = new Random();

    public static Trade getTrade() {
        return new Trade(
                randomName(),
                randomInt(),
                randomName(),
                randomName(),
                randomName(),
                randomName(),
                randomDouble(),
                randomDouble(),
                randomDouble(),
                randomDouble(),
                randomInt(),
                randomLong()
        );
    }

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
    private static boolean randomBoolean() {
        return random.nextBoolean();
    }
}

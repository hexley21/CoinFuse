package com.hxl.data.fakes;

import static com.hxl.data.fakes.DataTestConstants.*;

import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.domain.model.ValueAndTimestamp;

import java.util.ArrayList;
import java.util.List;


public class FakeDataFactory {

    public static List<Coin> getFakeCoins(int size) {
        List<Coin> fakeCoins = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fakeCoins.add(getCoin());
        }
        return fakeCoins;
    }

    public static List<CoinPriceHistory> getFakePriceHistory(int size) {
        List<CoinPriceHistory> fakeCoinPriceHistory = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fakeCoinPriceHistory.add(
                    new CoinPriceHistory(
                            randomDouble(),
                            randomDouble().longValue()
                    )
            );
        }
        return fakeCoinPriceHistory;
    }

    public static List<ValueAndTimestamp<String>> getFakeSearchQueries(int size) {
        List<ValueAndTimestamp<String>> fakeSearchQueries = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fakeSearchQueries.add(
                    new ValueAndTimestamp<>(
                            randomName(),
                            TIMESTAMP
                    )
            );
        }
        return fakeSearchQueries;
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

    public static Coin getCoin() {
        return getCoin(randomName());
    }
}

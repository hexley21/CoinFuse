package com.hxl.data.fakes;

import static com.hxl.data.fakes.DataTestConstants.*;

import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.domain.model.Exchange;
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

    public static List<Coin> getFakeCoins(List<String> ids) {
        List<Coin> fakeCoins = new ArrayList<>();
        for (String i: ids) {
            fakeCoins.add(getCoin(i));
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

    public static List<ValueAndTimestamp<String>> getFakeSearchQueries(List<String> ids) {
        List<ValueAndTimestamp<String>> fakeSearchQueries = new ArrayList<>();
        for (String i: ids) {
            fakeSearchQueries.add(
                    new ValueAndTimestamp<>(
                            i,
                            TIMESTAMP
                    )
            );
        }
        return fakeSearchQueries;
    }

    public static List<Exchange> getFakeExchanges(int size) {
        List<Exchange> fakeExchanges = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fakeExchanges.add(getExchange());
        }

        return fakeExchanges;
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

    public static Exchange getExchange(String exchangeId) {
        return new Exchange(
                exchangeId,
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

    public static Exchange getExchange() {
        return getExchange(randomName());
    }
}

package com.hxl.presentation.fakes;

import static com.hxl.presentation.fakes.PresentationTestConstants.randomBoolean;
import static com.hxl.presentation.fakes.PresentationTestConstants.randomDouble;
import static com.hxl.presentation.fakes.PresentationTestConstants.randomFloat;
import static com.hxl.presentation.fakes.PresentationTestConstants.randomInt;
import static com.hxl.presentation.fakes.PresentationTestConstants.randomLong;
import static com.hxl.presentation.fakes.PresentationTestConstants.randomName;

import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.domain.model.Exchange;
import com.hxl.domain.model.Trade;

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

    public static List<CoinPriceHistory> getFakeHistory(int size) {
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

    public static List<Exchange> getFakeExchanges(int size) {
        List<Exchange> fakeExchanges = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fakeExchanges.add(getExchange());
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
                (long) randomInt()
        );
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

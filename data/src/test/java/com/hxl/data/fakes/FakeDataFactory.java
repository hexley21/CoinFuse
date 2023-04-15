package com.hxl.data.fakes;

import static com.hxl.data.fakes.DataTestConstants.*;

import com.hxl.domain.model.Coin;
import com.hxl.domain.model.History;

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

    public static Coin getCoin() {
        return getCoin(randomName());
    }
}

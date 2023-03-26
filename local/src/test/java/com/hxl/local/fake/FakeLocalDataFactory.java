package com.hxl.local.fake;

import static com.hxl.local.fake.LocalTestConstants.*;

import com.hxl.domain.model.Coin;
import com.hxl.local.model.CoinEntity;

import java.util.ArrayList;
import java.util.List;

public class FakeLocalDataFactory {

    public static List<Coin> getFakeCoins(String[] ids) {
        List<Coin> fakeCoins = new ArrayList<>();
        for (String id : ids) {
            fakeCoins.add(getCoin(id));
        }
        return fakeCoins;
    }

    public static List<Coin> getFakeCoins(int size) {
        List<Coin> fakeCoins = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fakeCoins.add(getCoin());
        }
        return fakeCoins;
    }

    public static List<CoinEntity> getFakeData(String[] ids) {
        List<CoinEntity> fakeData = new ArrayList<>();
        for (String id : ids) {
            fakeData.add(getCoinEntity(id));
        }
        return fakeData;
    }

    public static List<CoinEntity> getFakeData(int size) {
        List<CoinEntity> fakeData = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fakeData.add(getCoinEntity());
        }
        return fakeData;
    }

    public static CoinEntity getCoinEntity(String id) {
        return new CoinEntity(
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
                TIMESTAMP,      // timestamp
                String.format(ASSET_URL, id)    // img
        );
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
                TIMESTAMP,      // timestamp
                String.format(ASSET_URL, id)    // img
        );
    }

    public static CoinEntity getCoinEntity() {
        return getCoinEntity(randomName());
    }

    public static Coin getCoin() {
        return getCoin(randomName());
    }
}

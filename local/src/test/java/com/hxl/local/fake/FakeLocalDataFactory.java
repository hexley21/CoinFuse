package com.hxl.local.fake;

import static com.hxl.local.fake.LocalTestConstants.*;

import com.hxl.domain.model.Coin;
import com.hxl.domain.model.Exchange;
import com.hxl.domain.model.ValueAndTimestamp;
import com.hxl.local.coin.model.CoinEntity;
import com.hxl.local.coin.model.CoinSearchEntity;
import com.hxl.local.exchange.model.ExchangeEntity;

import java.util.ArrayList;
import java.util.List;

public class FakeLocalDataFactory {

    public static List<Coin> getFakeCoins(String[] ids) {
        List<Coin> fakeCoins = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            fakeCoins.add(getCoin(ids[i], i));
        }
        return fakeCoins;
    }

    public static List<CoinSearchEntity> getFakeCoinSearchEntity(String[] ids) {
        List<CoinSearchEntity> fakeSearchEntities = new ArrayList<>();
        for (String i: ids) {
            fakeSearchEntities.add(getCoinSearchEntity(i));
        }
        return fakeSearchEntities;
    }

    public static List<CoinEntity> getFakeData(String[] ids) {
        List<CoinEntity> fakeData = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            fakeData.add(getCoinEntity(ids[i], i));
        }
        return fakeData;
    }

    public static Exchange[] getFakeExchanges(int size) {
        Exchange[] fakeExchanges = new Exchange[size];
        for (int i = 0; i < size; i++) {
            fakeExchanges[i] = getExchange();
        }

        return fakeExchanges;
    }

    public static ExchangeEntity[] getFakeExchangeEntities(int size) {
        ExchangeEntity[] fakeEntities = new ExchangeEntity[size];
        for (int i = 0; i < size; i++) {
            fakeEntities[i] = getExchangeEntity();
        }

        return fakeEntities;
    }

    public static CoinEntity getCoinEntity(String id, int rank) {
        return new CoinEntity(
                id,             // id
                rank,           // rank
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

    public static Coin getCoin(String id, int rank) {
        return new Coin(
                id,             // id
                rank,           // rank
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

    public static ExchangeEntity getExchangeEntity(String exchangeId) {
        return new ExchangeEntity(
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

    public static ExchangeEntity getExchangeEntity() {
        return getExchangeEntity(randomName());
    }

    public static CoinSearchEntity getCoinSearchEntity(String query) {
        return new CoinSearchEntity(
                query,
                TIMESTAMP
        );
    }

    public static ValueAndTimestamp<String> getSearchQuery(String query) {
        return new ValueAndTimestamp<>(
                query,
                TIMESTAMP
        );
    }
}

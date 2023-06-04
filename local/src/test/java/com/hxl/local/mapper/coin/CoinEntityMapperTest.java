package com.hxl.local.mapper.coin;

import static com.hxl.local.fake.LocalTestConstants.*;
import static org.junit.Assert.assertEquals;

import com.hxl.domain.model.Coin;
import com.hxl.local.coin.mapper.CoinEntityMapper;
import com.hxl.local.coin.model.CoinEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CoinEntityMapperTest {

    // Arrange
    final CoinEntity entity = new CoinEntity(
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
    final Coin coin = new Coin(
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

    @Test
    public void mapFromEntityReturnsValidModelObject() {
        // Act
        Coin result = CoinEntityMapper.mapFromEntity(entity);
        // Assert
        assertEquals(result.id, coin.id);
        assertEquals(result.rank, coin.rank);
        assertEquals(result.symbol, coin.symbol);
        assertEquals(result.name, coin.name);
        assertEquals(result.supply, coin.supply);
        assertEquals(result.maxSupply, coin.maxSupply);
        assertEquals(result.marketCapUsd, coin.marketCapUsd);
        assertEquals(result.volumeUsd24Hr, coin.volumeUsd24Hr);
        assertEquals(result.priceUsd, coin.priceUsd);
        assertEquals(result.changePercent24Hr, coin.changePercent24Hr);
        assertEquals(result.vwap24Hr, coin.vwap24Hr);
        assertEquals(result.explorer, coin.explorer);
        assertEquals(result.timestamp, coin.timestamp);
        assertEquals(result.img, coin.img);
    }

    @Test
    public void mapToEntityReturnsValidEntityObject() {
        // Act
        CoinEntity result = CoinEntityMapper.mapToEntity(coin);
        // Assert
        assertEquals(result.id, entity.id);
        assertEquals(result.rank, entity.rank);
        assertEquals(result.symbol, entity.symbol);
        assertEquals(result.name, entity.name);
        assertEquals(result.supply, entity.supply);
        assertEquals(result.maxSupply, entity.maxSupply);
        assertEquals(result.marketCapUsd, entity.marketCapUsd);
        assertEquals(result.volumeUsd24Hr, entity.volumeUsd24Hr);
        assertEquals(result.priceUsd, entity.priceUsd);
        assertEquals(result.changePercent24Hr, entity.changePercent24Hr);
        assertEquals(result.vwap24Hr, entity.vwap24Hr);
        assertEquals(result.explorer, entity.explorer);
        assertEquals(result.timestamp, entity.timestamp);
        assertEquals(result.img, entity.img);
    }
}

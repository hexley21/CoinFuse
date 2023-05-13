package com.hxl.remote.mapper;

import com.hxl.domain.model.Coin;
import com.hxl.remote.coin.mapper.CoinDTOMapper;
import com.hxl.remote.coin.model.CoinDTO;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.hxl.remote.fake.RemoteTestConstants.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CoinDTOMapperTest {

    CoinDTOMapper mapper;

    @Before
    public void setUp() {
        mapper = new CoinDTOMapper("https://assets/%s.png");
    }

    @Test
    public void mapDtoToModelReturnsConvertedObject() {
        // Arrange
        CoinDTO dto = new CoinDTO(
                ID,
                "1",
                "BTC",
                "Bitcoin",
                "1D",
                "2D",
                "3D",
                "4D",
                "5D",
                "6F",
                "7D",
                "bitcoin.com"
        );
        Coin coin = new Coin(
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
        // Act
        Coin result = mapper.mapFromDTO(dto, TIMESTAMP);
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
}

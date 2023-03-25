package com.hxl.remote.mapper;

import com.hxl.domain.model.Coin;
import com.hxl.remote.fake.FakeRemoteData;
import com.hxl.remote.model.CoinDTO;
import com.hxl.remote.model.CoinDTOMapper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoinDTOMapperTest {

    String assetUrl = "https://source/%s.img";
    CoinDTOMapper mapper;

    @Before
    public void setUp() {
        mapper = new CoinDTOMapper(assetUrl);
    }

    @Test
    public void mapDtoToModelReturnsConvertedObject() {
        // Arrange
        CoinDTO dto = new FakeRemoteData().getFakeCoin();
        // Act
        Coin coin = mapper.mapFromDTO(dto, System.currentTimeMillis());
        // Assert
        assertNotNull(coin);
        assertEquals(coin.img, String.format(assetUrl, dto.symbol.toLowerCase()));
    }
}

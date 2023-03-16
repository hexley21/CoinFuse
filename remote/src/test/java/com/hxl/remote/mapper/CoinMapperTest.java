package com.hxl.remote.mapper;

import com.hxl.domain.model.Coin;
import com.hxl.remote.fake.FakeData;
import com.hxl.remote.model.CoinDTO;
import com.hxl.remote.model.CoinMapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class CoinMapperTest {

    String assetUrl = "https://source/%s.img";
    CoinMapper mapper;

    @Before
    public void setUp() {
        mapper = new CoinMapper(assetUrl);
    }

    @Test
    public void mapDtoToModelReturnsConvertedObject() {
        // Arrange
        CoinDTO dto = new FakeData().getFakeCoin();
        // Act
        Coin coin = mapper.mapFromDTO(dto);
        // Assert
        assertNotNull(coin);
        assertEquals(coin.img, String.format(assetUrl, dto.symbol.toLowerCase()));
    }
}

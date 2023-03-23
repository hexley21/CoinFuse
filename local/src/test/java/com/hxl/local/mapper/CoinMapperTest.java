package com.hxl.local.mapper;

import static org.junit.Assert.assertNotNull;

import com.hxl.domain.model.Coin;
import com.hxl.local.fake.FakeData;
import com.hxl.local.model.CoinEntity;
import com.hxl.local.model.CoinEntityMapper;

import org.junit.Test;

public class CoinMapperTest {

    @Test
    public void mapDtoToModelReturnsConvertedObject() {
        // Arrange
        CoinEntity entity = new FakeData().getFakeEntity();
        Coin coin = new FakeData().getFakeCoin();
        // Act
        Coin mappedCoin = CoinEntityMapper.mapFromEntity(entity);
        CoinEntity mappedEntity = CoinEntityMapper.mapToEntity(coin);
        // Assert
        assertNotNull(mappedCoin);
        assertNotNull(mappedEntity);
    }
}

package com.hxl.local.mapper.coin;

import com.hxl.domain.model.ValueAndTimestamp;
import com.hxl.local.model.coin.CoinSearchEntity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.hxl.local.fake.FakeLocalDataFactory.getCoinSearchEntity;
import static com.hxl.local.fake.FakeLocalDataFactory.getSearchQuery;
import static com.hxl.local.fake.LocalTestConstants.ID;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class CoinSearchEntityMapperTest {

    @Test
    public void mapFromEntityConvertsEntityToValidModel() {
        // Arrange
        CoinSearchEntity coinSearchEntity = getCoinSearchEntity(ID);
        ValueAndTimestamp<String> searchQuery = getSearchQuery(ID);
        // Act
        ValueAndTimestamp<String> result = CoinSearchEntityMapper.mapFromEntity(coinSearchEntity);
        // Assert
        assertEquals(searchQuery.value, result.value);
        assertEquals(searchQuery.timestamp, result.timestamp);
    }

    @Test
    public void mapToEntityConvertsModelToValidEntity() {
        // Arrange
        ValueAndTimestamp<String> searchQuery = getSearchQuery(ID);
        CoinSearchEntity coinSearchEntity = getCoinSearchEntity(ID);
        // Act
        CoinSearchEntity result = CoinSearchEntityMapper.mapToEntity(searchQuery);
        // Assert
        assertEquals(coinSearchEntity.query, result.query);
        assertEquals(coinSearchEntity.timestamp, result.timestamp);
    }
}

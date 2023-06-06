package com.hxl.local.mapper.exchanges;

import static org.junit.Assert.assertEquals;

import com.hxl.domain.model.Exchange;
import com.hxl.local.exchange.mapper.ExchangeEntityMapper;
import com.hxl.local.exchange.model.ExchangeEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ExchangeEntityMapperTest {

    private final ExchangeEntity fakeEntity = new ExchangeEntity(
            "exchangeId",
            "name",
            1,
            1.1D,
            2.2D,
            3,
            true,
            "exchangeUrl",
            4L
    );
    private final Exchange fakeModel = new Exchange(
            "exchangeId",
            "name",
            1,
            1.1D,
            2.2D,
            3,
            true,
            "exchangeUrl",
            4L
    );

    @Test
    public void mapFromEntityReturnsValidModel() {
        // Act
        Exchange converted = ExchangeEntityMapper.mapFromEntity(fakeEntity);
        // Assert
        assertEquals(fakeModel.exchangeId, converted.exchangeId);
        assertEquals(fakeModel.name, converted.name);
        assertEquals(fakeModel.rank, converted.rank);
        assertEquals(fakeModel.percentTotalVolume, converted.percentTotalVolume);
        assertEquals(fakeModel.volumeUsd, converted.volumeUsd);
        assertEquals(fakeModel.tradingPairs, converted.tradingPairs);
        assertEquals(fakeModel.socket, converted.socket);
        assertEquals(fakeModel.exchangeUrl, converted.exchangeUrl);
        assertEquals(fakeModel.updated, converted.updated);
    }

    @Test
    public void mapToEntityReturnsValidEntity() {
        // Act
        ExchangeEntity converted = ExchangeEntityMapper.mapToEntity(fakeModel);
        // Assert
        assertEquals(fakeEntity.exchangeId, converted.exchangeId);
        assertEquals(fakeEntity.name, converted.name);
        assertEquals(fakeEntity.rank, converted.rank);
        assertEquals(fakeEntity.percentTotalVolume, converted.percentTotalVolume);
        assertEquals(fakeEntity.volumeUsd, converted.volumeUsd);
        assertEquals(fakeEntity.tradingPairs, converted.tradingPairs);
        assertEquals(fakeEntity.socket, converted.socket);
        assertEquals(fakeEntity.exchangeUrl, converted.exchangeUrl);
        assertEquals(fakeEntity.updated, converted.updated);
    }
}

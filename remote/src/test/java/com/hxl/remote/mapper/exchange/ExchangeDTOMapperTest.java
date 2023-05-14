package com.hxl.remote.mapper.exchange;

import static org.junit.Assert.assertEquals;

import com.hxl.domain.model.Exchange;
import com.hxl.remote.exchange.mapper.ExchangeDTOMapper;
import com.hxl.remote.exchange.model.ExchangeDTO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ExchangeDTOMapperTest {

    private final ExchangeDTO fakeDTO = new ExchangeDTO(
            "exchangeId",
            "name",
            "1",
            "1.1",
            "2.2D",
            "3",
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
    public void mapFromDTOConvertsToValidModel() {
        // Act
        Exchange converted = ExchangeDTOMapper.mapFromDTO(fakeDTO);
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
}

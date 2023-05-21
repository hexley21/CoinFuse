package com.hxl.remote.mapper.coin;

import static org.junit.Assert.assertEquals;

import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.remote.coin.mapper.CoinPriceHistoryDTOMapper;
import com.hxl.remote.coin.model.HistoryDTO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CoinPriceHistoryDTOMapperTest {

    @Test
    public void mapHistoryDtoToModelReturnsConvertedObject() {
        // Arrange
        HistoryDTO dto = new HistoryDTO("1", 2L, "3");
        CoinPriceHistory model = new CoinPriceHistory(1D, 2L);
        // Act
        CoinPriceHistory result = CoinPriceHistoryDTOMapper.mapFromDTO(dto);
        // Assert
        assertEquals(model.priceUsd, result.priceUsd);
        assertEquals(model.time, result.time);
    }
}

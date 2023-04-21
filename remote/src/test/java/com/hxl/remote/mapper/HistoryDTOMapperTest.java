package com.hxl.remote.mapper;

import static org.junit.Assert.assertEquals;

import com.hxl.domain.model.History;
import com.hxl.remote.model.HistoryDTO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoryDTOMapperTest {

    @Test
    public void mapHistoryDtoToModelReturnsConvertedObject() {
        // Arrange
        HistoryDTO dto = new HistoryDTO("1", 2L, "3");
        History model = new History(1D, 2L);
        // Act
        History result = HistoryDTOMapper.mapFromDTO(dto);
        // Assert
        assertEquals(model.priceUsd, result.priceUsd);
        assertEquals(model.time, result.time);
    }
}

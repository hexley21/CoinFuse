package com.hxl.remote.mapper.exchange;

import static org.junit.Assert.assertEquals;

import com.hxl.domain.model.Trade;
import com.hxl.remote.exchange.mapper.TradeDTOMapper;
import com.hxl.remote.exchange.model.TradeDTO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TradeDTOMapperTest {

    final TradeDTO fakeDTO = new TradeDTO(
            "binance",
            "1",
            "BTC",
            "bitcoin",
            "ETH",
            "ethereum",
            "1.23D",
            "2.3",
            "3.45",
            "4.567",
            "500",
            600L
    );

    final Trade fakeModel = new Trade(
            "binance",
            1,
            "BTC",
            "bitcoin",
            "ETH",
            "ethereum",
            1.23D,
            2.3D,
            3.45D,
            4.567D,
            500,
            600L
    );

    @Test
    public void mapFromDtoConvertsToValidObject() {
        // Act
        Trade converted = TradeDTOMapper.mapFromDto(fakeDTO);
        // Assert
        assertEquals(fakeModel.exchangeId, converted.exchangeId);
        assertEquals(fakeModel.rank, converted.rank);
        assertEquals(fakeModel.baseSymbol, converted.baseSymbol);
        assertEquals(fakeModel.baseId, converted.baseId);
        assertEquals(fakeModel.quoteSymbol, converted.quoteSymbol);
        assertEquals(fakeModel.quoteId, converted.quoteId);
        assertEquals(fakeModel.priceQuote, converted.priceQuote);
        assertEquals(fakeModel.priceUsd, converted.priceUsd);
        assertEquals(fakeModel.volumeUsd24Hr, converted.volumeUsd24Hr);
        assertEquals(fakeModel.percentExchangeVolume, converted.percentExchangeVolume);
        assertEquals(fakeModel.tradesCount24Hr, converted.tradesCount24Hr);
        assertEquals(fakeModel.updated, converted.updated);
    }
}

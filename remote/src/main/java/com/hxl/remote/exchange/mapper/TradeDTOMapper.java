package com.hxl.remote.exchange.mapper;

import com.hxl.domain.model.Trade;
import com.hxl.remote.exchange.model.TradeDTO;

public final class TradeDTOMapper {

    public static Trade mapFromDto(TradeDTO dto) {
        return new Trade(
                dto.exchangeId,
                dto.rank == null ? null : Integer.parseInt(dto.rank),
                dto.baseSymbol,
                dto.baseId,
                dto.quoteSymbol,
                dto.quoteId,
                dto.priceQuote == null ? null : Double.parseDouble(dto.priceQuote),
                dto.priceUsd == null ? null : Double.parseDouble(dto.priceUsd),
                dto.volumeUsd24Hr == null ? null : Double.parseDouble(dto.volumeUsd24Hr),
                dto.percentExchangeVolume == null ? null : Double.parseDouble(dto.percentExchangeVolume),
                dto.tradesCount24Hr == null ? null : Integer.parseInt(dto.tradesCount24Hr),
                dto.updated
        );
    }
}

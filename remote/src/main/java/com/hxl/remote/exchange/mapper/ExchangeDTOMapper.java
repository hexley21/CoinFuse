package com.hxl.remote.exchange.mapper;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import com.hxl.domain.model.Exchange;
import com.hxl.remote.exchange.model.ExchangeDTO;

public class ExchangeDTOMapper {

    public static Exchange mapFromDTO(ExchangeDTO dto) {
        return new Exchange(
                dto.exchangeId,
                dto.name,
                parseInt(dto.rank),
                parseDouble(dto.percentTotalVolume),
                parseDouble(dto.volumeUsd),
                parseInt(dto.tradingPairs),
                dto.socket,
                dto.exchangeUrl,
                dto.updated
        );
    }

}
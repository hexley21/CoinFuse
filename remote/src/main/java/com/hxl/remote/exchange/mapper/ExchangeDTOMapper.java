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
                dto.rank == null ? null : parseInt(dto.rank),
                dto.percentTotalVolume == null ? null : parseDouble(dto.percentTotalVolume),
                dto.volumeUsd == null ? null : parseDouble(dto.volumeUsd),
                dto.tradingPairs == null ? null : parseInt(dto.tradingPairs),
                dto.socket,
                dto.exchangeUrl,
                dto.updated
        );
    }

}
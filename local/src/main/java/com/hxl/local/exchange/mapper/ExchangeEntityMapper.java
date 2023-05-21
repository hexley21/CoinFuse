package com.hxl.local.exchange.mapper;

import com.hxl.domain.model.Exchange;
import com.hxl.local.exchange.model.ExchangeEntity;

public final class ExchangeEntityMapper {

    public static Exchange mapFromEntity(ExchangeEntity entity) {
        return new Exchange(
                entity.exchangeId,
                entity.name,
                entity.rank,
                entity.percentTotalVolume,
                entity.volumeUsd,
                entity.tradingPairs,
                entity.socket,
                entity.exchangeUrl,
                entity.updated
        );
    }

    public static ExchangeEntity mapToEntity(Exchange model) {
        return new ExchangeEntity(
                model.exchangeId,
                model.name,
                model.rank,
                model.percentTotalVolume,
                model.volumeUsd,
                model.tradingPairs,
                model.socket,
                model.exchangeUrl,
                model.updated
        );
    }

}

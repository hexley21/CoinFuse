package com.hxl.local.mapper;

import androidx.annotation.NonNull;

import com.hxl.domain.model.Coin;
import com.hxl.local.model.coin.CoinEntity;

public class CoinEntityMapper {

    public static Coin mapFromEntity(@NonNull CoinEntity entity) {
        return new Coin(
                entity.id,
                entity.rank,
                entity.symbol,
                entity.name,
                entity.supply,
                entity.maxSupply,
                entity.marketCapUsd,
                entity.volumeUsd24Hr,
                entity.priceUsd,
                entity.changePercent24Hr,
                entity.vwap24Hr,
                entity.explorer,
                entity.timestamp,
                entity.img
        );
    }

    public static CoinEntity mapToEntity(@NonNull Coin coin) {
        return new CoinEntity(
                coin.id,
                coin.rank,
                coin.symbol,
                coin.name,
                coin.supply,
                coin.maxSupply,
                coin.marketCapUsd,
                coin.volumeUsd24Hr,
                coin.priceUsd,
                coin.changePercent24Hr,
                coin.vwap24Hr,
                coin.explorer,
                coin.timestamp,
                coin.img
        );
    }
}

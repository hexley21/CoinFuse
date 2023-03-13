package com.hxl.remote.model;

import com.hxl.domain.model.Coin;

public class CoinMapper {
    public static Coin mapFromDTO(CoinDTO dto) {
        return new Coin(
                dto.id,
                Integer.parseInt(dto.rank),
                dto.symbol,
                dto.name,
                Integer.parseInt(dto.supply),
                Integer.parseInt(dto.maxSupply),
                Double.parseDouble(dto.marketCapUsd),
                Double.parseDouble(dto.volumeUsd24Hr),
                Double.parseDouble(dto.priceUsd),
                Float.parseFloat(dto.changePercent24Hr),
                Double.parseDouble(dto.vwap24Hr),
                dto.explorer
        );
    }
}

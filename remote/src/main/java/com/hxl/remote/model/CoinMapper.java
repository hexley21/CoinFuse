package com.hxl.remote.model;

import com.hxl.domain.model.Coin;

public class CoinMapper {

    private final String assetLocation;
    public CoinMapper(String url) {
        this.assetLocation = url;
    }
    public Coin mapFromDTO(CoinDTO dto) {
        return new Coin(
                dto.id,
                Integer.parseInt(dto.rank),
                dto.symbol,
                dto.name,
                Double.parseDouble(dto.supply),
                checkNull(dto.maxSupply),
                Double.parseDouble(dto.marketCapUsd),
                Double.parseDouble(dto.volumeUsd24Hr),
                Double.parseDouble(dto.priceUsd),
                Float.parseFloat(dto.changePercent24Hr),
                checkNull(dto.vwap24Hr),
                dto.explorer,
                String.format(assetLocation, dto.symbol.toLowerCase())
        );
    }

    private static Double checkNull(String resp) {
        if (resp != null) {
            return Double.parseDouble(resp);
        }
        return null;
    }
}
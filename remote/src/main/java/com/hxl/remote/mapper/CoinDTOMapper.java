package com.hxl.remote.mapper;

import com.hxl.domain.model.Coin;
import com.hxl.remote.model.CoinDTO;

public class CoinDTOMapper {

    private final String assetLocation;
    public CoinDTOMapper(String url) {
        this.assetLocation = url;
    }
    public Coin mapFromDTO(CoinDTO dto, Long timestamp) {
        return new Coin(
                dto.id,
                Integer.parseInt(dto.rank),
                dto.symbol,
                dto.name,
                checkDouble(dto.supply),
                checkDouble(dto.maxSupply),
                checkDouble(dto.marketCapUsd),
                checkDouble(dto.volumeUsd24Hr),
                checkDouble(dto.priceUsd),
                checkFloat(dto.changePercent24Hr),
                checkDouble(dto.vwap24Hr),
                dto.explorer,
                timestamp,
                String.format(assetLocation, dto.symbol.toLowerCase())
        );
    }

    private static Double checkDouble(String resp) {
        if (resp != null) {
            return Double.parseDouble(resp);
        }
        return null;
    }

    private static Float checkFloat(String resp) {
        if (resp != null) {
            return Float.parseFloat(resp);
        }
        return null;
    }
}
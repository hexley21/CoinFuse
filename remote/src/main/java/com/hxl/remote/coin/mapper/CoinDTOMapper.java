package com.hxl.remote.coin.mapper;

import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;

import com.hxl.domain.model.Coin;
import com.hxl.remote.coin.model.CoinDTO;

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
                parseDouble(dto.supply),
                parseDouble(dto.maxSupply),
                parseDouble(dto.marketCapUsd),
                parseDouble(dto.volumeUsd24Hr),
                parseDouble(dto.priceUsd),
                parseFloat(dto.changePercent24Hr),
                parseDouble(dto.vwap24Hr),
                dto.explorer,
                timestamp,
                String.format(assetLocation, dto.symbol.toLowerCase())
        );
    }

}
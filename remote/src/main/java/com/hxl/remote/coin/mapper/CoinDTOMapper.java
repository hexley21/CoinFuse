package com.hxl.remote.coin.mapper;

import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

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
                parseInt(dto.rank),
                dto.symbol,
                dto.name,
                dto.supply == null ? null : parseDouble(dto.supply),
                dto.maxSupply == null ?  null : parseDouble(dto.maxSupply),
                dto.marketCapUsd == null ? null : parseDouble(dto.marketCapUsd),
                dto.volumeUsd24Hr == null ? null : parseDouble(dto.volumeUsd24Hr),
                dto.priceUsd == null ? null : parseDouble(dto.priceUsd),
                dto.changePercent24Hr == null ? null : parseFloat(dto.changePercent24Hr),
                dto.vwap24Hr == null ? null : parseDouble(dto.vwap24Hr),
                dto.explorer,
                timestamp,
                String.format(assetLocation, dto.symbol.toLowerCase())
        );
    }

}
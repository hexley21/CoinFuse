package com.hxl.remote.coin.mapper;

import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.remote.coin.model.HistoryDTO;

public class CoinPriceHistoryDTOMapper {

    public static CoinPriceHistory mapFromDTO(HistoryDTO dto) {
        return new CoinPriceHistory(
                checkDouble(dto.priceUsd),
                dto.time
        );
    }

    private static Double checkDouble(String resp) {
        if (resp != null) {
            return Double.parseDouble(resp);
        }
        return null;
    }
}

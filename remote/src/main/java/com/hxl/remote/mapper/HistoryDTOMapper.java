package com.hxl.remote.mapper;

import com.hxl.domain.model.Coin;
import com.hxl.domain.model.History;
import com.hxl.remote.model.CoinDTO;
import com.hxl.remote.model.HistoryDTO;

public class HistoryDTOMapper {

    public static History mapFromDTO(HistoryDTO dto) {
        return new History(
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

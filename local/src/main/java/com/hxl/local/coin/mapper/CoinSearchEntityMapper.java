package com.hxl.local.coin.mapper;

import androidx.annotation.NonNull;

import com.hxl.domain.model.ValueAndTimestamp;
import com.hxl.local.coin.model.CoinSearchEntity;

public class CoinSearchEntityMapper {

    public static ValueAndTimestamp<String> mapFromEntity(@NonNull CoinSearchEntity coinSearchHistory) {
        return new ValueAndTimestamp<>(coinSearchHistory.myValue, coinSearchHistory.timestamp);
    }

    public static CoinSearchEntity mapToEntity(@NonNull ValueAndTimestamp<String> searchQuery){
        return new CoinSearchEntity(searchQuery.value, searchQuery.timestamp);
    }
}

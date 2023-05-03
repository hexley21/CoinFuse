package com.hxl.local.mapper.coin;

import androidx.annotation.NonNull;

import com.hxl.domain.model.ValueAndTimestamp;
import com.hxl.local.model.coin.CoinSearchEntity;

public class CoinSearchEntityMapper {

    public static ValueAndTimestamp<String> mapFromEntity(@NonNull CoinSearchEntity coinSearchHistory) {
        return new ValueAndTimestamp<>(coinSearchHistory.myValue, coinSearchHistory.timestamp);
    }

    public static CoinSearchEntity mapToEntity(@NonNull ValueAndTimestamp<String> searchQuery){
        return new CoinSearchEntity(searchQuery.value, searchQuery.timestamp);
    }
}

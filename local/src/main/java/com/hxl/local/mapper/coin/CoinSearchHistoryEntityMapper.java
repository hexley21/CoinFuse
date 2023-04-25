package com.hxl.local.mapper.coin;

import androidx.annotation.NonNull;

import com.hxl.domain.model.SearchQuery;
import com.hxl.local.model.coin.CoinSearchHistoryEntity;

public class CoinSearchHistoryEntityMapper {

    public static SearchQuery mapFromEntity(@NonNull CoinSearchHistoryEntity coinSearchHistory) {
        return new SearchQuery(coinSearchHistory.query, coinSearchHistory.timestamp);
    }

    public static CoinSearchHistoryEntity mapToEntity(@NonNull SearchQuery searchQuery){
        return new CoinSearchHistoryEntity(searchQuery.query, searchQuery.timestamp);
    }
}

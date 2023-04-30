package com.hxl.local.mapper.coin;

import androidx.annotation.NonNull;

import com.hxl.domain.model.SearchQuery;
import com.hxl.local.model.coin.CoinSearchEntity;

public class CoinSearchEntityMapper {

    public static SearchQuery mapFromEntity(@NonNull CoinSearchEntity coinSearchHistory) {
        return new SearchQuery(coinSearchHistory.query, coinSearchHistory.timestamp);
    }

    public static CoinSearchEntity mapToEntity(@NonNull SearchQuery searchQuery){
        return new CoinSearchEntity(searchQuery.query, searchQuery.timestamp);
    }
}

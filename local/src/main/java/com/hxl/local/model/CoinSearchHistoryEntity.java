package com.hxl.local.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import static com.hxl.local.database.AppDatabase.COIN_SEARCH_HISTORY_TABLE_NAME;

@Entity(tableName = COIN_SEARCH_HISTORY_TABLE_NAME)
public class CoinSearchHistoryEntity extends SearchQueryTable {

    public CoinSearchHistoryEntity(@NonNull String query, long timestamp) {
        super(query, timestamp);
    }

}

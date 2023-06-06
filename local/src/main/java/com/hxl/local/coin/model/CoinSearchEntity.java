package com.hxl.local.coin.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import static com.hxl.local.room.AppDatabase.COIN_SEARCH_HISTORY_TABLE_NAME;

import com.hxl.local.room.ValueAndTimestampTable;

@Entity(tableName = COIN_SEARCH_HISTORY_TABLE_NAME)
public class CoinSearchEntity extends ValueAndTimestampTable<String> {

    public CoinSearchEntity(@NonNull String myValue, long timestamp) {
        super(myValue, timestamp);
    }

}

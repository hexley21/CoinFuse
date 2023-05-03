package com.hxl.local.model.coin;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import static com.hxl.local.database.AppDatabase.COIN_SEARCH_HISTORY_TABLE_NAME;

import com.hxl.local.model.ValueAndTimestampTable;

@Entity(tableName = COIN_SEARCH_HISTORY_TABLE_NAME)
public class CoinSearchEntity extends ValueAndTimestampTable<String> {

    public CoinSearchEntity(@NonNull String myValue, long timestamp) {
        super(myValue, timestamp);
    }

}

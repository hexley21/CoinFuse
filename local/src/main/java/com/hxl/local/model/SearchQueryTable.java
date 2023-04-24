package com.hxl.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

public abstract class SearchQueryTable {

    @NonNull
    @ColumnInfo(name = "query")
    String query;

    @ColumnInfo(name = "timestamp")
    public long timestamp;

    public SearchQueryTable(@NonNull String query, long timestamp) {
        this.query = query;
        this.timestamp = timestamp;
    }

}

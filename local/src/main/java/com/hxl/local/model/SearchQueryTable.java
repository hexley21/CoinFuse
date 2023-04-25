package com.hxl.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public abstract class SearchQueryTable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "query")
    public String query;

    @ColumnInfo(name = "timestamp")
    public long timestamp;

    public SearchQueryTable(@NonNull String query, long timestamp) {
        this.query = query;
        this.timestamp = timestamp;
    }

}

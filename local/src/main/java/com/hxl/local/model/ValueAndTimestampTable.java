package com.hxl.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public abstract class ValueAndTimestampTable<T> {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "myValue")
    public T myValue;

    @ColumnInfo(name = "timestamp")
    public long timestamp;

    public ValueAndTimestampTable(@NonNull T myValue, long timestamp) {
        this.myValue = myValue;
        this.timestamp = timestamp;
    }

}

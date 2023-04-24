package com.hxl.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.hxl.local.database.CoinDatabase;

@Entity(tableName = CoinDatabase.BOOKMARKS_TABLE_NAME)
public class BookmarkEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    public String id;

    @ColumnInfo(name = "timestamp")
    public long timestamp;

    public BookmarkEntity(@NonNull String id, long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }
}

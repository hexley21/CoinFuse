package com.hxl.local.model.coin;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.hxl.local.database.AppDatabase;

@Entity(tableName = AppDatabase.BOOKMARKS_TABLE_NAME)
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

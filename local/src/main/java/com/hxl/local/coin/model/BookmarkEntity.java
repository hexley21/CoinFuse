package com.hxl.local.coin.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.hxl.local.room.AppDatabase;
import com.hxl.local.room.ValueAndTimestampTable;

@Entity(tableName = AppDatabase.BOOKMARKS_TABLE_NAME)
public class BookmarkEntity extends ValueAndTimestampTable<String> {

    public BookmarkEntity(@NonNull String myValue, long timestamp) {
        super(myValue, timestamp);
    }
}

package com.hxl.local.model.coin;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.hxl.local.database.AppDatabase;
import com.hxl.local.model.ValueAndTimestampTable;

@Entity(tableName = AppDatabase.BOOKMARKS_TABLE_NAME)
public class BookmarkEntity extends ValueAndTimestampTable<String> {

    public BookmarkEntity(@NonNull String myValue, long timestamp) {
        super(myValue, timestamp);
    }
}

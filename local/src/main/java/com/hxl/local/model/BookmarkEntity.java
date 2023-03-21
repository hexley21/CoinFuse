package com.hxl.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.hxl.local.database.CoinDatabase;

@Entity(tableName = CoinDatabase.FAVOURITE_TABLE_NAME)
public class BookmarkEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.TEXT)
    public String id;

    @ColumnInfo(name = "date_added", typeAffinity = ColumnInfo.INTEGER)
    public long timeAdded;

    public BookmarkEntity(@NonNull String id, long timeAdded) {
        this.id = id;
        this.timeAdded = timeAdded;
    }
}
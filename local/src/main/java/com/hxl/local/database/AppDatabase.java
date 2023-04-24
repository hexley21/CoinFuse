package com.hxl.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.hxl.local.model.BookmarkEntity;
import com.hxl.local.model.CoinEntity;

@Database(
        entities = {CoinEntity.class, BookmarkEntity.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "crypto_numismatist.db";
    public static final String COIN_TABLE_NAME = "coins";
    public static final String BOOKMARKS_TABLE_NAME = "bookmarks";
    public static final String COIN_SEARCH_HISTORY_TABLE_NAME = "coin_search_history";

    public abstract CoinDao coinDao();
    public abstract BookmarkDao bookmarkDao();
}

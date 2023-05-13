package com.hxl.local.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.hxl.local.coin.dao.BookmarkDao;
import com.hxl.local.coin.dao.CoinDao;
import com.hxl.local.coin.dao.CoinSearchDao;
import com.hxl.local.coin.model.BookmarkEntity;
import com.hxl.local.coin.model.CoinEntity;
import com.hxl.local.coin.model.CoinSearchEntity;
import com.hxl.local.exchange.dao.ExchangeDao;
import com.hxl.local.exchange.model.ExchangeEntity;

@Database(
        entities = {CoinEntity.class, BookmarkEntity.class, CoinSearchEntity.class, ExchangeEntity.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "crypto_numismatist.db";
    public static final String COIN_TABLE_NAME = "coins";
    public static final String BOOKMARKS_TABLE_NAME = "bookmarks";
    public static final String COIN_SEARCH_HISTORY_TABLE_NAME = "coin_search_history";

    public static final String EXCHANGE_TABLE_NAME = "exchanges";

    public abstract CoinDao coinDao();
    public abstract BookmarkDao bookmarkDao();
    public abstract CoinSearchDao coinSearchDao();
    public abstract ExchangeDao exchangeDao();
}

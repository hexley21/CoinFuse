package com.hxl.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.hxl.local.model.CoinEntity;
import com.hxl.local.model.FavouriteEntity;

@Database(
        entities = {CoinEntity.class, FavouriteEntity.class},
        version = 1,
        exportSchema = false
)
public abstract class CoinDatabase extends RoomDatabase {
    public static final String DB_NAME = "crypto_numismatist.db";
    public static final String COIN_TABLE_NAME = "coins";
    public static final String FAVOURITE_TABLE_NAME = "favourites";

    public abstract CoinDao coinDao();
    public abstract FavouriteDao favouriteDao();
}

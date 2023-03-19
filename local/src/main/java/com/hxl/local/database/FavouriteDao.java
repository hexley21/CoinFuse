package com.hxl.local.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hxl.domain.model.Coin;
import com.hxl.local.model.FavouriteEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable bookmarkCoin(FavouriteEntity entity);

    @Query("DELETE FROM favourites WHERE id = :id")
    Completable unBookmarkCoin(String id);

    @Query("SELECT * FROM favourites")
    Single<List<FavouriteEntity>> getBookmarkedCoins();

    @Query("DELETE FROM favourites")
    Completable clearFavourites();
}
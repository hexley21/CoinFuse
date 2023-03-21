package com.hxl.local.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hxl.local.model.BookmarkEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable bookmarkCoin(BookmarkEntity entity);

    @Query("DELETE FROM bookmarks WHERE id = :id")
    Completable unBookmarkCoin(String id);

    @Query("SELECT * FROM bookmarks")
    Single<List<BookmarkEntity>> getBookmarkedCoins();

    @Query("DELETE FROM bookmarks")
    Completable clearFavourites();
}
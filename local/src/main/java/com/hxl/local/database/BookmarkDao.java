package com.hxl.local.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hxl.local.model.BookmarkEntity;
import com.hxl.local.model.CoinEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable bookmarkCoin(BookmarkEntity entity);

    @Query("DELETE FROM bookmarks " +
            "WHERE id = :id")
    Completable unBookmarkCoin(String id);

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks where id == :id)")
    Single<Boolean> isCoinBookmarked(String id);

    @Query("SELECT * FROM bookmarks " +
            "ORDER BY timestamp DESC")
    Single<List<BookmarkEntity>> getBookmarkedCoinIds();

    @Query("SELECT coins.* FROM coins " +
            "INNER JOIN bookmarks on coins.id == bookmarks.id " +
            "ORDER BY bookmarks.timestamp DESC")
    Single<List<CoinEntity>> getBookmarkedCoins();

    @Query("DELETE FROM bookmarks")
    Completable clearBookmarks();
}

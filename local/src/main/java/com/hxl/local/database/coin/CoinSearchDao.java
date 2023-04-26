package com.hxl.local.database.coin;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hxl.local.model.coin.CoinSearchEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CoinSearchDao {

    @Query("SELECT * FROM coin_search_history ORDER BY timestamp DESC")
    Single<List<CoinSearchEntity>> getCoinSearchHistory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCoinSearchQuery(CoinSearchEntity coinSearchEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCoinSearchQuery(CoinSearchEntity... coinSearchEntity);

    @Query("DELETE FROM coin_search_history WHERE `query` = :query")
    Completable deleteCoinSearchQuery(String query);

    @Query("DELETE FROM coin_search_history")
    Completable deleteCoinSearchHistory();
}

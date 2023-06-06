package com.hxl.local.coin.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hxl.local.coin.model.CoinSearchEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CoinSearchDao {

    @Query("SELECT * FROM coin_search_history ORDER BY timestamp ASC LIMIT 20")
    Single<List<CoinSearchEntity>> getCoinSearchHistory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCoinSearchQuery(CoinSearchEntity coinSearchEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCoinSearchQuery(CoinSearchEntity... coinSearchEntity);

    @Query("DELETE FROM coin_search_history WHERE `myValue` = :s_query")
    Completable deleteCoinSearchQuery(String s_query);

    @Query("DELETE FROM coin_search_history")
    Completable deleteCoinSearchHistory();
}

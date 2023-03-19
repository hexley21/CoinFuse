package com.hxl.local.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hxl.local.model.CoinEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CoinDao {

    @Query("SELECT * FROM coins")
    Single<List<CoinEntity>> getCoins();

    @Query("SELECT * FROM coins WHERE id in (:ids)")
    Single<List<CoinEntity>> getCoins(String ids);

    @Query("SELECT * FROM coins WHERE rank > :offset LIMIT :limit")
    Single<List<CoinEntity>> getCoins(int limit, int offset);

    @Query("SELECT * FROM coins WHERE id = :id")
    Single<CoinEntity> getCoin(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable saveCoins(List<CoinEntity> coins);

    @Query("DELETE FROM coins")
    Completable clearCoins();
}

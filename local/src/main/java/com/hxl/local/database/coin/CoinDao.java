package com.hxl.local.database.coin;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hxl.local.model.coin.CoinEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CoinDao {

    @Query("SELECT * FROM coins ORDER BY rank ASC")
    Single<List<CoinEntity>> getCoins();

    @Query("SELECT * FROM coins WHERE id IN (:ids) ORDER BY rank ASC")
    Single<List<CoinEntity>> getCoins(List<String> ids);

    @Query("SELECT * FROM coins ORDER BY rank ASC LIMIT :limit OFFSET :offset")
    Single<List<CoinEntity>> getCoins(int limit, int offset);

    @Query("SELECT * FROM coins " +
            "WHERE id LIKE :key||'%' " +
            "OR symbol LIKE :key||'%'" +
            "OR name LIKE :key||'%'" +
            "ORDER BY rank ASC")
    Single<List<CoinEntity>> searchCoins(String key);

    @Query("SELECT * FROM coins WHERE id = :id")
    Single<CoinEntity> getCoin(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addCoin(CoinEntity... coin);

    @Query("DELETE FROM coins")
    Completable clearCoins();
}

package com.hxl.local.exchange.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hxl.local.exchange.model.ExchangeEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ExchangeDao {

    @Query("SELECT * FROM exchanges ORDER BY rank ASC")
    Single<List<ExchangeEntity>> getExchanges();

    @Query("SELECT * FROM exchanges ORDER BY rank ASC LIMIT :limit OFFSET :offset")
    Single<List<ExchangeEntity>> getExchanges(int limit, int offset);

    @Query("SELECT * FROM exchanges WHERE exchangeId = :exchangeId")
    Single<ExchangeEntity> getExchange(String exchangeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertExchanges(ExchangeEntity... exchanges);

    @Query("DELETE FROM exchanges")
    Completable eraseExchanges();
}

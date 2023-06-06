package com.hxl.local.exchange.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.hxl.local.room.AppDatabase;

@Entity(tableName = AppDatabase.EXCHANGE_TABLE_NAME)
public class ExchangeEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "exchangeId")
    public final String exchangeId;
    @ColumnInfo(name = "name")
    public final String name;
    @ColumnInfo(name = "rank")
    public final Integer rank;
    @ColumnInfo(name = "percentTotalVolume")
    public final Double percentTotalVolume;
    @ColumnInfo(name = "volumeUsd")
    public final Double volumeUsd;
    @ColumnInfo(name = "tradingPairs")
    public final Integer tradingPairs;
    @ColumnInfo(name = "socket")
    public final Boolean socket;
    @ColumnInfo(name = "exchangeUrl")
    public final String exchangeUrl;
    @ColumnInfo(name = "updated")
    public final Long updated;

    public ExchangeEntity(@NonNull String exchangeId, String name, Integer rank, Double percentTotalVolume, Double volumeUsd, Integer tradingPairs, Boolean socket, String exchangeUrl, Long updated) {
        this.exchangeId = exchangeId;
        this.name = name;
        this.rank = rank;
        this.percentTotalVolume = percentTotalVolume;
        this.volumeUsd = volumeUsd;
        this.tradingPairs = tradingPairs;
        this.socket = socket;
        this.exchangeUrl = exchangeUrl;
        this.updated = updated;
    }

}

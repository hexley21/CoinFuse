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
    public String exchangeId;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "rank")
    public Integer rank;
    @ColumnInfo(name = "percentTotalVolume")
    public Double percentTotalVolume;
    @ColumnInfo(name = "volumeUsd")
    public Double volumeUsd;
    @ColumnInfo(name = "tradingPairs")
    public Integer tradingPairs;
    @ColumnInfo(name = "socket")
    public Boolean socket;
    @ColumnInfo(name = "exchangeUrl")
    public String exchangeUrl;
    @ColumnInfo(name = "updated")
    public Long updated;

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

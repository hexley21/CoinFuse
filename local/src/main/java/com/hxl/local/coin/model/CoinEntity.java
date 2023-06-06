package com.hxl.local.coin.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.hxl.local.room.AppDatabase;

@Entity(tableName = AppDatabase.COIN_TABLE_NAME)
public class CoinEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    public final String id;
    @ColumnInfo(name = "rank")
    public final int rank;
    @ColumnInfo(name = "symbol")
    public final String symbol;
    @ColumnInfo(name = "name")
    public final String name;
    @ColumnInfo(name = "supply")
    public final Double supply;
    @ColumnInfo(name = "max_supply")
    public final Double maxSupply;
    @ColumnInfo(name = "market_cap_usd")
    public final Double marketCapUsd;
    @ColumnInfo(name = "volume_usd")
    public final Double volumeUsd24Hr;
    @ColumnInfo(name = "price_usd")
    public final Double priceUsd;
    @ColumnInfo(name = "change_percent")
    public final Float changePercent24Hr;
    @ColumnInfo(name = "vwap")
    public final Double vwap24Hr;
    @ColumnInfo(name = "explorer")
    public final String explorer;
    @ColumnInfo(name = "timestamp")
    public final Long timestamp ;
    @ColumnInfo(name = "img")
    public final String img;

    public CoinEntity(
            @NonNull String id,
            int rank,
            String symbol,
            String name,
            Double supply,
            Double maxSupply,
            Double marketCapUsd,
            Double volumeUsd24Hr,
            Double priceUsd,
            Float changePercent24Hr,
            Double vwap24Hr,
            String explorer,
            Long timestamp,
            String img
    ){
        this.id = id;
        this.rank = rank;
        this.symbol = symbol;
        this.name = name;
        this.supply = supply;
        this.maxSupply = maxSupply;
        this.marketCapUsd = marketCapUsd;
        this.volumeUsd24Hr = volumeUsd24Hr;
        this.priceUsd = priceUsd;
        this.changePercent24Hr = changePercent24Hr;
        this.vwap24Hr = vwap24Hr;
        this.explorer = explorer;
        this.timestamp = timestamp;
        this.img = img;
    }
}

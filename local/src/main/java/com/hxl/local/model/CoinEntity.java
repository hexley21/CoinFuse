package com.hxl.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.hxl.local.database.CoinDatabase;

@Entity(tableName = CoinDatabase.COIN_TABLE_NAME)
public class CoinEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    public String id;
    @ColumnInfo(name = "rank")
    public int rank;
    @ColumnInfo(name = "symbol")
    public String symbol;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "supply")
    public double supply;
    @ColumnInfo(name = "max_supply")
    public Double maxSupply;
    @ColumnInfo(name = "market_cap_usd")
    public double marketCapUsd;
    @ColumnInfo(name = "volume_usd")
    public double volumeUsd24Hr;
    @ColumnInfo(name = "price_usd")
    public double priceUsd;
    @ColumnInfo(name = "change_percent")
    public float changePercent24Hr;
    @ColumnInfo(name = "vwap")
    public Double vwap24Hr;
    @ColumnInfo(name = "explorer")
    public String explorer;
    @ColumnInfo(name = "img")
    public String img;

    public CoinEntity(
            @NonNull String id,
            int rank,
            String symbol,
            String name,
            double supply,
            Double maxSupply,
            double marketCapUsd,
            double volumeUsd24Hr,
            double priceUsd,
            float changePercent24Hr,
            Double vwap24Hr,
            String explorer,
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
        this.img = img;
    }
}

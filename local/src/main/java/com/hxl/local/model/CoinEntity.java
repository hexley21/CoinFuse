package com.hxl.local.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.hxl.local.database.CoinDatabase;

@Entity(tableName = CoinDatabase.COIN_TABLE_NAME)
public class CoinEntity {
    @PrimaryKey
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.TEXT)
    public String id;
    @ColumnInfo(name = "rank", typeAffinity = ColumnInfo.INTEGER, index = true)
    public int rank;
    @ColumnInfo(name = "symbol", typeAffinity = ColumnInfo.TEXT)
    public String symbol;
    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    public String name;
    @ColumnInfo(name = "supply", typeAffinity = ColumnInfo.REAL)
    public double supply;
    @ColumnInfo(name = "max_supply", typeAffinity = ColumnInfo.REAL)
    public Double maxSupply;
    @ColumnInfo(name = "market_cap_usd", typeAffinity = ColumnInfo.REAL)
    public double marketCapUsd;
    @ColumnInfo(name = "volume_usd", typeAffinity = ColumnInfo.REAL)
    public double volumeUsd24Hr;
    @ColumnInfo(name = "price_usd", typeAffinity = ColumnInfo.REAL)
    public double priceUsd;
    @ColumnInfo(name = "change_percent", typeAffinity = ColumnInfo.REAL)
    public float changePercent24Hr;
    @ColumnInfo(name = "vwap", typeAffinity = ColumnInfo.REAL)
    public Double vwap24Hr;
    @ColumnInfo(name = "explorer", typeAffinity = ColumnInfo.TEXT)
    public String explorer;
    @ColumnInfo(name = "img", typeAffinity = ColumnInfo.TEXT)
    public String img;


    public CoinEntity() {}

    public CoinEntity(
            String id,
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

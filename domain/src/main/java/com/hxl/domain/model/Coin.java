package com.hxl.domain.model;

public class Coin {
    public String id;
    public int rank;
    public String symbol;
    public String name;
    public double supply;
    public Double maxSupply; // Nullable
    public double marketCapUsd;
    public double volumeUsd24Hr;
    public double priceUsd;
    public float changePercent24Hr;
    public Double vwap24Hr; // Nullable
    public String explorer;
    public String img;

    public Coin(
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

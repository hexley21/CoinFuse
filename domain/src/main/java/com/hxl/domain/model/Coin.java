package com.hxl.domain.model;

public class Coin {
    public String id;
    public int rank;
    public String symbol;
    public String name;
    public int supply;
    public int maxSupply;
    public double marketCapUsd;
    public double volumeUsd24Hr;
    public double priceUsd;
    public float changePercent24Hr;
    public double vwap24Hr;
    public String explorer;

    public Coin(
            String id,
            int rank,
            String symbol,
            String name,
            int supply,
            int maxSupply,
            double marketCapUsd,
            double volumeUsd24Hr,
            double priceUsd,
            float changePercent24Hr,
            double vwap24Hr,
            String explorer
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
    }
}

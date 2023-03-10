package com.hxl.domain.model;

public class Coin {
    public final String symbol;
    public final String volumeUsd24Hr;
    public final String marketCapUsd;
    public final String priceUsd;
    public final String vwap24Hr;
    public final String changePercent24Hr;
    public final String name;
    public final String explorer;
    public final String rank;
    public final String id;
    public final String maxSupply;
    public final String supply;

    public Coin(
            String symbol,
            String volumeUsd24Hr,
            String marketCapUsd,
            String priceUsd,
            String vwap24Hr,
            String changePercent24Hr,
            String name,
            String explorer,
            String rank,
            String id,
            String maxSupply,
            String supply){
        this.symbol = symbol;
        this.volumeUsd24Hr = volumeUsd24Hr;
        this.marketCapUsd = marketCapUsd;
        this.priceUsd = priceUsd;
        this.vwap24Hr = vwap24Hr;
        this.changePercent24Hr = changePercent24Hr;
        this.name = name;
        this.explorer = explorer;
        this.rank = rank;
        this.id = id;
        this.maxSupply = maxSupply;
        this.supply = supply;
    }
}

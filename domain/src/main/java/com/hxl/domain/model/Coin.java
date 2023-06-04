package com.hxl.domain.model;

public class Coin {
    public final String id;
    public final int rank;
    public final String symbol;
    public final String name;
    public final Double supply;
    public final Double maxSupply;
    public final Double marketCapUsd;
    public final Double volumeUsd24Hr;
    public final Double priceUsd;
    public final Float changePercent24Hr;
    public final Double vwap24Hr;
    public final String explorer;
    public Long timestamp;
    public final String img;

    public Coin(
            String id,
            Integer rank,
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coin) {
            return this.hashCode() == obj.hashCode();
        }
        return false;
    }
}

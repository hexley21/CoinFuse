package com.hxl.domain.model;

public class Coin {
    public String id;
    public int rank;
    public String symbol;
    public String name;
    public Double supply;
    public Double maxSupply;
    public Double marketCapUsd;
    public Double volumeUsd24Hr;

    public Double priceUsd;
    public Float changePercent24Hr;
    public Double vwap24Hr;
    public String explorer;
    public String img;

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coin) {
            return this.hashCode() == obj.hashCode();
        }
        return false;
    }
}

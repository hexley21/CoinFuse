package com.hxl.domain.model;

public class Exchange {
    public String exchangeId;
    public String name;
    public Integer rank;
    public Double percentTotalVolume;
    public Double volumeUsd;
    public Integer tradingPairs;
    public Boolean socket;
    public String exchangeUrl;
    public Long updated;

    public Exchange(String exchangeId, String name, Integer rank, Double percentTotalVolume, Double volumeUsd, Integer tradingPairs, Boolean socket, String exchangeUrl, Long updated) {
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

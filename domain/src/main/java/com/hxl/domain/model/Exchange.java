package com.hxl.domain.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exchange exchange = (Exchange) o;
        return Objects.equals(exchangeId, exchange.exchangeId) && Objects.equals(name, exchange.name) && Objects.equals(rank, exchange.rank) && Objects.equals(percentTotalVolume, exchange.percentTotalVolume) && Objects.equals(volumeUsd, exchange.volumeUsd) && Objects.equals(tradingPairs, exchange.tradingPairs) && Objects.equals(socket, exchange.socket) && Objects.equals(exchangeUrl, exchange.exchangeUrl) && Objects.equals(updated, exchange.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeId, name, rank, percentTotalVolume, volumeUsd, tradingPairs, socket, exchangeUrl, updated);
    }
}

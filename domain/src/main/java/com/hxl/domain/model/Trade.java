package com.hxl.domain.model;

import java.util.Objects;

public class Trade {

    public String exchangeId;
    public Integer rank;
    public String baseSymbol;
    public String baseId;
    public String quoteSymbol;
    public String quoteId;
    public Double priceQuote;
    public Double priceUsd;
    public Double volumeUsd24Hr;
    public Double percentExchangeVolume;
    public Integer tradesCount24Hr;
    public Long updated;

    public Trade(String exchangeId, Integer rank, String baseSymbol, String baseId, String quoteSymbol, String quoteId, Double priceQuote, Double priceUsd, Double volumeUsd24Hr, Double percentExchangeVolume, Integer tradesCount24Hr, Long updated) {
        this.exchangeId = exchangeId;
        this.rank = rank;
        this.baseSymbol = baseSymbol;
        this.baseId = baseId;
        this.quoteSymbol = quoteSymbol;
        this.quoteId = quoteId;
        this.priceQuote = priceQuote;
        this.priceUsd = priceUsd;
        this.volumeUsd24Hr = volumeUsd24Hr;
        this.percentExchangeVolume = percentExchangeVolume;
        this.tradesCount24Hr = tradesCount24Hr;
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return Objects.equals(exchangeId, trade.exchangeId) && Objects.equals(rank, trade.rank) && Objects.equals(baseSymbol, trade.baseSymbol) && Objects.equals(baseId, trade.baseId) && Objects.equals(quoteSymbol, trade.quoteSymbol) && Objects.equals(quoteId, trade.quoteId) && Objects.equals(priceQuote, trade.priceQuote) && Objects.equals(priceUsd, trade.priceUsd) && Objects.equals(volumeUsd24Hr, trade.volumeUsd24Hr) && Objects.equals(percentExchangeVolume, trade.percentExchangeVolume) && Objects.equals(tradesCount24Hr, trade.tradesCount24Hr) && Objects.equals(updated, trade.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeId, rank, baseSymbol, baseId, quoteSymbol, quoteId, priceQuote, priceUsd, volumeUsd24Hr, percentExchangeVolume, tradesCount24Hr, updated);
    }
}

package com.hxl.domain.model;

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
}

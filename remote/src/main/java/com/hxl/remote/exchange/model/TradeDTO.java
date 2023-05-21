package com.hxl.remote.exchange.model;

import com.squareup.moshi.Json;

public class TradeDTO {
    @Json(name = "exchangeId")
    public String exchangeId;
    @Json(name = "rank")
    public String rank;
    @Json(name = "baseSymbol")
    public String baseSymbol;
    @Json(name = "baseId")
    public String baseId;
    @Json(name = "quoteSymbol")
    public String quoteSymbol;
    @Json(name = "quoteId")
    public String quoteId;
    @Json(name = "priceQuote")
    public String priceQuote;
    @Json(name = "priceUsd")
    public String priceUsd;
    @Json(name = "volumeUsd24Hr")
    public String volumeUsd24Hr;
    @Json(name = "percentExchangeVolume")
    public String percentExchangeVolume;
    @Json(name = "tradesCount24Hr")
    public String tradesCount24Hr;
    @Json(name = "updated")
    public Long updated;

    public TradeDTO() {}
    public TradeDTO(String exchangeId, String rank, String baseSymbol, String baseId, String quoteSymbol, String quoteId, String priceQuote, String priceUsd, String volumeUsd24Hr, String percentExchangeVolume, String tradesCount24Hr, Long updated) {
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
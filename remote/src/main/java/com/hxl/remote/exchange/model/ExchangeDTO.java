package com.hxl.remote.exchange.model;

import com.squareup.moshi.Json;

public class ExchangeDTO {
    @Json(name = "exchangeId")
    public String exchangeId;
    @Json(name = "name")
    public String name;
    @Json(name = "rank")
    public String rank;
    @Json(name = "percentTotalVolume")
    public String percentTotalVolume;
    @Json(name = "volumeUsd")
    public String volumeUsd;
    @Json(name = "tradingPairs")
    public String tradingPairs;
    @Json(name = "socket")
    public Boolean socket;
    @Json(name = "exchangeUrl")
    public String exchangeUrl;
    @Json(name = "updated")
    public Long updated;

    public ExchangeDTO() {}

    public ExchangeDTO(String exchangeId, String name, String rank, String percentTotalVolume, String volumeUsd, String tradingPairs, Boolean socket, String exchangeUrl, Long updated) {
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

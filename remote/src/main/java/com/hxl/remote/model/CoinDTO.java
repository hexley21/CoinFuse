package com.hxl.remote.model;

import com.squareup.moshi.Json;

public class CoinDTO {
    @Json(name = "id")
    public String id;
    @Json(name = "rank")
    public String rank;
    @Json(name = "symbol")
    public String symbol;
    @Json(name = "name")
    public String name;
    @Json(name = "supply")
    public String supply;
    @Json(name = "maxSupply")
    public String maxSupply;
    @Json(name = "marketCapUsd")
    public String marketCapUsd;
    @Json(name = "volumeUsd24Hr")
    public String volumeUsd24Hr;
    @Json(name = "priceUsd")
    public String priceUsd;
    @Json(name = "changePercent24Hr")
    public String changePercent24Hr;
    @Json(name = "vwap24Hr")
    public String vwap24Hr;
    @Json(name = "explorer")
    public String explorer;

    public CoinDTO() {}

    public CoinDTO(
            String id,
            String rank,
            String symbol,
            String name,
            String supply,
            String maxSupply,
            String marketCapUsd,
            String volumeUsd24Hr,
            String priceUsd,
            String changePercent24Hr,
            String vwap24Hr,
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

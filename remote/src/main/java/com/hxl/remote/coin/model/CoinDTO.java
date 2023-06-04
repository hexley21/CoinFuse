package com.hxl.remote.coin.model;

import com.squareup.moshi.Json;

public class CoinDTO {
    @Json(name = "id")
    public final String id;
    @Json(name = "rank")
    public final String rank;
    @Json(name = "symbol")
    public final String symbol;
    @Json(name = "name")
    public final String name;
    @Json(name = "supply")
    public final String supply;
    @Json(name = "maxSupply")
    public final String maxSupply;
    @Json(name = "marketCapUsd")
    public final String marketCapUsd;
    @Json(name = "volumeUsd24Hr")
    public final String volumeUsd24Hr;
    @Json(name = "priceUsd")
    public final String priceUsd;
    @Json(name = "changePercent24Hr")
    public final String changePercent24Hr;
    @Json(name = "vwap24Hr")
    public final String vwap24Hr;
    @Json(name = "explorer")
    public final String explorer;

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

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
}

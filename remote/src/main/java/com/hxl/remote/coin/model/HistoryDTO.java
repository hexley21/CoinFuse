package com.hxl.remote.coin.model;

import com.squareup.moshi.Json;

public class HistoryDTO {
    @Json(name = "priceUsd")
    public final String priceUsd;
    @Json(name = "time")
    public final Long time;
    @Json(name = "date")
    public final String date;

    public HistoryDTO(
            String priceUsd,
            Long time,
            String date
    ) {
        this.priceUsd = priceUsd;
        this.time = time;
        this.date = date;
    }
}

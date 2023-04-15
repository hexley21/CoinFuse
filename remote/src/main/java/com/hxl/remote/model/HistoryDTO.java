package com.hxl.remote.model;

import com.squareup.moshi.Json;

public class HistoryDTO {
    @Json(name = "priceUsd")
    public String priceUsd;
    @Json(name = "time")
    public Long time;
    @Json(name = "date")
    public String date;

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

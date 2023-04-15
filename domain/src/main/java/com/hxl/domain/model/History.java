package com.hxl.domain.model;

public class History {

    public enum Interval {
        M1,
        M5,
        M15,
        M30,
        H1,
        H2,
        H6,
        H12,
        D1
    }
    public Double priceUsd;
    public Long time;

    public History(
            Double priceUsd,
            Long time
    ) {
        this.priceUsd = priceUsd;
        this.time = time;
    }
}

package com.hxl.domain.model;

public class CoinPriceHistory {

    public enum Interval {
        D1("m1"),
        D5("m5"),
        D7("m15"),
        D14("m30"),
        M1("h1"),
        M2("h2"),
        M6("h6"),
        YD1("h12"),
        Y1("d1");

        public final String param;
        Interval (String param) {
            this.param = param;
        }
    }
    public final Double priceUsd;
    public final Long time;

    public CoinPriceHistory(
            Double priceUsd,
            Long time
    ) {
        this.priceUsd = priceUsd;
        this.time = time;
    }
}

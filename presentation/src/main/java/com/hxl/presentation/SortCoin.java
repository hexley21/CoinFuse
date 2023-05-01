package com.hxl.presentation;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;
import static java.util.Comparator.comparingLong;

import com.hxl.domain.model.Coin;

import java.util.Comparator;

public class SortCoin {

    public static Comparator<Coin> by(SortType sortType) {
        switch (sortType) {
            case RANK_DESC:
                return comparingInt(c -> -c.rank);
            case NAME_ASC:
                return comparing(c -> c.name);
            case NAME_DESC:
                return (c1, c2) -> c2.name.compareTo(c1.name);
            case PRICE_ASC:
                return comparingDouble(c -> c.priceUsd);
            case PRICE_DESC:
                return comparingDouble(c -> -c.priceUsd);
            case MARKET_ASC:
                return comparingDouble(c -> c.marketCapUsd == null ? Double.MIN_VALUE : c.marketCapUsd);
            case MARKET_DESC:
                return comparingDouble(c -> c.marketCapUsd == null ? Double.MAX_VALUE : -c.marketCapUsd);
            case VOLUME_ASC:
                return comparingDouble(c -> c.volumeUsd24Hr == null ? Double.MIN_VALUE : c.volumeUsd24Hr);
            case VOLUME_DESC:
                return comparingDouble(c -> c.volumeUsd24Hr == null ? Double.MAX_VALUE : -c.volumeUsd24Hr);
            case CHANGE_ASC:
                return comparingDouble(c -> c.changePercent24Hr == null ? Double.MIN_VALUE : c.changePercent24Hr);
            case CHANGE_DESC:
                return comparingDouble(c -> c.changePercent24Hr == null ? Integer.MAX_VALUE : -c.changePercent24Hr);
            case TIMESTAMP_ASC:
                return comparingLong(c -> c.timestamp);
            case TIMESTAMP_DESC:
                return comparingLong(c -> -c.timestamp);
        }
        return comparingInt(c -> c.rank);
    }

    public static void by() {
        by(SortType.NONE);
    }

    public enum SortType {
        RANK_ASC,
        RANK_DESC,
        NAME_ASC,
        NAME_DESC,
        PRICE_ASC,
        PRICE_DESC,
        MARKET_ASC,
        MARKET_DESC,
        VOLUME_ASC,
        VOLUME_DESC,
        CHANGE_ASC,
        CHANGE_DESC,
        TIMESTAMP_ASC,
        TIMESTAMP_DESC,
        NONE
    }
}

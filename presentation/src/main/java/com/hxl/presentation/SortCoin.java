package com.hxl.presentation;

import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;
import static java.util.Comparator.comparingLong;

import com.hxl.domain.model.Coin;

import java.util.Comparator;

public class SortCoin {

    public static Comparator<Coin> by(SortType sortType, SortBy sortBy) {
        if (sortBy == SortBy.DESC)
            return sortByDesc(sortType);
        return sortByAsc(sortType);
    }

//    public static Comparator<Coin> by(SortType sortType) {
//        return sortByDesc(sortType);
//    }

    private static Comparator<Coin> sortByDesc(SortType sortType) {
        switch (sortType) {
            case RANK:
                return comparingInt(c -> -c.rank);
            case NAME:
                return (c1, c2) -> c2.name.compareTo(c1.name);
            case PRICE:
                return comparingDouble(c -> -c.priceUsd);
            case MARKET:
                return comparingDouble(c -> c.marketCapUsd == null ? Double.MAX_VALUE : -c.marketCapUsd);
            case VOLUME:
                return comparingDouble(c -> c.volumeUsd24Hr == null ? Double.MAX_VALUE : -c.volumeUsd24Hr);
            case CHANGE:
                return comparingDouble(c -> c.changePercent24Hr == null ? Double.MIN_VALUE : c.changePercent24Hr);
            case TIMESTAMP:
                return comparingLong(c -> -c.timestamp);
        }
        return comparingInt(c -> c.rank);
    }

    public static Comparator<Coin> sortByAsc(SortType sortType) {
        switch (sortType) {
            case RANK:
                return comparingInt(c -> -c.rank);
            case NAME:
                return (c1, c2) -> c2.name.compareTo(c1.name);
            case PRICE:
                return comparingDouble(c -> -c.priceUsd);
            case MARKET:
                return comparingDouble(c -> c.marketCapUsd == null ? Double.MAX_VALUE : -c.marketCapUsd);
            case VOLUME:
                return comparingDouble(c -> c.volumeUsd24Hr == null ? Double.MAX_VALUE : -c.volumeUsd24Hr);
            case CHANGE:
                return comparingDouble(c -> c.changePercent24Hr == null ? Double.MIN_VALUE : c.changePercent24Hr);
            case TIMESTAMP:
                return comparingLong(c -> -c.timestamp);
        }
        return comparingInt(c -> -c.rank);
    }

    public enum SortType {
        RANK,
        NAME,
        PRICE,
        MARKET,
        VOLUME,
        CHANGE,
        TIMESTAMP,
        NONE
    }
}

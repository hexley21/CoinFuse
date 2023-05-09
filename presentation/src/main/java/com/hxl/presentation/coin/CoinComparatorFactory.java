package com.hxl.presentation.coin;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;
import static java.util.Comparator.comparingLong;

import com.hxl.domain.model.Coin;
import com.hxl.presentation.OrderBy;

import java.util.Comparator;

public final class CoinComparatorFactory {

    public static Comparator<Coin> createComparator(CoinSortBy coinSortBy, OrderBy orderBy) {
        if (orderBy == OrderBy.DESC)
            return sortByDesc(coinSortBy);
        return sortByAsc(coinSortBy);
    }

    private static Comparator<Coin> sortByDesc(CoinSortBy coinSortBy) {
        switch (coinSortBy) {
            case NAME:
                return (c1, c2) -> c2.name.compareTo(c1.name);
            case PRICE:
                return comparingDouble(c -> -c.priceUsd);
            case MARKET:
                return comparingDouble(c -> c.marketCapUsd == null ? Double.MAX_VALUE : -c.marketCapUsd);
            case VOLUME:
                return comparingDouble(c -> c.volumeUsd24Hr == null ? Double.MAX_VALUE : -c.volumeUsd24Hr);
            case CHANGE:
                return comparingDouble(c -> c.changePercent24Hr == null ? Integer.MAX_VALUE : -c.changePercent24Hr);
            case TIMESTAMP:
                return comparingLong(c -> -c.timestamp);
        }
        return comparingInt(c -> c.rank);
    }

    private static Comparator<Coin> sortByAsc(CoinSortBy coinSortBy) {
        switch (coinSortBy) {
            case NAME:
                return comparing(c -> c.name);
            case PRICE:
                return comparingDouble(c -> c.priceUsd);
            case MARKET:
                return comparingDouble(c -> c.marketCapUsd == null ? Double.MIN_VALUE : c.marketCapUsd);
            case VOLUME:
                return comparingDouble(c -> c.volumeUsd24Hr == null ? Double.MIN_VALUE : c.volumeUsd24Hr);
            case CHANGE:
                return comparingDouble(c -> c.changePercent24Hr == null ? Double.MIN_VALUE : c.changePercent24Hr);
            case TIMESTAMP:
                return comparingLong(c -> c.timestamp);
        }
        return comparingInt(c -> c.rank);
    }
}

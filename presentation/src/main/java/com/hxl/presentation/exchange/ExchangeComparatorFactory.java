package com.hxl.presentation.exchange;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;
import static java.util.Comparator.comparingLong;

import com.hxl.domain.model.Exchange;
import com.hxl.presentation.OrderBy;

import java.util.Comparator;

public class ExchangeComparatorFactory {

    public static Comparator<Exchange> createComparator(ExchangeSortBy sortBy, OrderBy orderBy) {
        if (orderBy == OrderBy.DESC)
            return sortByDesc(sortBy);
        return sortByAsc(sortBy);
    }

    private static Comparator<Exchange> sortByDesc(ExchangeSortBy sortBy) {
        switch (sortBy) {
            case NAME:
                return (e1, e2) -> e2.name.compareTo(e1.name);
            case TOTAL_VOLUME_SHARE:
                return comparingDouble(e -> e.percentTotalVolume == null ? Double.MAX_VALUE : -e.percentTotalVolume);
            case VOLUME:
                return comparingDouble(e -> e.volumeUsd == null ? Double.MAX_VALUE : -e.volumeUsd);
            case TRADING_PAIRS:
                return comparingInt(e -> e.tradingPairs == null ? Integer.MAX_VALUE : -e.tradingPairs);
            case SOCKET:
                return comparing(e -> e.socket == null || e.socket);
            case UPDATED:
                return comparingLong(e -> e.updated == null ? Long.MAX_VALUE : -e.updated);
        }
        return comparingInt(e -> e.rank == null ? Integer.MAX_VALUE : -e.rank);
    }

    private static Comparator<Exchange> sortByAsc(ExchangeSortBy sortBy) {
        switch (sortBy) {
            case NAME:
                return comparing(e -> e.name);
            case TOTAL_VOLUME_SHARE:
                return comparingDouble(e -> e.percentTotalVolume == null ? Double.MIN_VALUE : e.percentTotalVolume);
            case VOLUME:
                return comparingDouble(e -> e.volumeUsd == null ? Double.MIN_VALUE : e.volumeUsd);
            case TRADING_PAIRS:
                return comparingDouble(e -> e.tradingPairs == null ? Double.MIN_VALUE : e.rank);
            case SOCKET:
                return comparing(e -> e.socket != null && !e.socket);
            case UPDATED:
                return comparingLong(e -> e.updated == null ? Long.MIN_VALUE : e.updated);
        }
        return comparingInt(e -> e.rank == null ? Integer.MIN_VALUE : e.rank);
    }

}

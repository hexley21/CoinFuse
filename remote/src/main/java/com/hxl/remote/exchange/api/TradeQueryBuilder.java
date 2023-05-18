package com.hxl.remote.exchange.api;

import java.util.HashMap;
import java.util.Map;

public final class TradeQueryBuilder {

    private final Map<String, String> querryMap = new HashMap<>();

    public TradeQueryBuilder addExchangeId(String exchangeId) {
        if (exchangeId != null) {
            querryMap.put("exchangeId", exchangeId);
        }
        return this;
    }

    public TradeQueryBuilder addBaseId(String baseId) {
        if (baseId != null) {
            querryMap.put("baseId", baseId);
        }
        return this;
    }

    public TradeQueryBuilder addQuoteId(String quoteId) {
        if (quoteId != null) {
            querryMap.put("quoteId", quoteId);
        }
        return this;
    }

    public TradeQueryBuilder addLimit(int limit) {
        if (limit > 0) {
            querryMap.put("limit", String.valueOf(limit));
        }
        return this;
    }

    public TradeQueryBuilder addOffset(int offset) {
        if (offset > 0) {
            querryMap.put("offset", String.valueOf(offset));
        }
        return this;
    }

    public Map<String, String> build() {
        return querryMap;
    }
}

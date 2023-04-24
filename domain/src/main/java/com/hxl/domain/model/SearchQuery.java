package com.hxl.domain.model;

public class SearchQuery {
    public String query;
    public long timestamp;

    public SearchQuery(String query, long timestamp) {
        this.query = query;
        this.timestamp = timestamp;
    }
}

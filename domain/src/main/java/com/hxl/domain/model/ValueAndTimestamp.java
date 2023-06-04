package com.hxl.domain.model;

public class ValueAndTimestamp <T>{
    public final T value;
    public final long timestamp;

    public ValueAndTimestamp(T value, long timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }
}

package com.hxl.domain.model;

public class ValueAndTimestamp <T>{
    public T value;
    public long timestamp;

    public ValueAndTimestamp(T value, long timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }
}

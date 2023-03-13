package com.hxl.remote.model;

public class Response<T> {

    public T data;
    public long timestamp;

    public Response(T data, long timestamp) {
        this.data = data;
        this.timestamp = timestamp;
    }
}
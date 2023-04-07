package com.hxl.remote.model;

import com.squareup.moshi.Json;

public class Response<T> {

    @Json(name = "data")
    public T data;
    @Json(name = "timestamp")
    public long timestamp;

    public Response() {}

    public Response(T data, long timestamp) {
        this.data = data;
        this.timestamp = timestamp;
    }
}
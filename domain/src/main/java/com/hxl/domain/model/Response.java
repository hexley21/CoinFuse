package com.hxl.domain.model;

public class Response<T> {

    public Status status;
    public T data;
    public long timestamp;
    String error;

    public Response(Status status, T data, long timestamp, String error) {
        this.status = status;
        this.data = data;
        this.timestamp = timestamp;
        this.error = error;
    }

    public static <T> Response<T> success(T data, long timestamp){
        return new Response<>(Status.SUCCESS, data, timestamp, null);
    }

    public static <T> Response<T> error(T data, long timestamp, String message){
        return new Response<>(Status.ERROR, data, timestamp, message);
    }

    public static <T> Response<T> loading(T data, long timestamp){
        return new Response<>(Status.LOADING, data, timestamp, null);
    }

}

enum Status {
    SUCCESS,
    ERROR,
    LOADING
}

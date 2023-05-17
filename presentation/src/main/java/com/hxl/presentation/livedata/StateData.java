package com.hxl.presentation.livedata;

public class StateData<T> {
    private final DataState state;
    private final T data;
    private final Throwable error;

    private StateData(DataState state, T data, Throwable error) {
        this.state = state;
        this.data = data;
        this.error = error;
    }

    public static <T> StateData<T> loading() {
        return new StateData<>(DataState.LOADING, null, null);
    }

    public static <T> StateData<T> success(T data) {
        return new StateData<>(DataState.SUCCESS, data, null);
    }

    public static <T> StateData<T> error(Throwable error) {
        return new StateData<>(DataState.ERROR, null, error);
    }

    public DataState getState() {
        return state;
    }

    public T getData() {
        return data;
    }

    public Throwable getError() {
        return error;
    }
}

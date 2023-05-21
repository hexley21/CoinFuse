package com.hxl.presentation.livedata;

import androidx.lifecycle.MutableLiveData;

public class StateLiveData<T> extends MutableLiveData<StateData<T>> {

    public void setLoading() {
        postValue(StateData.loading());
    }

    public void setSuccess(T data) {
        postValue(StateData.success(data));
    }

    public void setError(Throwable throwable) {
        postValue(StateData.error(throwable));
    }
}

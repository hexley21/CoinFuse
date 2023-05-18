package com.hxl.presentation.viewmodels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.exchanges.GetExchange;
import com.hxl.domain.interactors.exchanges.GetTrades;
import com.hxl.domain.model.Exchange;
import com.hxl.domain.model.Trade;
import com.hxl.presentation.livedata.StateLiveData;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class ExchangeDetailsViewModel extends ViewModel {

    private final static String  TAG = "ExchangeDetailsVM";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final GetExchange getExchange;
    private final GetTrades getTrades;

    private StateLiveData<Exchange> currentExchange;
    private StateLiveData<List<Trade>> currentTrades;

    @Inject
    public ExchangeDetailsViewModel(GetExchange getExchange, GetTrades getTrades) {
        this.getExchange = getExchange;
        this.getTrades = getTrades;
    }

    public StateLiveData<Exchange> getCurrentExchange() {
        if (currentExchange == null) {
            currentExchange = new StateLiveData<>();
        }

        return currentExchange;
    }

    public StateLiveData<List<Trade>> getCurrentTrades() {
        if (currentTrades == null) {
            currentTrades = new StateLiveData<>();
        }
        return currentTrades;
    }

    public void fetchExchange(String exchangeId) {
        getCurrentExchange().setLoading();
        getExchange.invoke(exchangeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        exchange -> {
                            getCurrentExchange().setSuccess(exchange);
                            Log.d(TAG, "fetchExchange: success");
                        },
                        e -> {
                            getCurrentExchange().setError(e);
                            Log.e(TAG, "fetchExchange: failed", e);
                        },
                        compositeDisposable
                );
    }

    public void fetchTrades(Map<String, String> queryMap) {
        getTrades.invoke(queryMap)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        trades -> {
                            getCurrentTrades().setSuccess(trades);
                            Log.d(TAG, "fetchTrades: success");
                        },
                        e -> {
                            getCurrentTrades().setError(e);
                            Log.e(TAG, "fetchTrades: failed", e);
                        },
                        compositeDisposable
                );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}

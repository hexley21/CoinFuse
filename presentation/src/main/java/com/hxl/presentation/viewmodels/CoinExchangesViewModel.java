package com.hxl.presentation.viewmodels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.GetTradesByCoin;
import com.hxl.domain.model.Trade;
import com.hxl.presentation.livedata.StateLiveData;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class CoinExchangesViewModel extends ViewModel {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String TAG = "CoinExchangesViewModel";

    private final GetTradesByCoin getTradesByCoin;
    
    private StateLiveData<List<Trade>> currentTrades;

    @Inject
    public CoinExchangesViewModel(GetTradesByCoin getTradesByCoin) {
        this.getTradesByCoin = getTradesByCoin;
    }

    public StateLiveData<List<Trade>> getCurrentTrades() {
        if (currentTrades == null) {
            currentTrades = new StateLiveData<>();
        }

        return currentTrades;
    }

    public void fetchTrades(String id) {
        getCurrentTrades().setLoading();
        getTradesByCoin.invoke(id)
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

}

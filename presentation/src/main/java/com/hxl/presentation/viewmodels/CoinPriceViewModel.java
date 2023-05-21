package com.hxl.presentation.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.GetCoin;
import com.hxl.domain.interactors.coins.GetCoinPriceHistory;
import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.presentation.livedata.StateData;
import com.hxl.presentation.livedata.StateLiveData;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class CoinPriceViewModel extends ViewModel {
    private static final String TAG = "CoinPriceViewModel";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @NonNull private final GetCoin getCoin;
    @NonNull private final GetCoinPriceHistory getCoinPriceHistory;

    private StateLiveData<Coin> currentCoin;
    private StateLiveData<List<CoinPriceHistory>> currentCoinPriceHistory;

    @Inject
    public CoinPriceViewModel(@NonNull GetCoin getCoin, @NonNull GetCoinPriceHistory getCoinPriceHistory) {
        this.getCoin = getCoin;
        this.getCoinPriceHistory = getCoinPriceHistory;
    }

    public StateLiveData<Coin> getCurrentCoin() {
        if (currentCoin == null) {
            currentCoin = new StateLiveData<>();
        }

        return currentCoin;
    }

    public StateLiveData<List<CoinPriceHistory>> getCurrentCoinPriceHistory () {
        if (currentCoinPriceHistory == null) {
            currentCoinPriceHistory = new StateLiveData<>();
        }

        return currentCoinPriceHistory;
    }

    public void fetchCoin(String id) {
        getCurrentCoinPriceHistory().setValue(StateData.loading());
        getCoin.invoke(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coin -> {
                            getCurrentCoin().setValue(StateData.success(coin));
                            Log.d(TAG, "fetchCoin: success");
                        },
                        e -> {
                            getCurrentCoin().setValue(StateData.error(e));
                            Log.e(TAG, "fetchCoin: failed", e);
                        },
                        compositeDisposable
                );
    }

    public void fetchPriceHistory(String id, CoinPriceHistory.Interval interval) {
        getCurrentCoinPriceHistory().setValue(StateData.loading());
        getCoinPriceHistory.invoke(id, interval)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        histories -> {
                            getCurrentCoinPriceHistory().setValue(StateData.success(histories));
                            Log.d(TAG, "fetchPriceHistory: success");
                        },
                        e -> {
                            getCurrentCoinPriceHistory().setValue(StateData.error(e));
                            Log.e(TAG, "fetchPriceHistory: failed", e);
                        },
                        compositeDisposable
                );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
        Log.d(TAG, "onCleared: CompositeDisposable was disposed");
    }
}

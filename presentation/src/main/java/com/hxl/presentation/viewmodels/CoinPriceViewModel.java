package com.hxl.presentation.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.GetCoin;
import com.hxl.domain.interactors.coins.GetCoinPriceHistory;
import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class CoinPriceViewModel extends ViewModel {
    private final GetCoin getCoin;
    private final GetCoinPriceHistory getCoinPriceHistory;

    private MutableLiveData<Coin> currentCoin;
    private MutableLiveData<List<CoinPriceHistory>> currentCoinPriceHistory;

    @Inject
    public CoinPriceViewModel(GetCoin getCoin, GetCoinPriceHistory getCoinPriceHistory) {
        this.getCoin = getCoin;
        this.getCoinPriceHistory = getCoinPriceHistory;
    }

    public MutableLiveData<Coin> getCurrentCoin() {
        if (currentCoin == null) {
            currentCoin = new MutableLiveData<>();
        }

        return currentCoin;
    }

    public MutableLiveData<List<CoinPriceHistory>> getCurrentCoinPriceHistory () {
        if (currentCoinPriceHistory == null) {
            currentCoinPriceHistory = new MutableLiveData<>();
        }
        return currentCoinPriceHistory;
    }

    public Single<Coin> fetchCoin(String id) {
        return getCoin.invoke(id);
    }

    public Single<List<CoinPriceHistory>> fetchPriceHistory(String id, CoinPriceHistory.Interval interval) {
        return getCoinPriceHistory.invoke(id, interval);
    }

}

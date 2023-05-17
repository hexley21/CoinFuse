package com.hxl.presentation.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.GetTradesByCoin;
import com.hxl.domain.model.Trade;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class CoinExchangesViewModel extends ViewModel {

    private final GetTradesByCoin getTradesByCoin;
    
    private MutableLiveData<List<Trade>> currentTrades;

    @Inject
    public CoinExchangesViewModel(GetTradesByCoin getTradesByCoin) {
        this.getTradesByCoin = getTradesByCoin;
    }

    public MutableLiveData<List<Trade>> getCurrentTrades() {
        if (currentTrades == null) {
            currentTrades = new MutableLiveData<>();
        }

        return currentTrades;
    }

    public Single<List<Trade>> fetchTrades(String id) {
        return getTradesByCoin.invoke(id);
    }

    public Single<List<Trade>> fetchTrades(String id, int limit, int offset) {
        return getTradesByCoin.invoke(id, limit, offset);
    }

}

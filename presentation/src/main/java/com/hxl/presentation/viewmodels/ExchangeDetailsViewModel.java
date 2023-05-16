package com.hxl.presentation.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.exchanges.GetExchange;
import com.hxl.domain.interactors.exchanges.GetTrades;
import com.hxl.domain.model.Exchange;
import com.hxl.domain.model.Trade;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class ExchangeDetailsViewModel extends ViewModel {

    private final GetExchange getExchange;
    private final GetTrades getTrades;

    private MutableLiveData<Exchange> currentExchange;
    private MutableLiveData<List<Trade>> currentTrades;

    public MutableLiveData<Exchange> getCurrentExchange() {
        if (currentExchange == null) {
            currentExchange = new MutableLiveData<>();
        }
        return currentExchange;
    }

    public MutableLiveData<List<Trade>> getCurrentTrades() {
        if (currentTrades == null) {
            currentTrades = new MutableLiveData<>();
        }
        return currentTrades;
    }
    @Inject
    public ExchangeDetailsViewModel(GetExchange getExchange, GetTrades getTrades) {
        this.getExchange = getExchange;
        this.getTrades = getTrades;
    }

    public Single<Exchange> fetchExchanges(String exchangeId) {
        return getExchange.invoke(exchangeId);
    }

    public Single<List<Trade>> fetchTrades(Map<String, String> queryMap) {
        return getTrades.invoke(queryMap);
    }
}

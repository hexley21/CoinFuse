package com.hxl.presentation.viewmodels;

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

    @Inject
    public ExchangeDetailsViewModel(GetExchange getExchange, GetTrades getTrades) {
        this.getExchange = getExchange;
        this.getTrades = getTrades;
    }

    public Single<Exchange> getExchange(String exchangeId) {
        return getExchange.invoke(exchangeId);
    }

    public Single<List<Trade>> getTrades(Map<String, String> queryMap) {
        return getTrades.invoke(queryMap);
    }
}

package com.hxl.presentation.viewmodels;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.exchanges.GetExchanges;
import com.hxl.domain.model.Exchange;
import com.hxl.presentation.OrderBy;
import com.hxl.presentation.exchange.ExchangeComparatorFactory;
import com.hxl.presentation.exchange.ExchangeSortBy;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class ExchangesViewModel extends ViewModel {

    private final GetExchanges getExchanges;

    @Inject
    public ExchangesViewModel(GetExchanges getExchanges) {
        this.getExchanges = getExchanges;
    }

    public Single<List<Exchange>> getExchanges(ExchangeSortBy sortBy, OrderBy orderBy) {
        return getExchanges.invoke()
                .map(x -> x.stream()
                        .sorted(ExchangeComparatorFactory.createComparator(sortBy, orderBy))
                        .collect(Collectors.toList()));
    }

}

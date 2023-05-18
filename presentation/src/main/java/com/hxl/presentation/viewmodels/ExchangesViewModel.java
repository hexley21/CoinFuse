package com.hxl.presentation.viewmodels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.exchanges.GetExchanges;
import com.hxl.domain.model.Exchange;
import com.hxl.presentation.OrderBy;
import com.hxl.presentation.exchange.ExchangeComparatorFactory;
import com.hxl.presentation.exchange.ExchangeSortBy;
import com.hxl.presentation.livedata.StateData;
import com.hxl.presentation.livedata.StateLiveData;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class ExchangesViewModel extends ViewModel {

    private final String TAG = "ExchangesViewModel";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final GetExchanges getExchanges;

    private StateLiveData<List<Exchange>> currentExchanges;

    public StateLiveData<List<Exchange>> getCurrentExchanges() {
        if (currentExchanges == null) {
            currentExchanges = new StateLiveData<>();
        }

        return currentExchanges;
    }

    @Inject
    public ExchangesViewModel(GetExchanges getExchanges) {
        this.getExchanges = getExchanges;
    }

    public void fetchExchanges(ExchangeSortBy sortBy, OrderBy orderBy) {
        getExchanges.invoke()
                .map(x -> x.stream()
                        .sorted(ExchangeComparatorFactory.createComparator(sortBy, orderBy))
                        .collect(Collectors.toList()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        exchanges -> {
                            getCurrentExchanges().setValue(StateData.success(exchanges));
                            Log.d(TAG, "fetchExchanges: success");
                        },
                        e -> {
                            getCurrentExchanges().setValue(StateData.error(e));
                            Log.e(TAG, "fetchExchanges: failed", e);
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

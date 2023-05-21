package com.hxl.presentation.viewmodels;

import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.hxl.domain.interactors.coins.GetCoins;
import com.hxl.domain.interactors.coins.GetCoinsBySearchHistory;
import com.hxl.domain.interactors.coins.InsertCoinSearchQuery;
import com.hxl.domain.interactors.coins.SearchCoins;
import com.hxl.domain.model.Coin;
import com.hxl.presentation.coin.CoinPagingSource;
import com.hxl.presentation.livedata.StateLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class CoinsMenuViewModel extends ViewModel {
    private static final String TAG = "CoinsMenuViewModel";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @NotNull private final SearchCoins searchCoins;
    @NotNull private final GetCoinsBySearchHistory coinsBySearchHistory;
    @NotNull private final InsertCoinSearchQuery insertCoinSearchQuery;
    @NotNull public Flowable<PagingData<Coin>> coinStream;

    private StateLiveData<PagingData<Coin>> currentCoins;
    private StateLiveData<List<Coin>> currentCoinSearch;
    private StateLiveData<List<Coin>> currentCoinsSearchHistory;

    public StateLiveData<PagingData<Coin>> getCurrentCoins() {
        if (currentCoins == null) {
            currentCoins = new StateLiveData<>();
        }

        return currentCoins;
    }

    public StateLiveData<List<Coin>> getCurrentCoinSearch() {
        if (currentCoinSearch == null) {
            this.currentCoinSearch = new StateLiveData<>();
        }

        return currentCoinSearch;
    }

    public StateLiveData<List<Coin>> getCurrentCoinsSearchHistory() {
        if (currentCoinsSearchHistory == null) {
            this.currentCoinsSearchHistory = new StateLiveData<>();
        }

        return currentCoinsSearchHistory;
    }

    public void pageCoins() {
        getCurrentCoins().setLoading();
        coinStream.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        pagingData -> {
                            getCurrentCoins().setSuccess(pagingData);
                            Log.d(TAG, "pageCoins: success");
                        },
                        e -> {
                            getCurrentCoins().setError(e);
                            Log.e(TAG, "pageCoins: failed", e);
                        },
                        () -> Log.d(TAG, "pageCoins: complete"),
                        compositeDisposable
                );
    }

    public void fetchCoinSearch(String query) {
        getCurrentCoinSearch().setLoading();
        searchCoins.invoke(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coins -> {
                            getCurrentCoinSearch().setSuccess(coins);
                            Log.d(TAG, "fetchCoinSearch: success");
                        },
                        e -> {
                            getCurrentCoinSearch().setError(e);
                            Log.e(TAG, "fetchCoinSearch: failed", e);
                        },
                        compositeDisposable
                );
    }

    public void fetchCoinSearchHistory() {
        getCurrentCoinsSearchHistory().setLoading();
        coinsBySearchHistory.invoke()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coins -> {
                            getCurrentCoinsSearchHistory().setSuccess(coins);
                            Log.d(TAG, "fetchCoinSearchHistory: success");
                        },
                        e -> {
                            getCurrentCoinsSearchHistory().setError(e);
                            Log.e(TAG, "fetchCoinSearchHistory: failed", e);
                        },
                        compositeDisposable
                );
    }

    public void insertCoinSearchQuery(String query) {
        insertCoinSearchQuery.invoke(query)
                .subscribe(
                        () -> Log.d(TAG, "insertCoinSearchQuery: success"),
                        e -> Log.e(TAG, "insertCoinSearchQuery: failed", e),
                        compositeDisposable
                );
    }
    public void clearCompositeDisposable() {
        compositeDisposable.clear();
    }
    @Inject
    public CoinsMenuViewModel(
            @NotNull GetCoins getCoins,
            @NotNull SearchCoins searchCoins,
            @NotNull GetCoinsBySearchHistory coinsBySearchHistory,
            @NotNull InsertCoinSearchQuery insertCoinSearchQuery
    ) {
        this.searchCoins = searchCoins;
        this.coinsBySearchHistory = coinsBySearchHistory;
        this.insertCoinSearchQuery = insertCoinSearchQuery;

        final Pager<Integer, Coin> pager = new Pager<>(
                new PagingConfig(CoinPagingSource.AMOUNT),
                () -> new CoinPagingSource(getCoins));

        coinStream = PagingRx.getFlowable(pager);

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
        Log.d(TAG, "onCleared: CompositeDisposable was disposed");
    }
}

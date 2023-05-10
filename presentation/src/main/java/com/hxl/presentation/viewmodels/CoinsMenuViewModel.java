package com.hxl.presentation.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.hxl.domain.interactors.coins.DeleteCoinSearchQuery;
import com.hxl.domain.interactors.coins.GetCoinSearchHistory;
import com.hxl.domain.interactors.coins.GetCoins;
import com.hxl.domain.interactors.coins.InsertCoinSearchQuery;
import com.hxl.domain.interactors.coins.SearchCoins;
import com.hxl.domain.model.Coin;
import com.hxl.domain.model.ValueAndTimestamp;
import com.hxl.presentation.coin.CoinPagingSource;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class CoinsMenuViewModel extends ViewModel {

    @NotNull private final GetCoins getCoins;
    @NotNull private final SearchCoins searchCoins;
    @NotNull private final GetCoinSearchHistory getCoinSearchHistory;
    @NotNull private final InsertCoinSearchQuery insertCoinSearchQuery;
    @NotNull private final DeleteCoinSearchQuery deleteCoinSearchQuery;

    public Single<List<Coin>> getCoins() {
        return getCoins.invoke();
    }

    public Pager<Integer, Coin> pager;

    public Single<List<Coin>> getCoins(List<String> coins) {
        return getCoins.invoke(coins);
    }

    public Single<List<Coin>> searchCoins(String key) {
        return searchCoins.invoke(key);
    }

    public Single<List<ValueAndTimestamp<String>>> getCoinSearchHistory() {
        return getCoinSearchHistory.invoke();
    }

    public Completable insertCoinSearchQuery(String query) {
        return insertCoinSearchQuery.invoke(query);
    }

    public Completable deleteCoinSearchQuery(String query) {
        return deleteCoinSearchQuery.invoke(query);
    }

    public Flowable<PagingData<Coin>> coinStream;

    @Inject
    public CoinsMenuViewModel(@NotNull GetCoins getCoins,
                              @NotNull SearchCoins searchCoins,
                              @NotNull GetCoinSearchHistory getCoinSearchHistory,
                              @NotNull InsertCoinSearchQuery insertCoinSearchQuery,
                              @NotNull DeleteCoinSearchQuery deleteCoinSearchQuery) {
        this.getCoins = getCoins;
        this.searchCoins = searchCoins;
        this.getCoinSearchHistory = getCoinSearchHistory;
        this.insertCoinSearchQuery = insertCoinSearchQuery;
        this.deleteCoinSearchQuery = deleteCoinSearchQuery;

        pager = new Pager<>(
                new PagingConfig(CoinPagingSource.AMOUNT),
                () -> new CoinPagingSource(getCoins));

        coinStream = PagingRx.getFlowable(pager);

    }
}

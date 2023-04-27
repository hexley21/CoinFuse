package com.hxl.presentation.viewmodels;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.DeleteCoinSearchQuery;
import com.hxl.domain.interactors.coins.GetCoinSearchHistory;
import com.hxl.domain.interactors.coins.GetCoins;
import com.hxl.domain.interactors.coins.InsertCoinSearchQuery;
import com.hxl.domain.interactors.coins.SearchCoins;
import com.hxl.domain.model.Coin;
import com.hxl.domain.model.SearchQuery;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Completable;
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

    public Single<List<Coin>> searchCoins(String key) {
        return searchCoins.invoke(key);
    }

    public Single<List<SearchQuery>> getCoinSearchHistory() {
        return getCoinSearchHistory.invoke();
    }

    public Completable insertCoinSearchQuery(String query) {
        return insertCoinSearchQuery.invoke(query);
    }

    public Completable deleteCoinSearchQuery(String query) {
        return deleteCoinSearchQuery.invoke(query);
    }

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
    }
}

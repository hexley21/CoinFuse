package com.hxl.presentation.viewmodels;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.GetBookmarkedCoins;
import com.hxl.domain.interactors.coins.SearchBookmarkedCoins;
import com.hxl.domain.model.Coin;
import com.hxl.presentation.OrderBy;
import com.hxl.presentation.coin.CoinSortBy;
import com.hxl.presentation.coin.CoinComparatorFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class BookmarksViewModel extends ViewModel {

    private final GetBookmarkedCoins getBookmarkedCoins;
    private final SearchBookmarkedCoins searchBookmarkedCoins;

    public Single<List<Coin>> bookmarkedCoins(CoinSortBy coinSortBy, OrderBy orderBy) {
        return getBookmarkedCoins.invoke()
                .observeOn(AndroidSchedulers.mainThread())
                .map(x -> {
                    x.sort(CoinComparatorFactory.createComparator(coinSortBy, orderBy));
                    return x;
                });
    }

    public Single<List<Coin>> searchBookmarkedCoins(String query, CoinSortBy coinSortBy, OrderBy orderBy) {
        String q = query.toLowerCase();
        return searchBookmarkedCoins.invoke(q)
                .map(x -> {
                    x.sort(CoinComparatorFactory.createComparator(coinSortBy, orderBy));
                    return x;
                });
    }

    @Inject
    public BookmarksViewModel(GetBookmarkedCoins getBookmarkedCoins, SearchBookmarkedCoins searchBookmarkedCoins) {
        this.getBookmarkedCoins = getBookmarkedCoins;
        this.searchBookmarkedCoins = searchBookmarkedCoins;
    }
}

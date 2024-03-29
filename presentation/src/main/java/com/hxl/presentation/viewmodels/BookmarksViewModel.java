package com.hxl.presentation.viewmodels;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.GetBookmarkedCoins;
import com.hxl.domain.interactors.coins.SearchBookmarkedCoins;
import com.hxl.domain.model.Coin;
import com.hxl.presentation.OrderBy;
import com.hxl.presentation.coin.CoinSortBy;
import com.hxl.presentation.coin.CoinComparatorFactory;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class BookmarksViewModel extends ViewModel {

    private final GetBookmarkedCoins getBookmarkedCoins;
    private final SearchBookmarkedCoins searchBookmarkedCoins;

    public Single<List<Coin>> bookmarkedCoins(CoinSortBy coinSortBy, OrderBy orderBy) {
        return getBookmarkedCoins.invoke()
                .map(x -> x.stream()
                        .sorted(CoinComparatorFactory.createComparator(coinSortBy, orderBy))
                        .collect(Collectors.toList())
                );
    }

    public Single<List<Coin>> searchBookmarkedCoins(String query, CoinSortBy coinSortBy, OrderBy orderBy) {
        String q = query.toLowerCase();
        return searchBookmarkedCoins.invoke(q)
                .map(x -> x.stream()
                        .sorted(CoinComparatorFactory.createComparator(coinSortBy, orderBy))
                        .collect(Collectors.toList())
                );
    }

    @Inject
    public BookmarksViewModel(GetBookmarkedCoins getBookmarkedCoins, SearchBookmarkedCoins searchBookmarkedCoins) {
        this.getBookmarkedCoins = getBookmarkedCoins;
        this.searchBookmarkedCoins = searchBookmarkedCoins;
    }
}

package com.hxl.presentation.viewmodels;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.GetBookmarkedCoins;
import com.hxl.domain.model.Coin;
import com.hxl.presentation.OrderBy;
import com.hxl.presentation.coin.CoinSortBy;
import com.hxl.presentation.coin.CoinComparatorFactory;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class BookmarksViewModel extends ViewModel {

    private final GetBookmarkedCoins getBookmarkedCoins;

    public Single<List<Coin>> bookmarkedCoins(CoinSortBy coinSortBy, OrderBy orderBy) {
        return getBookmarkedCoins.invoke()
                .map(x -> {
                    x.sort(CoinComparatorFactory.createComparator(coinSortBy, orderBy));
                    return x;
                });
    }

    public Single<List<Coin>> bookmarkedCoins(String query, CoinSortBy coinSortBy, OrderBy orderBy) {
        String q = query.toLowerCase();
        return getBookmarkedCoins.invoke()
                .observeOn(AndroidSchedulers.mainThread())
                .map(x -> {
                    x.sort(CoinComparatorFactory.createComparator(coinSortBy, orderBy));
                    return x;
                })
                .map(x -> x.stream().filter(c -> (
                        (c.id.toLowerCase().startsWith(q))
                        || (c.symbol.toLowerCase().startsWith(q))
                        || (c.name.toLowerCase().startsWith(q))
                )).collect(Collectors.toList()));
    }

    @Inject
    public BookmarksViewModel(GetBookmarkedCoins getBookmarkedCoins) {
        this.getBookmarkedCoins = getBookmarkedCoins;
    }
}

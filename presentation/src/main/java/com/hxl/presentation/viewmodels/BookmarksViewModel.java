package com.hxl.presentation.viewmodels;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.GetBookmarkedCoins;
import com.hxl.domain.interactors.coins.UnBookmarkCoin;
import com.hxl.domain.model.Coin;
import com.hxl.presentation.SortCoin;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class BookmarksViewModel extends ViewModel {

    private final GetBookmarkedCoins getBookmarkedCoins;
    private final UnBookmarkCoin unBookmarkCoin;

    public Single<List<Coin>> bookmarkedCoins() {
        return getBookmarkedCoins.invoke()
                .map(x -> {
                    x.sort(SortCoin.by(SortCoin.SortType.TIMESTAMP_DESC));
                    return x;
                });
    }

    public Single<List<Coin>> bookmarkedCoins(SortCoin.SortType sortType) {
        return getBookmarkedCoins.invoke()
                .map(x -> {
                    x.sort(SortCoin.by(sortType));
                    return x;
                });
    }

    public Completable unBookmarkCoin(String id) {
        return unBookmarkCoin.invoke(id);
    }

    @Inject
    public BookmarksViewModel(GetBookmarkedCoins getBookmarkedCoins, UnBookmarkCoin unBookmarkCoin) {
        this.getBookmarkedCoins = getBookmarkedCoins;
        this.unBookmarkCoin = unBookmarkCoin;

    }
}
package com.hxl.presentation.viewmodels;

import static com.hxl.presentation.SortCoin.SortType.TIMESTAMP;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.GetBookmarkedCoins;
import com.hxl.domain.model.Coin;
import com.hxl.presentation.OrderBy;
import com.hxl.presentation.SortCoin;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class BookmarksViewModel extends ViewModel {

    private final GetBookmarkedCoins getBookmarkedCoins;

    public Single<List<Coin>> bookmarkedCoins() {
        return getBookmarkedCoins.invoke()
                .map(x -> {
                    x.sort(SortCoin.by(TIMESTAMP, OrderBy.DESC));
                    return x;
                });
    }

    public Single<List<Coin>> bookmarkedCoins(SortCoin.SortType sortType, OrderBy orderBy) {
        return getBookmarkedCoins.invoke()
                .map(x -> {
                    x.sort(SortCoin.by(sortType, orderBy));
                    return x;
                });
    }

    @Inject
    public BookmarksViewModel(GetBookmarkedCoins getBookmarkedCoins) {
        this.getBookmarkedCoins = getBookmarkedCoins;
    }
}

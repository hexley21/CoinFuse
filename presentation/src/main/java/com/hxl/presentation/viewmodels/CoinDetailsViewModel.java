package com.hxl.presentation.viewmodels;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.BookmarkCoin;
import com.hxl.domain.interactors.coins.GetCoin;
import com.hxl.domain.interactors.coins.IsCoinBookmarked;
import com.hxl.domain.interactors.coins.UnBookmarkCoin;
import com.hxl.domain.model.Coin;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class CoinDetailsViewModel extends ViewModel {

    private final GetCoin getCoin;

    private final IsCoinBookmarked isCoinBookmarked;
    private final BookmarkCoin bookmarkCoin;
    private final UnBookmarkCoin unBookmarkCoin;

    public Single<Coin> getCoin(String id){
        return getCoin.invoke(id);
    }

    public Single<Boolean> isCoinBookmarked(String id) {
        return isCoinBookmarked.invoke(id);
    }

    public Completable bookmarkCoin(String id) {
        return bookmarkCoin.invoke(id);
    }

    public Completable unBookmarkCoin(String id) {
        return unBookmarkCoin.invoke(id);
    }
    @Inject
    public CoinDetailsViewModel(
            GetCoin getCoin,
            IsCoinBookmarked isCoinBookmarked,
            BookmarkCoin bookmarkCoin,
            UnBookmarkCoin unBookmarkCoin
    ) {
        this.getCoin = getCoin;
        this.isCoinBookmarked = isCoinBookmarked;
        this.bookmarkCoin = bookmarkCoin;
        this.unBookmarkCoin = unBookmarkCoin;
    }
}

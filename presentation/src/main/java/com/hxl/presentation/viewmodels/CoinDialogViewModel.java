package com.hxl.presentation.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.BookmarkCoin;
import com.hxl.domain.interactors.coins.IsCoinBookmarked;
import com.hxl.domain.interactors.coins.UnBookmarkCoin;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class CoinDialogViewModel extends ViewModel {

    @NonNull private final IsCoinBookmarked isCoinBookmarked;
    @NonNull private final BookmarkCoin bookmarkCoin;
    @NonNull private final UnBookmarkCoin unBookmarkCoin;

    @Inject
    public CoinDialogViewModel(@NonNull IsCoinBookmarked isCoinBookmarked, @NonNull BookmarkCoin bookmarkCoin, @NonNull UnBookmarkCoin unBookmarkCoin) {
        this.isCoinBookmarked = isCoinBookmarked;
        this.bookmarkCoin = bookmarkCoin;
        this.unBookmarkCoin = unBookmarkCoin;
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


}

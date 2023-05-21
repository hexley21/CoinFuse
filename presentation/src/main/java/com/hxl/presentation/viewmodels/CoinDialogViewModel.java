package com.hxl.presentation.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.BookmarkCoin;
import com.hxl.domain.interactors.coins.DeleteCoinSearchQuery;
import com.hxl.domain.interactors.coins.IsCoinBookmarked;
import com.hxl.domain.interactors.coins.UnBookmarkCoin;
import com.hxl.presentation.livedata.StateLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class CoinDialogViewModel extends ViewModel {

    private static final String TAG = "CoinDialogVM";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @NonNull private final IsCoinBookmarked isCoinBookmarked;
    @NonNull private final BookmarkCoin bookmarkCoin;
    @NonNull private final UnBookmarkCoin unBookmarkCoin;
    @NonNull private final DeleteCoinSearchQuery deleteCoinSearchQuery;

    @Inject
    public CoinDialogViewModel(@NonNull IsCoinBookmarked isCoinBookmarked, @NonNull BookmarkCoin bookmarkCoin, @NonNull UnBookmarkCoin unBookmarkCoin, @NonNull DeleteCoinSearchQuery deleteCoinSearchQuery) {
        this.isCoinBookmarked = isCoinBookmarked;
        this.bookmarkCoin = bookmarkCoin;
        this.unBookmarkCoin = unBookmarkCoin;
        this.deleteCoinSearchQuery = deleteCoinSearchQuery;
    }

    public StateLiveData<Void> currentDeleteState;

    public StateLiveData<Void> getCurrentDeleteState() {
        if (currentDeleteState == null) {
            this.currentDeleteState = new StateLiveData<>();
        }

        return currentDeleteState;
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

    public void deleteSearchQuery(String query) {
        getCurrentDeleteState().setLoading();
        deleteCoinSearchQuery.invoke(query)
                .subscribe(
                        () -> {
                            getCurrentDeleteState().setSuccess(null);
                            Log.d(TAG, "getDeleteCoinSearchQuery: success");
                        },
                        e -> {
                            getCurrentDeleteState().setError(e);
                            Log.e(TAG, "getDeleteCoinSearchQuery: failed", e);
                        },
                        compositeDisposable
                );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}

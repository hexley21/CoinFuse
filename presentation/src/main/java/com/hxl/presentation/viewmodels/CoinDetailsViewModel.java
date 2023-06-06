package com.hxl.presentation.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.BookmarkCoin;
import com.hxl.domain.interactors.coins.IsCoinBookmarked;
import com.hxl.domain.interactors.coins.UnBookmarkCoin;
import com.hxl.presentation.livedata.StateLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class CoinDetailsViewModel extends ViewModel {

    private static final String TAG = "CoinDetailsViewModel";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @NonNull private final IsCoinBookmarked isCoinBookmarked;
    @NonNull private final BookmarkCoin bookmarkCoin;
    @NonNull private final UnBookmarkCoin unBookmarkCoin;

    private StateLiveData<Boolean> currentBookmarkState;

    public StateLiveData<Boolean> getCurrentBookmarkState() {
        if (currentBookmarkState == null) {
            currentBookmarkState = new StateLiveData<>();
        }

        return currentBookmarkState;
    }

    public void fetchBookmarkState(String id) {
        getCurrentBookmarkState().setLoading();
        isCoinBookmarked.invoke(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        bool -> {
                            getCurrentBookmarkState().setSuccess(bool);
                            Log.d(TAG, "fetchBookmarkState: success");
                        },
                        e -> {
                            getCurrentBookmarkState().setError(e);
                            Log.d(TAG, "fetchBookmarkState: failed", e);
                        },
                        compositeDisposable
                );
    }

    public void bookmarkCoin(String id) {
        bookmarkCoin.invoke(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.d(TAG, "bookmarkCoin: success"),
                        e -> Log.e(TAG, "bookmarkCoin: failed", e),
                        compositeDisposable
                );
    }

    public void unBookmarkCoin(String id) {
        unBookmarkCoin.invoke(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.d(TAG, "unBookmarkCoin: success"),
                        e -> Log.e(TAG, "unBookmarkCoin: failed", e),
                        compositeDisposable
                );
    }

    @Inject
    public CoinDetailsViewModel(
            @NonNull IsCoinBookmarked isCoinBookmarked,
            @NonNull BookmarkCoin bookmarkCoin,
            @NonNull UnBookmarkCoin unBookmarkCoin
    ) {
        this.isCoinBookmarked = isCoinBookmarked;
        this.bookmarkCoin = bookmarkCoin;
        this.unBookmarkCoin = unBookmarkCoin;
    }
}

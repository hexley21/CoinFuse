package com.hxl.presentation.viewmodels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.EraseBookmarks;
import com.hxl.domain.interactors.coins.EraseCoinCache;
import com.hxl.domain.interactors.coins.EraseCoinSearchHistory;
import com.hxl.domain.interactors.exchanges.EraseExchange;
import com.hxl.domain.interactors.prefs.GetLanguage;
import com.hxl.domain.interactors.prefs.GetTheme;
import com.hxl.domain.interactors.prefs.SaveLanguage;
import com.hxl.domain.interactors.prefs.SaveTheme;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class SettingsViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String TAG = "SettingsViewModel";

    @NotNull private final GetLanguage getLanguage;
    @NotNull private final GetTheme getTheme;
    @NotNull private final SaveLanguage saveLanguage;
    @NotNull private final SaveTheme saveTheme;
    @NotNull private final EraseExchange eraseExchange;
    @NotNull private final EraseCoinSearchHistory eraseCoinSearchHistory;
    @NotNull private final EraseBookmarks eraseBookmarks;
    @NotNull private final EraseCoinCache eraseCoinCache;

    @Inject
    public SettingsViewModel(@NotNull GetLanguage getLanguage, @NotNull GetTheme getTheme, @NotNull SaveLanguage saveLanguage, @NotNull SaveTheme saveTheme, @NotNull EraseExchange eraseExchange, @NotNull EraseCoinSearchHistory eraseCoinSearchHistory, @NotNull EraseBookmarks eraseBookmarks, @NotNull EraseCoinCache eraseCoinCache) {
        this.getLanguage = getLanguage;
        this.getTheme = getTheme;
        this.saveLanguage = saveLanguage;
        this.saveTheme = saveTheme;
        this.eraseExchange = eraseExchange;
        this.eraseCoinSearchHistory = eraseCoinSearchHistory;
        this.eraseBookmarks = eraseBookmarks;
        this.eraseCoinCache = eraseCoinCache;
    }

    private Completable eraseCacheCompletable() {
        return eraseCoinCache.invoke()
                .concatWith(eraseExchange.invoke())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public String getLanguage() {
        return getLanguage.invoke();
    }

    public int getTheme() {
        return getTheme.invoke();
    }

    public void saveLanguage(String language) {
        saveLanguage.invoke(language);
    }

    public void saveTheme(int theme) {
        saveTheme.invoke(theme);
    }


    public void eraseCoinSearchHistory(Runnable onSuccess, Runnable onFail) {
        eraseCoinSearchHistory.invoke()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            onSuccess.run();
                            Log.d(TAG, "eraseBookmarks: success");
                        },
                        e -> {
                            onFail.run();
                            Log.e(TAG, "eraseBookmarks: failed", e);
                        },
                        compositeDisposable
                );
    }

    public void eraseBookmarks(Runnable onSuccess, Runnable onFail) {
        eraseBookmarks.invoke()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            onSuccess.run();
                            Log.d(TAG, "eraseBookmarks: success");
                        },
                        e -> {
                            onFail.run();
                            Log.e(TAG, "eraseBookmarks: failed", e);
                        },
                        compositeDisposable
                );
    }

    public void eraseCache(Runnable onSuccess, Runnable onFail) {
        eraseCacheCompletable().subscribe(
                        () -> {
                            onSuccess.run();
                            Log.d(TAG, "eraseCache: success");
                        },
                        e -> {
                            onFail.run();
                            Log.e(TAG, "eraseCache: failed", e);
                        },
                        compositeDisposable
                );
    }

    public void eraseStorage(Runnable onSuccess, Runnable onFail) {
        eraseBookmarks.invoke()
                .concatWith(eraseCoinSearchHistory.invoke())
                .concatWith(eraseCacheCompletable())
                .subscribe(
                        () -> {
                            onSuccess.run();
                            Log.d(TAG, "eraseStorage: success");
                        },
                        e -> {
                            onFail.run();
                            Log.e(TAG, "eraseStorage: failed", e);
                        },
                        compositeDisposable
                );
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
        Log.d(TAG, "onCleared: CompositeDisposable was cleared");
    }
}

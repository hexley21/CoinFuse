package com.hxl.presentation.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.EraseBookmarks;
import com.hxl.domain.interactors.coins.EraseCoinCache;
import com.hxl.domain.interactors.coins.EraseCoinSearchHistory;
import com.hxl.domain.interactors.exchanges.EraseExchange;
import com.hxl.domain.interactors.prefs.GetCurrency;
import com.hxl.domain.interactors.prefs.GetLanguage;
import com.hxl.domain.interactors.prefs.GetTheme;
import com.hxl.domain.interactors.prefs.SaveCurrency;
import com.hxl.domain.interactors.prefs.SaveLanguage;
import com.hxl.domain.interactors.prefs.SaveTheme;
import com.hxl.domain.model.PrefKeys;

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

    @NotNull private final GetCurrency getCurrency;
    @NotNull private final GetLanguage getLanguage;
    @NotNull private final GetTheme getTheme;
    @NotNull private final SaveCurrency saveCurrency;
    @NotNull private final SaveLanguage saveLanguage;
    @NotNull private final SaveTheme saveTheme;
    @NotNull private final EraseExchange eraseExchange;
    @NotNull private final EraseCoinSearchHistory eraseCoinSearchHistory;
    @NotNull private final EraseBookmarks eraseBookmarks;
    @NotNull private final EraseCoinCache eraseCoinCache;

    @Inject
    public SettingsViewModel(@NotNull GetCurrency getCurrency, @NotNull GetLanguage getLanguage, @NotNull GetTheme getTheme, @NotNull SaveCurrency saveCurrency, @NotNull SaveLanguage saveLanguage, @NotNull SaveTheme saveTheme, @NotNull EraseExchange eraseExchange, @NotNull EraseCoinSearchHistory eraseCoinSearchHistory, @NotNull EraseBookmarks eraseBookmarks, @NotNull EraseCoinCache eraseCoinCache) {
        this.getCurrency = getCurrency;
        this.getLanguage = getLanguage;
        this.getTheme = getTheme;
        this.saveCurrency = saveCurrency;
        this.saveLanguage = saveLanguage;
        this.saveTheme = saveTheme;
        this.eraseExchange = eraseExchange;
        this.eraseCoinSearchHistory = eraseCoinSearchHistory;
        this.eraseBookmarks = eraseBookmarks;
        this.eraseCoinCache = eraseCoinCache;
    }

    private MutableLiveData<Boolean> currentEraseCache;
    private MutableLiveData<Boolean> currentEraseBookmarks;
    private MutableLiveData<Boolean> currentEraseSearchHistory;
    private MutableLiveData<Boolean> currentEraseStorage;

    private Completable eraseCacheCompletable() {
        return eraseCoinCache.invoke()
                .concatWith(eraseExchange.invoke())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public MutableLiveData<Boolean> getCurrentEraseCache() {
        if (currentEraseCache == null) {
            currentEraseCache = new MutableLiveData<>();
        }

        return currentEraseCache;
    }

    public MutableLiveData<Boolean> getCurrentEraseBookmarks() {
        if (currentEraseBookmarks == null) {
            currentEraseBookmarks = new MutableLiveData<>();
        }

        return currentEraseBookmarks;
    }

    public MutableLiveData<Boolean> getCurrentEraseSearchHistory() {
        if (currentEraseSearchHistory == null) {
            currentEraseSearchHistory = new MutableLiveData<>();
        }

        return currentEraseSearchHistory;
    }

    public MutableLiveData<Boolean> getCurrentEraseStorage() {
        if (currentEraseStorage == null) {
            currentEraseStorage = new MutableLiveData<>();
        }

        return currentEraseStorage;
    }

    public String getCurrency() {
        return getCurrency.invoke();
    }

    public String getLanguage() {
        return getLanguage.invoke();
    }

    public int getTheme() {
        return getTheme.invoke();
    }

    public void saveCurrency(String currency) {
        saveCurrency.invoke(currency);
    }

    public void saveLanguage(String language) {
        saveLanguage.invoke(language);
    }

    public void saveTheme(int theme) {
        saveTheme.invoke(theme);
    }


    public void eraseCoinSearchHistory() {
        eraseCoinSearchHistory.invoke()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            getCurrentEraseSearchHistory().setValue(true);
                            Log.d(TAG, "eraseBookmarks: success");
                        },
                        e -> {
                            getCurrentEraseSearchHistory().setValue(false);
                            Log.e(TAG, "eraseBookmarks: failed", e);
                        },
                        compositeDisposable
                );
    }

    public void eraseBookmarks() {
        eraseBookmarks.invoke()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            getCurrentEraseBookmarks().setValue(true);
                            Log.d(TAG, "eraseBookmarks: success");
                        },
                        e -> {
                            getCurrentEraseBookmarks().setValue(true);
                            Log.e(TAG, "eraseBookmarks: failed", e);
                        },
                        compositeDisposable
                );
    }

    public void eraseCache() {
        eraseCacheCompletable().subscribe(
                        () -> {
                            getCurrentEraseCache().setValue(true);
                            Log.d(TAG, "eraseCache: success");
                        },
                        e -> {
                            getCurrentEraseCache().setValue(false);
                            Log.e(TAG, "eraseCache: failed", e);
                        },
                        compositeDisposable
                );
    }

    public void eraseStorage() {
        eraseBookmarks.invoke()
                .concatWith(eraseCoinSearchHistory.invoke())
                .concatWith(eraseCacheCompletable())
                .subscribe(
                        () -> {
                            getCurrentEraseStorage().setValue(true);
                            Log.d(TAG, "eraseStorage: success");
                        },
                        e -> {
                            getCurrentEraseStorage().setValue(false);
                            Log.e(TAG, "eraseStorage: failed", e);
                        },
                        compositeDisposable
                );
        saveTheme(PrefKeys.THEME.def);
        saveCurrency(PrefKeys.CURRENCY.def);
        saveLanguage(PrefKeys.LANGUAGE.def);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
        Log.d(TAG, "onCleared: CompositeDisposable was cleared");
    }
}

package com.hxl.presentation.viewmodels;

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

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Completable;

@HiltViewModel
public class SettingsViewModel extends ViewModel {

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

    public Completable getEraseExchange() {
        return eraseExchange.invoke();
    }

    public Completable getEraseCoinSearchHistory() {
        return eraseCoinSearchHistory.invoke();
    }

    public Completable getEraseBookmarks() {
        return eraseBookmarks.invoke();
    }

    public Completable getEraseCoinCache() {
        return eraseCoinCache.invoke();
    }

}

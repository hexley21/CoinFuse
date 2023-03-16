package com.hxl.cryptonumismatist.di;


import com.hxl.domain.interactors.coins.BookmarkCoin;
import com.hxl.domain.interactors.coins.GetBookmarkedCoins;
import com.hxl.domain.interactors.coins.GetCoin;
import com.hxl.domain.interactors.coins.GetCoins;
import com.hxl.domain.interactors.coins.SaveCoins;
import com.hxl.domain.interactors.coins.UnBookmarkCoin;
import com.hxl.domain.interactors.prefs.GetCurrency;
import com.hxl.domain.interactors.prefs.GetLanguage;
import com.hxl.domain.interactors.prefs.GetTheme;
import com.hxl.domain.interactors.prefs.GetWelcome;
import com.hxl.domain.interactors.prefs.SaveCurrency;
import com.hxl.domain.interactors.prefs.SaveLanguage;
import com.hxl.domain.interactors.prefs.SaveTheme;
import com.hxl.domain.interactors.prefs.SaveWelcome;
import com.hxl.domain.repository.CoinRepository;
import com.hxl.domain.repository.PreferenceRepository;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DomainModule {

    // region coins
    @Provides
    GetCoins provideGetCoins(CoinRepository coinRepository) {
        return new GetCoins(coinRepository);
    }

    @Provides
    GetCoin provideGetCoin(CoinRepository coinRepository) {
        return new GetCoin(coinRepository);
    }

    @Provides
    SaveCoins provideSaveCoins(CoinRepository coinRepository) {
        return new SaveCoins(coinRepository);
    }

    @Provides
    BookmarkCoin provideBookmarkCoin(CoinRepository coinRepository) {
        return new BookmarkCoin(coinRepository);
    }

    @Provides
    UnBookmarkCoin provideUnBookmarkCoin(CoinRepository coinRepository) {
        return new UnBookmarkCoin(coinRepository);
    }

    @Provides
    GetBookmarkedCoins provideGetBookmarkedCoins(CoinRepository coinRepository) {
        return new GetBookmarkedCoins(coinRepository);
    }
    // endregion

    // region prefs
    @Provides
    GetCurrency provideGetCurrency(PreferenceRepository preferenceRepository) {
        return new GetCurrency(preferenceRepository);
    }
    @Provides
    GetLanguage provideGetLanguage(PreferenceRepository preferenceRepository) {
        return new GetLanguage(preferenceRepository);
    }
    @Provides
    GetTheme provideGetTheme(PreferenceRepository preferenceRepository) {
        return new GetTheme(preferenceRepository);
    }
    @Provides
    GetWelcome provideGetWelcome(PreferenceRepository preferenceRepository) {
        return new GetWelcome(preferenceRepository);
    }

    @Provides
    SaveCurrency provideSaveCurrency(PreferenceRepository preferenceRepository) {
        return new SaveCurrency(preferenceRepository);
    }
    @Provides
    SaveLanguage provideSaveLanguage(PreferenceRepository preferenceRepository) {
        return new SaveLanguage(preferenceRepository);
    }
    @Provides
    SaveTheme provideSaveTheme(PreferenceRepository preferenceRepository) {
        return new SaveTheme(preferenceRepository);
    }
    @Provides
    SaveWelcome provideSaveWelcome(PreferenceRepository preferenceRepository) {
        return new SaveWelcome(preferenceRepository);
    }
    // endregion
}

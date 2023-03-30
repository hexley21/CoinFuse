package com.hxl.cryptonumismatist.presentation.fragments.coins.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.GetCoins;
import com.hxl.domain.interactors.coins.SearchCoins;
import com.hxl.domain.model.Coin;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class CoinsFragmentViewModel extends ViewModel {

    @NotNull private final GetCoins getCoins;
    @NotNull private final SearchCoins searchCoins;

    public Single<List<Coin>> getCoins() {
        return getCoins.invoke();
    }

    public Single<List<Coin>> searchCoins(String key) {
        return searchCoins.invoke(key);
    }

    @Inject
    public CoinsFragmentViewModel(@NonNull GetCoins getCoins, @NonNull SearchCoins searchCoins) {
        this.getCoins = getCoins;
        this.searchCoins = searchCoins;
    }
}

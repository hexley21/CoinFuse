package com.hxl.cryptonumismatist.presentation.fragments.coins.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.GetCoins;
import com.hxl.domain.model.Coin;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class CoinsFragmentViewModel extends ViewModel {

    @NotNull private final GetCoins getCoins;

    public Single<List<Coin>> getCoins() {
        return getCoins.invoke();
    }

    @Inject
    public CoinsFragmentViewModel(@NonNull GetCoins getCoins) {
        this.getCoins = getCoins;
    }
}

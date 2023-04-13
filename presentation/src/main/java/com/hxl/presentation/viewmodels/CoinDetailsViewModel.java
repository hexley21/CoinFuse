package com.hxl.presentation.viewmodels;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.GetCoin;
import com.hxl.domain.model.Coin;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class CoinDetailsViewModel extends ViewModel {

    private final GetCoin getCoin;

    public Single<Coin> getCoin(String id){
        return getCoin.invoke(id);
    }

    @Inject
    public CoinDetailsViewModel(GetCoin getCoin) {
        this.getCoin = getCoin;
    }
}

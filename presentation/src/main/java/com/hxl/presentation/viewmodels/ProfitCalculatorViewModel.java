package com.hxl.presentation.viewmodels;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.SearchCoins;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.annotations.NonNull;

@HiltViewModel
public class ProfitCalculatorViewModel extends ViewModel {

    @NonNull private final SearchCoins searchCoins;

    @Inject
    public ProfitCalculatorViewModel(@NonNull SearchCoins searchCoins){
        this.searchCoins = searchCoins;
    }

}

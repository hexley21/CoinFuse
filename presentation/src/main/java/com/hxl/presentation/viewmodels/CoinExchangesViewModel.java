package com.hxl.presentation.viewmodels;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CoinExchangesViewModel extends ViewModel {

    @Inject
    public CoinExchangesViewModel() {
    }
}

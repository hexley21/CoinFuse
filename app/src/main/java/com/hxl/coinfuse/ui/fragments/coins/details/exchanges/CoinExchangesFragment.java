package com.hxl.coinfuse.ui.fragments.coins.details.exchanges;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentCoinExchangesBinding;
import com.hxl.presentation.viewmodels.CoinExchangesViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CoinExchangesFragment extends BaseFragment<FragmentCoinExchangesBinding, CoinExchangesViewModel> {
    @Override
    protected FragmentCoinExchangesBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCoinExchangesBinding.inflate(inflater, container, false);
    }

    @Override
    protected CoinExchangesViewModel setViewModel() {
        return new ViewModelProvider(this).get(CoinExchangesViewModel.class);
    }
}

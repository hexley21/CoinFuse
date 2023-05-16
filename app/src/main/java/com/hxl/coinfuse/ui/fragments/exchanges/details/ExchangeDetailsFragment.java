package com.hxl.coinfuse.ui.fragments.exchanges.details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentExchangeDetailsBinding;
import com.hxl.presentation.viewmodels.ExchangeDetailsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExchangeDetailsFragment extends BaseFragment<FragmentExchangeDetailsBinding, ExchangeDetailsViewModel> {
    @Override
    protected FragmentExchangeDetailsBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentExchangeDetailsBinding.inflate(inflater, container, false);
    }

    @Override
    protected ExchangeDetailsViewModel setViewModel() {
        return new ViewModelProvider(this).get(ExchangeDetailsViewModel.class);
    }


}

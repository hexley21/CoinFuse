package com.hxl.coinfuse.ui.fragments.profit_cal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentProfitCalculatorBinding;
import com.hxl.presentation.viewmodels.ProfitCalculatorViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfitCalculatorFragment extends BaseFragment<FragmentProfitCalculatorBinding, ProfitCalculatorViewModel> {
    @Override
    protected FragmentProfitCalculatorBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProfitCalculatorBinding.inflate(inflater, container, false);
    }

    @Override
    protected Class<ProfitCalculatorViewModel> setViewModelClass() {
        return ProfitCalculatorViewModel.class;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        binding.setCurrency("$");
    }
}

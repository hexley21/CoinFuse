package com.hxl.coinfuse.ui.fragments.profit_cal;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentProfitCalculatorBinding;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.presentation.viewmodels.ProfitCalculatorViewModel;

import java.util.function.Consumer;

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

    private static final String TAG = "ProfitCalculatorFr";

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        binding.setCurrency("$");
        if (!vm.getCurrentProfit().hasObservers()) {
            vm.getCurrentProfit().observe(requireActivity(), val -> {
                binding.setProfit(val);
                if (val > 0) {
                    binding.profitResult.setTextColor(UiUtils.getColor(requireContext(), R.attr.growth));
                }
                else if (val < 0){
                    binding.profitResult.setTextColor(UiUtils.getColor(requireContext(), com.google.android.material.R.attr.colorError));
                }
                else {
                    binding.profitResult.setTextColor(UiUtils.getColor(requireContext(), com.google.android.material.R.attr.colorOnSurface));
                }
            });
        }
        if (!vm.getCurrentInvestment().hasObservers()) {
            vm.getCurrentInvestment().observe(requireActivity(), val -> {
                binding.setInvestment(val);
                if (val > 0) {
                    binding.profitInvestment.setTextColor(UiUtils.getColor(requireContext(), R.attr.growth));
                }
                else if (val < 0){
                    binding.profitInvestment.setTextColor(UiUtils.getColor(requireContext(), com.google.android.material.R.attr.colorError));
                }
                else {
                    binding.profitInvestment.setTextColor(UiUtils.getColor(requireContext(), com.google.android.material.R.attr.colorOnSurface));
                }
            });

        }

        if (!vm.getCurrentExit().hasObservers()) {
            vm.getCurrentExit().observe(requireActivity(), val -> {
                binding.setExit(val);
                if (val > 0) {
                    binding.profitExit.setTextColor(UiUtils.getColor(requireContext(), R.attr.growth));
                }
                else if (val < 0){
                    binding.profitExit.setTextColor(UiUtils.getColor(requireContext(), com.google.android.material.R.attr.colorError));
                }
                else {
                    binding.profitExit.setTextColor(UiUtils.getColor(requireContext(), com.google.android.material.R.attr.colorOnSurface));
                }
            });
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert binding.tfProfitInvestment.getEditText() != null;
        assert binding.tfProfitSell.getEditText() != null;
        assert binding.tfProfitInFee.getEditText() != null;
        assert binding.tfProfitExFee.getEditText() != null;
        assert binding.tfProfitBuy.getEditText() != null;

        binding.tfProfitBuy.getEditText().setText(vm.getBuyPriceField() == null ? "" : vm.getBuyPriceField().toString());
        binding.tfProfitInvestment.getEditText().setText(vm.getInvestmentField() == null ? "" : vm.getInvestmentField().toString());
        binding.tfProfitSell.getEditText().setText(vm.getSellPriceField() == null ? "" : vm.getSellPriceField().toString());
        binding.tfProfitInFee.getEditText().setText(vm.getInvestmentFeeField() == null ? "" : vm.getInvestmentFeeField().toString());
        binding.tfProfitExFee.getEditText().setText(vm.getExitFeeField() == null ? "" : vm.getExitFeeField().toString());

        binding.profitToolbar.setNavigationOnClickListener(v -> {
            binding.tfProfitBuy.getEditText().setText("");
            binding.tfProfitInvestment.getEditText().setText("");
            binding.tfProfitSell.getEditText().setText("");
            binding.tfProfitInFee.getEditText().setText("");
            binding.tfProfitExFee.getEditText().setText("");
            vm.clearEverything();
        });

        binding.tfProfitBuy.getEditText().addTextChangedListener(createTextWatcher(val -> vm.setBuyPriceField(val)));
        binding.tfProfitInvestment.getEditText().addTextChangedListener(createTextWatcher(val -> vm.setInvestmentField(val)));
        binding.tfProfitSell.getEditText().addTextChangedListener(createTextWatcher(val -> vm.setSellPriceField(val)));
        binding.tfProfitInFee.getEditText().addTextChangedListener(createTextWatcher(val -> vm.setInvestmentFeeField(val)));
        binding.tfProfitExFee.getEditText().addTextChangedListener(createTextWatcher(val -> vm.setExitFeeField(val)));

        binding.tfProfitBuy.addOnEndIconChangedListener((textInputLayout, previousIcon) -> vm.setBuyPriceField(0.0D));
        binding.tfProfitInvestment.addOnEndIconChangedListener((textInputLayout, previousIcon) -> vm.setInvestmentField(0.0D));
        binding.tfProfitSell.addOnEndIconChangedListener((textInputLayout, previousIcon) -> vm.setSellPriceField(0.0D));
        binding.tfProfitInFee.addOnEndIconChangedListener((textInputLayout, previousIcon) -> vm.setInvestmentFeeField(0.0D));
        binding.tfProfitExFee.addOnEndIconChangedListener((textInputLayout, previousIcon) -> vm.setExitFeeField(0.0D));
    }

    private TextWatcher createTextWatcher(Consumer<Double> val) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    val.accept(0.0);
                    return;
                }
                try {
                    val.accept(Double.parseDouble(s.toString()));
                }
                catch (Exception e) {
                    val.accept(0.0);
                    Log.e(TAG, "onTextChanged: failed", e);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

}

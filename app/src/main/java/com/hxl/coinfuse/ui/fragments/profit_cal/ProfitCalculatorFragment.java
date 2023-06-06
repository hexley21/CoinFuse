package com.hxl.coinfuse.ui.fragments.profit_cal;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.consumerArgKey;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.android.material.search.SearchBar;
import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentProfitCalculatorBinding;
import com.hxl.coinfuse.ui.dialogs.ParcelableConsumer;
import com.hxl.coinfuse.util.NumberFormatUtil;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.domain.model.Coin;
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

        if (savedInstanceState != null) {
            binding.profitSearchBar.setText(savedInstanceState.getString("searchTitle"));
        }

        binding.setCurrency("$");
        if (!vm.getCurrentProfit().hasObservers()) {
            vm.getCurrentProfit().observe(requireActivity(), val -> {
                binding.setProfit(NumberFormatUtil.formatDouble(val));
                binding.setChange(NumberFormatUtil.formatDouble(vm.getChange() * 100.0D));

                if (val > 0) {
                    binding.profitResult.setTextColor(UiUtils.getColor(requireContext(), R.attr.growth));
                    binding.profitChange.setTextColor(UiUtils.getColor(requireContext(), R.attr.growth));
                }
                else if (val < 0){
                    binding.profitResult.setTextColor(UiUtils.getColor(requireContext(), com.google.android.material.R.attr.colorError));
                    binding.profitChange.setTextColor(UiUtils.getColor(requireContext(), com.google.android.material.R.attr.colorError));
                }
                else {
                    binding.profitResult.setTextColor(UiUtils.getColor(requireContext(), com.google.android.material.R.attr.colorOnSurface));
                    binding.profitChange.setTextColor(UiUtils.getColor(requireContext(), com.google.android.material.R.attr.colorOnSurface));
                }
            });
        }
        if (!vm.getCurrentInvestment().hasObservers()) {
            vm.getCurrentInvestment().observe(requireActivity(), val -> {
                binding.setInvestment(NumberFormatUtil.formatDouble(val));
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
                binding.setExit(NumberFormatUtil.formatDouble(val));
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert binding.tfProfitInvestment.getEditText() != null;
        assert binding.tfProfitSell.getEditText() != null;
        assert binding.tfProfitInFee.getEditText() != null;
        assert binding.tfProfitExFee.getEditText() != null;
        assert binding.tfProfitBuy.getEditText() != null;

        binding.profitSearchBar.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(consumerArgKey, (ParcelableConsumer<Coin>) coin -> {
                if (coin == null) {
                    ((SearchBar) requireActivity().findViewById(R.id.search_bar)).setText("");
                    binding.tfProfitBuy.getEditText().setText("");
                    binding.tfProfitSell.getEditText().setText("");
                    vm.setBuyPriceField(null);
                    vm.setSellPriceField(null);
                    return;
                }
                binding.profitSearchBar.setText(coin.name);
                binding.tfProfitBuy.getEditText().setText(coin.priceUsd.toString());
                binding.tfProfitSell.getEditText().setText(coin.priceUsd.toString());
                vm.setBuyPriceField(coin.priceUsd);
                vm.setInvestment(coin.priceUsd);
            });
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_main).navigate(R.id.navigation_to_profitCalculatorDialog, bundle);
        });

        binding.tfProfitBuy.getEditText().setText(vm.getBuyPriceField() == null ? "" : vm.getBuyPriceField().toString());
        binding.tfProfitInvestment.getEditText().setText(vm.getInvestmentField() == null ? "" : vm.getInvestmentField().toString());
        binding.tfProfitSell.getEditText().setText(vm.getSellPriceField() == null ? "" : vm.getSellPriceField().toString());
        binding.tfProfitInFee.getEditText().setText(vm.getInvestmentFeeField() == null ? "" : vm.getInvestmentFeeField().toString());
        binding.tfProfitExFee.getEditText().setText(vm.getExitFeeField() == null ? "" : vm.getExitFeeField().toString());

        binding.profitToolbar.setNavigationOnClickListener(v -> {
            binding.profitSearchBar.setText("");
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            outState.putString("searchTitle", binding.profitSearchBar.getText() == null ? "" : binding.profitSearchBar.getText().toString());
        }
        catch (Exception e) {
            Log.e(TAG, "onSaveInstanceState: failed " + e.getMessage());
        }
    }
}

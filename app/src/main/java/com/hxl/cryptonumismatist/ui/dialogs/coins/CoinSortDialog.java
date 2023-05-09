package com.hxl.cryptonumismatist.ui.dialogs.coins;

import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.coinSortCallbackArgKey;
import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.isTimeSortableArgKey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatRadioButton;

import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.base.BaseDialog;
import com.hxl.cryptonumismatist.databinding.DialogCoinSortBinding;
import com.hxl.cryptonumismatist.util.UiUtils;
import com.hxl.presentation.OrderBy;
import com.hxl.presentation.coin.CoinSortBy;

public class CoinSortDialog extends BaseDialog<DialogCoinSortBinding> {
    @Override
    protected DialogCoinSortBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return DialogCoinSortBinding.inflate(inflater, container, false);
    }

    private CoinSortBy finalCoinSortBy = CoinSortBy.RANK;
    private OrderBy finalOrderBy = OrderBy.DESC;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        assert getArguments() != null;
        boolean isTimeSortable = getArguments().getBoolean(isTimeSortableArgKey);
        CoinSortCallback serializableFunction = getArguments().getParcelable(coinSortCallbackArgKey);
        bind(isTimeSortable, serializableFunction);
    }

    private void bind(boolean isTimeSortable, CoinSortCallback function) {
        if (!isTimeSortable) {
            binding.dialogCoinSortTime.setVisibility(View.GONE);
        }

        registerListener(binding.dialogCoinSortRank, CoinSortBy.RANK);
        registerListener(binding.dialogCoinSortName, CoinSortBy.NAME);
        registerListener(binding.dialogCoinSortPrice, CoinSortBy.PRICE);
        registerListener(binding.dialogCoinSortMarket, CoinSortBy.MARKET);
        registerListener(binding.dialogCoinSortVolume, CoinSortBy.VOLUME);
        registerListener(binding.dialogCoinSortChange, CoinSortBy.CHANGE);
        registerListener(binding.dialogCoinSortTime, CoinSortBy.TIMESTAMP);

        binding.sortApply.setOnClickListener(v -> {
            dismiss();
            function.apply(finalCoinSortBy, finalOrderBy);
        });

        binding.chipOrderBy.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                finalOrderBy = OrderBy.ASC;
                binding.chipOrderBy.setText(UiUtils.getString(requireContext(), R.string.sort_by_asc));
                binding.chipOrderBy.setChipIcon(UiUtils.getDrawable(requireContext(), R.drawable.arrow_upward));
            }
            else {
                finalOrderBy = OrderBy.DESC;
                binding.chipOrderBy.setText(UiUtils.getString(requireContext(), R.string.sort_by_desc));
                binding.chipOrderBy.setChipIcon(UiUtils.getDrawable(requireContext(), R.drawable.arrow_downward));
            }
        });
    }

    private void registerListener(AppCompatRadioButton radioButton, CoinSortBy coinSortBy) {
        radioButton.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                finalCoinSortBy = coinSortBy;
            }
        });
    }

}

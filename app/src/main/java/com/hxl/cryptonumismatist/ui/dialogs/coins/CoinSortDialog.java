package com.hxl.cryptonumismatist.ui.dialogs.coins;

import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.coinSortCallbackArgKey;
import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.isTimeSortableArgKey;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.base.BaseDialog;
import com.hxl.cryptonumismatist.databinding.DialogCoinSortBinding;
import com.hxl.cryptonumismatist.util.UiUtils;
import com.hxl.presentation.SortBy;
import com.hxl.presentation.SortCoin;

public class CoinSortDialog extends BaseDialog<DialogCoinSortBinding> {
    @Override
    protected DialogCoinSortBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return DialogCoinSortBinding.inflate(inflater, container, false);
    }

    private SortCoin.SortType finalSortType = SortCoin.SortType.NONE;
    private SortBy finalSortBy = SortBy.DESC;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        assert getArguments() != null;
        boolean isTimeSortable = getArguments().getBoolean(isTimeSortableArgKey);
        CoinSortCallback serializableFunction = (CoinSortCallback) getArguments().getParcelable(coinSortCallbackArgKey);
        bind(isTimeSortable, serializableFunction);
    }

    private void bind(boolean isTimeSortable, CoinSortCallback function) {
        if (!isTimeSortable) {
            binding.dialogCoinSortTime.setVisibility(View.GONE);
        }

        registerListener(binding.dialogCoinSortRank, SortCoin.SortType.RANK);
        registerListener(binding.dialogCoinSortName, SortCoin.SortType.NAME);
        registerListener(binding.dialogCoinSortPrice, SortCoin.SortType.PRICE);
        registerListener(binding.dialogCoinSortMarket, SortCoin.SortType.MARKET);
        registerListener(binding.dialogCoinSortVolume, SortCoin.SortType.VOLUME);
        registerListener(binding.dialogCoinSortChange, SortCoin.SortType.CHANGE);
        registerListener(binding.dialogCoinSortTime, SortCoin.SortType.TIMESTAMP);

        binding.sortApply.setOnClickListener(v -> function.apply(finalSortType, finalSortBy));
    }

    private void registerListener(CheckBox checkBox, SortCoin.SortType sortType) {
        checkBox.setOnCheckedChangeListener((v, b) -> {
            finalSortType = sortType;
            Drawable drawableStart = checkBox.getCompoundDrawablesRelative()[0];
            if(b) {
                finalSortBy = SortBy.DESC;
                checkBox.setCompoundDrawablesRelative(
                        drawableStart,
                        null,
                        UiUtils.getDrawable(requireContext(), R.drawable.arrow_downward),
                        null);
            }
            else {
                finalSortBy = SortBy.ASC;
                checkBox.setCompoundDrawablesRelative(
                        drawableStart,
                        null,
                        UiUtils.getDrawable(requireContext(), R.drawable.arrow_upward),
                        null);
            }
        });

    }

}

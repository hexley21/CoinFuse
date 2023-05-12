package com.hxl.coinfuse.ui.dialogs.coins;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinSortCallbackArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.isTimeSortableArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.orderByArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.sortByArgKey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatRadioButton;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseDialog;
import com.hxl.coinfuse.databinding.DialogCoinSortBinding;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.presentation.OrderBy;
import com.hxl.presentation.coin.CoinSortBy;

public class CoinSortDialog extends BaseDialog<DialogCoinSortBinding> {
    @Override
    protected DialogCoinSortBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return DialogCoinSortBinding.inflate(inflater, container, false);
    }

    private CoinSortBy finalCoinSortBy;
    private OrderBy finalOrderBy;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        assert getArguments() != null;
        boolean isTimeSortable = getArguments().getBoolean(isTimeSortableArgKey);
        CoinSortCallback serializableFunction = getArguments().getParcelable(coinSortCallbackArgKey);
        finalCoinSortBy = (CoinSortBy) getArguments().getSerializable(sortByArgKey);
        finalOrderBy = (OrderBy) getArguments().getSerializable(orderByArgKey);
        bind(isTimeSortable, serializableFunction);
    }

    private void bind(boolean isTimeSortable, CoinSortCallback function) {
        if (!isTimeSortable) {
            binding.dialogCoinSortTime.setVisibility(View.GONE);
        }

        switch (finalCoinSortBy) {
            case NAME:
                binding.dialogCoinSortName.setChecked(true);
                break;
            case PRICE:
                binding.dialogCoinSortPrice.setChecked(true);
                break;
            case MARKET:
                binding.dialogCoinSortMarket.setChecked(true);
                break;
            case VOLUME:
                binding.dialogCoinSortVolume.setChecked(true);
                break;
            case CHANGE:
                binding.dialogCoinSortChange.setChecked(true);
                break;
            case TIMESTAMP:
                binding.dialogCoinSortTime.setChecked(true);
                break;
        }

        if (finalOrderBy == OrderBy.ASC) {
            binding.chipOrderBy.setChecked(true);
            binding.chipOrderBy.setText(UiUtils.getString(requireContext(), R.string.sort_by_asc));
            binding.chipOrderBy.setChipIcon(UiUtils.getDrawable(requireContext(), R.drawable.arrow_upward));
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
            assert getArguments() != null;
            CoinSortBy oldSort = (CoinSortBy) getArguments().getSerializable(sortByArgKey);
            OrderBy oldOrder = (OrderBy) getArguments().getSerializable(orderByArgKey);
            if ((oldSort != finalCoinSortBy) || (oldOrder != finalOrderBy)) {
                function.apply(finalCoinSortBy, finalOrderBy);
            }
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

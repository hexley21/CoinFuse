package com.hxl.coinfuse.ui.dialogs.exchanges;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.orderByArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.sortByArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.sortCallbackArgKey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatRadioButton;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseDialog;
import com.hxl.coinfuse.databinding.DialogExchangeSortBinding;
import com.hxl.coinfuse.ui.dialogs.SortCallback;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.presentation.OrderBy;
import com.hxl.presentation.exchange.ExchangeSortBy;

public class ExchangeSortDialog extends BaseDialog<DialogExchangeSortBinding> {
    @Override
    protected DialogExchangeSortBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return DialogExchangeSortBinding.inflate(inflater, container, false);
    }

    private ExchangeSortBy finalCoinSortBy;
    private OrderBy finalOrderBy;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        assert getArguments() != null;
        SortCallback<ExchangeSortBy> serializableFunction = getArguments().getParcelable(sortCallbackArgKey);
        finalCoinSortBy = (ExchangeSortBy) getArguments().getSerializable(sortByArgKey);
        finalOrderBy = (OrderBy) getArguments().getSerializable(orderByArgKey);
        bind(serializableFunction);
    }

    private void bind(SortCallback<ExchangeSortBy> function) {

        switch (finalCoinSortBy) {
            case NAME:
                binding.dialogExchangeSortName.setChecked(true);
                break;
            case TOTAL_VOLUME_SHARE:
                binding.dialogExchangeSortTotalVolume.setChecked(true);
                break;
            case VOLUME:
                binding.dialogExchangeSortVolume.setChecked(true);
                break;
            case TRADING_PAIRS:
                binding.dialogExchangeSortPairs.setChecked(true);
                break;
            case SOCKET:
                binding.dialogExchangeSortSocket.setChecked(true);
                break;
            case UPDATED:
                binding.dialogExchangeSortUpdate.setChecked(true);
                break;
        }

        if (finalOrderBy == OrderBy.ASC) {
            binding.chipOrderBy.setChecked(true);
            binding.chipOrderBy.setText(UiUtils.getString(requireContext(), R.string.sort_by_asc));
            binding.chipOrderBy.setChipIcon(UiUtils.getDrawable(requireContext(), R.drawable.arrow_upward));
        }

        registerListener(binding.dialogExchangeSortRank, ExchangeSortBy.RANK);
        registerListener(binding.dialogExchangeSortName, ExchangeSortBy.NAME);
        registerListener(binding.dialogExchangeSortTotalVolume, ExchangeSortBy.TOTAL_VOLUME_SHARE);
        registerListener(binding.dialogExchangeSortVolume, ExchangeSortBy.VOLUME);
        registerListener(binding.dialogExchangeSortPairs, ExchangeSortBy.TRADING_PAIRS);
        registerListener(binding.dialogExchangeSortSocket, ExchangeSortBy.SOCKET);
        registerListener(binding.dialogExchangeSortUpdate, ExchangeSortBy.UPDATED);

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

    private void registerListener(AppCompatRadioButton radioButton, ExchangeSortBy exchangeSortBy) {
        radioButton.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                finalCoinSortBy = exchangeSortBy;
            }
        });
    }

}

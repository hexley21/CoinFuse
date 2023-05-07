package com.hxl.cryptonumismatist.ui.dialogs.coins;

import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.isTimeSortableArgKey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxl.cryptonumismatist.base.BaseDialog;
import com.hxl.cryptonumismatist.databinding.DialogCoinSortBinding;

public class CoinSortDialog extends BaseDialog<DialogCoinSortBinding> {
    @Override
    protected DialogCoinSortBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return DialogCoinSortBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        assert getArguments() != null;
        boolean isTimeSortable = getArguments().getBoolean(isTimeSortableArgKey);

        bind(isTimeSortable);
    }

    private void bind(boolean isTimeSortable) {

        if (!isTimeSortable) {
            binding.dialogCoinSortTime.setVisibility(View.GONE);
        }
    }

}

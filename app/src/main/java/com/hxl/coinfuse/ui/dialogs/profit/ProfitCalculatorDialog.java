package com.hxl.coinfuse.ui.dialogs.profit;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.consumerArgKey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import com.hxl.coinfuse.base.BaseDialog;
import com.hxl.coinfuse.databinding.DialogProfitCoinSearchBinding;
import com.hxl.coinfuse.ui.dialogs.ParcelableConsumer;
import com.hxl.coinfuse.ui.fragments.coins.main.adapter.CoinSearchAdapter;
import com.hxl.coinfuse.util.EspressoIdlingResource;
import com.hxl.domain.model.Coin;
import com.hxl.presentation.livedata.DataState;
import com.hxl.presentation.viewmodels.ProfitCalculatorDialogViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfitCalculatorDialog extends BaseDialog<DialogProfitCoinSearchBinding> {
    @Override
    protected DialogProfitCoinSearchBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return DialogProfitCoinSearchBinding.inflate(inflater, container, false);
    }

    private ProfitCalculatorDialogViewModel vm;
    private CoinSearchAdapter coinSearchAdapter;
    private ParcelableConsumer<Coin> callback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this).get(ProfitCalculatorDialogViewModel.class);
        if (callback == null) {
            assert getArguments() != null;
            callback = getArguments().getParcelable(consumerArgKey);
        }
        assert callback != null;
        coinSearchAdapter = new CoinSearchAdapter();
        coinSearchAdapter.setOnEndCallBack(coin -> {
            callback.accept(coin);
            dismiss();
        });
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);

        binding.chipNone.setOnClickListener(v -> {
            callback.accept(null);
            dismiss();
        });

        vm.getCurrentSearch().observe(requireActivity(), search -> {
            if (search.getState() == DataState.LOADING)
                EspressoIdlingResource.increment();
            else if (search.getState() == DataState.SUCCESS) {
                coinSearchAdapter.setList(search.getData());
                EspressoIdlingResource.decrement();
            } else if (search.getState() == DataState.ERROR) {
                Toast.makeText(requireContext(), search.getError().getMessage(), Toast.LENGTH_SHORT).show();
                EspressoIdlingResource.decrement();
            }
        });

        vm.fetchSearch("");

        binding.rvProfitSearch.setAdapter(coinSearchAdapter);

        binding.searchBarCoinProfit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                vm.fetchSearch(newText);
                return false;
            }
        });
    }
}

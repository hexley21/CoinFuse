package com.hxl.coinfuse.ui.fragments.coins.details.exchanges;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinArgKey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentCoinExchangesBinding;
import com.hxl.coinfuse.ui.fragments.exchanges.details.TradesAdapter;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.presentation.livedata.DataState;
import com.hxl.presentation.viewmodels.CoinExchangesViewModel;

import java.net.UnknownHostException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CoinExchangesFragment extends BaseFragment<FragmentCoinExchangesBinding, CoinExchangesViewModel> {
    @Override
    protected FragmentCoinExchangesBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCoinExchangesBinding.inflate(inflater, container, false);
    }

    @Override
    protected Class<CoinExchangesViewModel> setViewModelClass() {
        return CoinExchangesViewModel.class;
    }


    private final TradesAdapter tradesAdapter = new TradesAdapter(true);
    private String coinId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        coinId = getArguments().getString(coinArgKey);
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);

        vm.getCurrentTrades().observe(requireActivity(), exchanges -> {
            if (exchanges.getState() == DataState.LOADING) {
                return;
            } else if (exchanges.getState() == DataState.SUCCESS) {
                if (exchanges.getData().isEmpty()) {
                    showError(new IllegalStateException(UiUtils.getString(requireContext(), R.string.error_no_data)));
                    hidePageLoading();
                    return;
                }
                tradesAdapter.setList(exchanges.getData());
                hideError();
            } else if (exchanges.getState() == DataState.ERROR) {
                showError(exchanges.getError());
            }
            hidePageLoading();
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvCoinExchanges.setAdapter(tradesAdapter);

        vm.fetchTrades(coinId);

        binding.srlCoinExchanges.setOnRefreshListener(() -> vm.fetchTrades(coinId));

        final View.OnClickListener onErrorClick = v -> {
            binding.srlCoinExchanges.setRefreshing(true);
            vm.fetchTrades(coinId);
        };

        binding.iconError.setOnClickListener(onErrorClick);
        binding.iconWifiOff.setOnClickListener(onErrorClick);
        binding.errorText.setOnClickListener(onErrorClick);
    }


    private void hidePageLoading() {
        binding.shimmerCoinExchanges.setVisibility(View.GONE);
        binding.srlCoinExchanges.setVisibility(View.VISIBLE);
        binding.srlCoinExchanges.setRefreshing(false);
    }

    private void showError(Throwable e) {
        binding.srlCoinExchanges.setRefreshing(false);

        if (tradesAdapter.getItemCount() > 0) {
            if (e instanceof UnknownHostException) {
                showSnackBar(UiUtils.getString(requireContext(), R.string.error_no_internet));
                return;
            }
            showSnackBar(e.getMessage());
            return;
        }

        binding.errorText.setVisibility(View.VISIBLE);
        if (e instanceof UnknownHostException) {
            binding.setErrorText(UiUtils.getString(requireContext(), R.string.error_no_internet));
            binding.iconError.setVisibility(View.GONE);
            binding.iconWifiOff.setVisibility(View.VISIBLE);
            return;
        }
        binding.iconWifiOff.setVisibility(View.GONE);
        binding.setErrorText(e.getMessage());
        binding.iconError.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        binding.iconError.setVisibility(View.GONE);
        binding.iconWifiOff.setVisibility(View.GONE);
        binding.errorText.setVisibility(View.GONE);
    }

}

package com.hxl.coinfuse.ui.fragments.exchanges.details;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeNameArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangePairsArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeUrlArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeVolumeArgKey;
import static com.hxl.coinfuse.util.NumberFormatUtil.formatDouble;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentExchangeDetailsBinding;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.presentation.livedata.DataState;
import com.hxl.presentation.viewmodels.ExchangeDetailsViewModel;
import com.hxl.remote.exchange.api.TradeQueryBuilder;

import java.net.UnknownHostException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExchangeDetailsFragment extends BaseFragment<FragmentExchangeDetailsBinding, ExchangeDetailsViewModel> {
    @Override
    protected FragmentExchangeDetailsBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentExchangeDetailsBinding.inflate(inflater, container, false);
    }

    @Override
    protected ExchangeDetailsViewModel setViewModel() {
        return new ViewModelProvider(this).get(ExchangeDetailsViewModel.class);
    }

    private String exchangeId;
    private final TradesAdapter tradesAdapter = new TradesAdapter(false);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        exchangeId = getArguments().getString(exchangeArgKey);
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        bindArguments();

        vm.getCurrentExchange().observe(requireActivity(), exchange -> {
            if (exchange.getState() == DataState.SUCCESS) {
                binding.exchangesTopAppbar.setTitle(exchange.getData().name);
                binding.setPairs(String.valueOf(exchange.getData().tradingPairs));
                binding.setVolume(formatDouble(exchange.getData().volumeUsd));
            } else if (exchange.getState() == DataState.ERROR) {
                showSnackBar(exchange.getError().getMessage());
            }
        });

        vm.getCurrentTrades().observe(requireActivity(), trades -> {
            if (trades.getState() == DataState.LOADING) {
                return;
            } else if (trades.getState() == DataState.SUCCESS) {
                if (trades.getData().isEmpty()) {
                    showError(new IllegalStateException(UiUtils.getString(requireContext(), R.string.error_no_data)));
                    hideLoading();
                    return;
                }
                tradesAdapter.setList(trades.getData());
                hideError();
            } else if (trades.getState() == DataState.ERROR) {
                showError(trades.getError());
            }
            hideLoading();
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvExchangeTrades.setAdapter(tradesAdapter);

        fetchExchange();
        fetchTrades();


        binding.exchangesTopAppbar.setNavigationOnClickListener(v ->
                requireActivity().onBackPressed());


        binding.srlExchangeDetails.setOnRefreshListener(() -> {
            fetchExchange();
            fetchTrades();
        });

        final View.OnClickListener onErrorClick = v -> {
            fetchExchange();
            fetchTrades();
            binding.srlExchangeDetails.setRefreshing(true);
        };

        binding.iconTradesWifiOff.setOnClickListener(onErrorClick);
        binding.iconErrorTrades.setOnClickListener(onErrorClick);
        binding.errorTradesText.setOnClickListener(onErrorClick);
    }

    private void bindArguments() {
        assert getArguments() != null;
        final String exchangeName = getArguments().getString(exchangeNameArgKey);
        final String exchangeUrl = getArguments().getString(exchangeUrlArgKey);
        final String exchangeVolume = getArguments().getString(exchangeVolumeArgKey);
        final int exchangePairs = getArguments().getInt(exchangePairsArgKey);

        binding.exchangesTopAppbar.setTitle(exchangeName);
        binding.setPairs(String.valueOf(exchangePairs));
        binding.setVolume(exchangeVolume);
        binding.setCurrency("$");

        binding.exchangesTopAppbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_exchange_website) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(exchangeUrl)));
                return true;
            }
            return false;
        });

    }

    private void fetchExchange() {
        vm.fetchExchange(exchangeId);
    }

    private void fetchTrades() {
        TradeQueryBuilder queryBuilder = new TradeQueryBuilder().addExchangeId(exchangeId);
        vm.fetchTrades(queryBuilder.build());
    }

    // region visibility management
    private void hideLoading() {
        binding.shimmerTrades.setVisibility(View.GONE);
        binding.srlExchangeDetails.setVisibility(View.VISIBLE);
        binding.srlExchangeDetails.setRefreshing(false);
    }

    private void showError(Throwable e) {
        binding.srlExchangeDetails.setRefreshing(false);

        if (tradesAdapter.getItemCount() > 0) {
            if (e instanceof UnknownHostException) {
                showSnackBar(UiUtils.getString(requireContext(), R.string.error_no_internet));
                return;
            }
            showSnackBar(e.getMessage());
            return;
        }
        binding.errorTradesText.setVisibility(View.VISIBLE);
        if (e instanceof UnknownHostException) {
            binding.iconErrorTrades.setVisibility(View.GONE);
            binding.setErrorText(UiUtils.getString(requireContext(), R.string.error_no_internet));
            binding.iconTradesWifiOff.setVisibility(View.VISIBLE);
            return;
        }
        binding.iconTradesWifiOff.setVisibility(View.GONE);
        binding.setErrorText(e.getMessage());
        binding.iconErrorTrades.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        binding.errorTradesText.setVisibility(View.GONE);
        binding.iconTradesWifiOff.setVisibility(View.GONE);
        binding.iconErrorTrades.setVisibility(View.GONE);
    }
    // endregion
}

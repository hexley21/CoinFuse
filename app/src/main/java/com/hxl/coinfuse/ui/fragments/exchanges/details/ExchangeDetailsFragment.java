package com.hxl.coinfuse.ui.fragments.exchanges.details;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeArgKey;
import static com.hxl.coinfuse.util.NumberFormatUtil.formatDouble;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentExchangeDetailsBinding;
import com.hxl.coinfuse.util.EspressoIdlingResource;
import com.hxl.presentation.viewmodels.ExchangeDetailsViewModel;
import com.hxl.remote.exchange.api.TradeQueryBuilder;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

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

    private static final String TAG = "ExchangeDetails";

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
        binding.rvExchangeTrades.setAdapter(tradesAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.exchangesTopAppbar.setNavigationOnClickListener(v ->
                requireActivity().onBackPressed());

        fetchExchange();
        fetchTrades();
    }


    private void fetchExchange() {
        EspressoIdlingResource.increment();
        vm.getExchange(exchangeId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        exchange -> {
                            binding.exchangesTopAppbar.setTitle(exchange.name);
                            binding.setPairs(String.valueOf(exchange.tradingPairs));
                            binding.setVolume(formatDouble(exchange.volumeUsd));
                            binding.setCurrency("$");

                            binding.exchangesTopAppbar.setOnMenuItemClickListener(item -> {
                                if (item.getItemId() == R.id.menu_exchange_website) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(exchange.exchangeUrl)));
                                    return true;
                                }
                                return false;
                            });
                            Log.d(TAG, "fetchExchange: succeed");
                            EspressoIdlingResource.decrement();
                        },
                        e -> {
                            Log.e(TAG, "fetchExchange: failed", e);
                            EspressoIdlingResource.decrement();
                        },
                        compositeDisposable
                );
    }

    private void fetchTrades() {
        EspressoIdlingResource.increment();

        TradeQueryBuilder queryBuilder = new TradeQueryBuilder();
        queryBuilder.addExchangeId(exchangeId);

        vm.getTrades(queryBuilder.build()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        trades -> {
                            tradesAdapter.setList(trades);

                            Log.d(TAG, "fetchTrades: succeed");
                            EspressoIdlingResource.decrement();
                        },
                        e -> {
                            Log.e(TAG, "fetchTrades: failed", e);
                            EspressoIdlingResource.decrement();
                        },
                        compositeDisposable
                );
    }
}

package com.hxl.coinfuse.ui.fragments.coins.details.price;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.coinfuse.util.NumberFormatUtil.formatBigDouble;
import static com.hxl.coinfuse.util.NumberFormatUtil.formatDoubleDetailed;
import static com.hxl.coinfuse.util.NumberFormatUtil.formatFloat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentCoinPriceChartBinding;
import com.hxl.coinfuse.ui.fragments.coins.details.price.graph.DateAxisFormatter;
import com.hxl.coinfuse.ui.fragments.coins.details.price.graph.LineChartUtil;
import com.hxl.coinfuse.util.EspressoIdlingResource;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.presentation.viewmodels.CoinPriceViewModel;

import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

@AndroidEntryPoint
public class CoinPriceFragment extends BaseFragment<FragmentCoinPriceChartBinding, CoinPriceViewModel> {
    @Override
    protected FragmentCoinPriceChartBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCoinPriceChartBinding.inflate(inflater, container, false);
    }

    @Override
    protected CoinPriceViewModel setViewModel() {
        return new ViewModelProvider(this).get(CoinPriceViewModel.class);
    }
    private static final String TAG = "CoinPriceFragment";
    private String coinId;
    private LineChartUtil chartUtil;
    private CoinPriceHistory.Interval finalInterval = CoinPriceHistory.Interval.D1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        coinId = getArguments().getString(coinArgKey);
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        final Observer<Coin> coinObserver = coin -> {
            binding.setSymbol(coin.symbol);
            // Set Price and Currency
            binding.setPrice(formatDoubleDetailed(coin.priceUsd));
            binding.setCurrency("$");
            // Set change in %, color and check for null
            if (coin.changePercent24Hr != null) {
                if (coin.changePercent24Hr >= 0) {
                    binding.setChSmbl(getResources().getString(R.string.arrow_up));
                    binding.tvChange.setTextColor(UiUtils.getColor(requireContext(), R.attr.growth));
                } else {
                    binding.setChSmbl(getResources().getString(R.string.arrow_down));
                    binding.tvChange.setTextColor(UiUtils.getColor(requireContext(), com.google.android.material.R.attr.colorError));
                }
                binding.setChange(formatFloat(Math.abs(coin.changePercent24Hr)));
            } else {
                binding.tvChange.setText("");
            }
            // Set fetch timestamp
            Instant instant = Instant.ofEpochMilli(coin.timestamp);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd MMM yyyy", Locale.getDefault());
            binding.setTimestamp(localDateTime.format(formatter));
            // Set market-cap, volume & supply
            binding.setMarketCap(formatBigDouble(coin.marketCapUsd));
            binding.setVolume24Hr(formatBigDouble(coin.volumeUsd24Hr));
            binding.setSupply(formatBigDouble(coin.supply));
        };

        final Observer<List<CoinPriceHistory>> priceHistoryObserver = histories -> {

            if (histories.size() > 0) {
                ArrayList<Entry> entries = new ArrayList<>();

                for (int i = 0; i < histories.size(); i++) {
                    entries.add(new Entry(histories.get(i).time, histories.get(i).priceUsd.floatValue()));
                }
                chartUtil.setData(entries);

                if (finalInterval == CoinPriceHistory.Interval.D1) {
                    binding.setDayLow(formatFloat(binding.priceGraph.getYChartMin()));
                    binding.setDayHigh(formatFloat(binding.priceGraph.getYChartMax()));
                }

                visibilityShowGraph();

                Log.d(TAG, "setPriceChart.onSuccess: coin price history was gathered successfully");
            }
            else {
                visibilityGraphError(false, getResources().getString(R.string.error_no_data));

                Log.d(TAG, "setPriceChart.onSuccess: EMPTY coin price history was gathered successfully");
            }
        };

        vm.getCurrentCoin().observe(requireActivity(), coinObserver);
        vm.getCurrentCoinPriceHistory().observe(requireActivity(), priceHistoryObserver);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initChartUtil();
        bind();
        binding.intervalGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            switch (group.getCheckedChipId()) {
                case (R.id.chip_24h):
                    onChipClick(DateAxisFormatter.getShortTimeFormatter(), CoinPriceHistory.Interval.D1);
                    break;
                case (R.id.chip_7d):
                    onChipClick(DateAxisFormatter.getLongTimeFormatter(), CoinPriceHistory.Interval.D7);
                    break;
                case (R.id.chip_14d):
                    onChipClick(DateAxisFormatter.getLongTimeFormatter(), CoinPriceHistory.Interval.D14);
                    break;
                case (R.id.chip_1m):
                    onChipClick(DateAxisFormatter.getLongTimeFormatter(), CoinPriceHistory.Interval.M1);
                    break;
                case (R.id.chip_2m):
                    onChipClick(DateAxisFormatter.getLongTimeFormatter(), CoinPriceHistory.Interval.M2);
                    break;
                case (R.id.chip_6m):
                    onChipClick(DateAxisFormatter.getLongTimeFormatter(), CoinPriceHistory.Interval.M6);
                    break;
                case (R.id.chip_1y):
                    onChipClick(DateAxisFormatter.getLongTimeFormatter(), CoinPriceHistory.Interval.Y1);
                    break;
            }
            fetchCoinHistory();
        });

        binding.coinDetailsRefresh.setOnRefreshListener(this::bind);
    }

    private void bind() {
        fetchCoin();
        chartUtil.drawLineGraph();

        chartUtil.setValueFormatter(DateAxisFormatter.getShortTimeFormatter());
        fetchCoinHistory();
    }

    private void fetchCoin() {
        vm.fetchCoin(coinId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coin -> {
                            vm.getCurrentCoin().setValue(coin);
                            Log.d(TAG, String.format("getCoin.onSuccess: %s was fetched successfully", coinId));
                        },
                        e -> Log.e(TAG, String.format("getCoin.onError: %s couldn't be fetched", coinId), e),
                        compositeDisposable
                );
    }

    public void fetchCoinHistory() {
        EspressoIdlingResource.increment();
        visibilityHideGraph();

        vm.fetchPriceHistory(coinId, finalInterval)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        histories -> {
                            vm.getCurrentCoinPriceHistory().setValue(histories);
                            EspressoIdlingResource.decrement();
                        },

                        e -> {
                            Log.e(TAG, String.format("setPriceChart.onError: couldn't get price history of %s, with interval %s", coinId, finalInterval.param), e);
                            visibilityGraphError(e instanceof UnknownHostException, e.getMessage());

                            EspressoIdlingResource.decrement();
                        },
                        compositeDisposable
                );
    }

    private void initChartUtil() {
        chartUtil = new LineChartUtil(
                binding.priceGraph,
                UiUtils.getColor(requireContext(), com.google.android.material.R.attr.colorPrimary),
                UiUtils.getColor(requireContext(), com.google.android.material.R.attr.colorOnSurface)
        );
    }

    private void onChipClick(ValueFormatter formatter, CoinPriceHistory.Interval interval) {
        chartUtil.setValueFormatter(formatter);
        finalInterval = interval;
    }

    private void visibilityHideGraph() {
        binding.iconGraphWifiOff.setVisibility(View.GONE);
        binding.iconGraphError.setVisibility(View.GONE);
        binding.graphErrorText.setVisibility(View.GONE);

        binding.priceGraph.setVisibility(View.INVISIBLE);

        binding.pbGraph.setVisibility(View.VISIBLE);
    }

    private void visibilityShowGraph() {
        binding.priceGraph.setVisibility(View.VISIBLE);
        binding.pbGraph.setVisibility(View.GONE);

        if (binding.coinDetailsRefresh.isRefreshing()) {
            binding.coinDetailsRefresh.setRefreshing(false);
        }

        binding.coinDetailsContainer.setVisibility(View.VISIBLE);
//        binding.loadingLayout.setVisibility(View.GONE);
    }

    private void visibilityGraphError(boolean wifiOff, String error) {
        visibilityShowGraph();

        binding.pbGraph.setVisibility(View.GONE);
        binding.graphErrorText.setVisibility(View.VISIBLE);
        binding.priceGraph.setVisibility(View.INVISIBLE);
        binding.setGraphError(error);
        if (wifiOff) {
            binding.iconGraphWifiOff.setVisibility(View.VISIBLE);
            binding.setGraphError(getResources().getString(R.string.error_no_internet));
        }
        else {
            binding.iconGraphError.setVisibility(View.VISIBLE);
        }

        if (binding.getDayHigh() == null || binding.getDayLow() == null) {
            binding.tvDayHighVal.setText("-");
            binding.tvDayLowVal.setText("-");
        }
    }
}
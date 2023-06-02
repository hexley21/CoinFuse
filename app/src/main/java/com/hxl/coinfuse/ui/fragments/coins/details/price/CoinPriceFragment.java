package com.hxl.coinfuse.ui.fragments.coins.details.price;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.coinfuse.util.NumberFormatUtil.formatBigDouble;
import static com.hxl.coinfuse.util.NumberFormatUtil.formatDoubleDetailed;
import static com.hxl.coinfuse.util.NumberFormatUtil.formatFloat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentCoinPriceChartBinding;
import com.hxl.coinfuse.ui.fragments.coins.details.price.graph.DateAxisFormatter;
import com.hxl.coinfuse.ui.fragments.coins.details.price.graph.LineChartUtil;
import com.hxl.coinfuse.util.EspressoIdlingResource;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.presentation.livedata.DataState;
import com.hxl.presentation.viewmodels.CoinPriceViewModel;

import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CoinPriceFragment extends BaseFragment<FragmentCoinPriceChartBinding, CoinPriceViewModel> {
    @Override
    protected FragmentCoinPriceChartBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCoinPriceChartBinding.inflate(inflater, container, false);
    }

    @Override
    protected Class<CoinPriceViewModel> setViewModelClass() {
        return CoinPriceViewModel.class;
    }

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

        vm.getCurrentCoin().observe(requireActivity(), coin -> {
            if (coin.getState() == DataState.SUCCESS) {
                binding.setSymbol(coin.getData().symbol);
                // Set Price and Currency
                binding.setPrice(formatDoubleDetailed(coin.getData().priceUsd));
                binding.setCurrency("$");
                // Set fetch timestamp
                Instant instant = Instant.ofEpochMilli(coin.getData().timestamp);
                LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd MMM yyyy", Locale.getDefault());
                binding.setTimestamp(localDateTime.format(formatter));

                // Set color to change in percent and check for null
                setChangePercent24Hr(coin.getData().changePercent24Hr);

                // Check values for null
                isMarketCapNull(coin.getData().marketCapUsd);
                isVol24Null(coin.getData().volumeUsd24Hr);
                isSupplyNull(coin.getData().supply);
            }
            else if (coin.getState() == DataState.ERROR) {
                showSnackBar(coin.getError().getMessage());
            }
            hidePageLoading();
        });

        vm.getCurrentCoinPriceHistory().observe(requireActivity(), histories -> {
            if (histories.getState() == DataState.LOADING) {
                showGraphLoading();
            }
            else if (histories.getState() == DataState.SUCCESS) {
                if (histories.getData().isEmpty()) {
                    showGraphError(new IllegalStateException(UiUtils.getString(requireContext(), R.string.error_no_data)));
                    return;
                }

                ArrayList<Entry> entries = new ArrayList<>();

                histories.getData().forEach(h -> entries.add(new Entry(h.time, h.priceUsd.floatValue())));

                chartUtil.setData(entries);

                final float dayLow = binding.priceGraph.getYChartMin();
                final float dayHigh = binding.priceGraph.getYChartMax();

                if (finalInterval == CoinPriceHistory.Interval.D1) {
                    binding.containerDayVals.setVisibility(View.VISIBLE);

                    if (dayLow < 1.0f)
                        binding.setDayLow(formatDoubleDetailed((double) dayLow));
                    else
                        binding.setDayLow(formatFloat(dayLow));

                    if (dayHigh < 1.0f)
                        binding.setDayHigh(formatDoubleDetailed((double) dayHigh));
                    else
                        binding.setDayHigh(formatFloat(dayHigh));
                }

                hideGraphLoading();
            }
            else if (histories.getState() == DataState.ERROR){
                showGraphError(histories.getError());
            }
        });
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
            vm.fetchPriceHistory(coinId, finalInterval);
        });

        binding.srlCoinPrice.setOnRefreshListener(this::bind);

        binding.iconGraphWifiOff.setOnClickListener(v -> {
            vm.fetchPriceHistory(coinId, finalInterval);
            binding.srlCoinPrice.setRefreshing(true);
        });
        binding.iconGraphError.setOnClickListener(v -> vm.fetchPriceHistory(coinId, finalInterval));
        binding.graphErrorText.setOnClickListener(v -> vm.fetchPriceHistory(coinId, finalInterval));
    }

    private void bind() {
        EspressoIdlingResource.increment();
        vm.fetchCoin(coinId);
        chartUtil.drawLineGraph();

        chartUtil.setValueFormatter(DateAxisFormatter.getShortTimeFormatter());
        vm.fetchPriceHistory(coinId, finalInterval);
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

    // region value management
    private void setChangePercent24Hr(Float changePercent24Hr) {
        if (changePercent24Hr == null) {
            binding.tvChange.setText("");
            return;
        }
        if (changePercent24Hr >= 0) {
            binding.setChSmbl(getResources().getString(R.string.arrow_up));
            binding.tvChange.setTextColor(UiUtils.getColor(requireContext(), R.attr.growth));
        } else {
            binding.setChSmbl(getResources().getString(R.string.arrow_down));
            binding.tvChange.setTextColor(UiUtils.getColor(requireContext(), com.google.android.material.R.attr.colorError));
        }
        binding.setChange(formatFloat(Math.abs(changePercent24Hr)));
    }

    private void isMarketCapNull(Double marketCapUsd) {
        if (marketCapUsd == null) {
            binding.containerMarketCap.setVisibility(View.GONE);
            return;
        }
        binding.setMarketCap(formatBigDouble(marketCapUsd));
    }

    private void isVol24Null(Double volumeUsd24Hr) {
        if (volumeUsd24Hr == null) {
            binding.containerVol24.setVisibility(View.GONE);
            return;
        }
        binding.setVolume24Hr(formatBigDouble(volumeUsd24Hr));
    }

    private void isSupplyNull(Double supply) {
        if (supply == null) {
            binding.containerSupply.setVisibility(View.GONE);
            return;
        }
        binding.setSupply(formatBigDouble(supply));
    }
    // endregion

    // region visibility management
    private void hidePageLoading() {
        EspressoIdlingResource.decrement();
        binding.srlCoinPrice.setRefreshing(false);
        binding.shimmerCoinPrices.setVisibility(View.GONE);
        binding.srlCoinPrice.setVisibility(View.VISIBLE);
    }

    private void showGraphLoading() {
        EspressoIdlingResource.increment();
        binding.iconGraphWifiOff.setVisibility(View.GONE);
        binding.iconGraphError.setVisibility(View.GONE);
        binding.graphErrorText.setVisibility(View.GONE);
        binding.priceGraph.setVisibility(View.INVISIBLE);

        binding.pbGraph.setVisibility(View.VISIBLE);

        hideGraphError();
    }

    private void hideGraphLoading() {
        EspressoIdlingResource.decrement();
        binding.priceGraph.setVisibility(View.VISIBLE);
        binding.pbGraph.setVisibility(View.GONE);
        binding.srlCoinPrice.setRefreshing(false);
    }

    private void showGraphError(Throwable e) {
        EspressoIdlingResource.decrement();
        binding.priceGraph.setVisibility(View.INVISIBLE);
        binding.pbGraph.setVisibility(View.GONE);
        binding.srlCoinPrice.setRefreshing(false);

        binding.graphErrorText.setVisibility(View.VISIBLE);

        if (e instanceof UnknownHostException) {
            binding.iconGraphWifiOff.setVisibility(View.VISIBLE);
            binding.setGraphError(UiUtils.getString(requireContext(), R.string.error_no_internet));
            return;
        }
        binding.iconGraphError.setVisibility(View.VISIBLE);
        binding.setGraphError(e.getMessage());


    }

    private void hideGraphError() {
        EspressoIdlingResource.decrement();
        binding.iconGraphError.setVisibility(View.GONE);
        binding.iconGraphWifiOff.setVisibility(View.GONE);
        binding.graphErrorText.setVisibility(View.GONE);
    }
    // endregion
}
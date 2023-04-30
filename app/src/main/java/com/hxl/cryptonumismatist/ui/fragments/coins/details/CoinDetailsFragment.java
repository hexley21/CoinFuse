package com.hxl.cryptonumismatist.ui.fragments.coins.details;

import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.cryptonumismatist.util.NumberFormatUtil.formatDoubleDetailed;
import static com.hxl.cryptonumismatist.util.NumberFormatUtil.formatFloat;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.RequestManager;
import com.github.mikephil.charting.data.Entry;
import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.base.BaseFragment;
import com.hxl.cryptonumismatist.databinding.FragmentCoinDetailsBinding;
import com.hxl.cryptonumismatist.ui.fragments.coins.details.graph.DateAxisFormatter;
import com.hxl.cryptonumismatist.ui.fragments.coins.details.graph.LineChartUtil;
import com.hxl.cryptonumismatist.util.EspressoIdlingResource;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.presentation.viewmodels.CoinDetailsViewModel;

import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

@AndroidEntryPoint
public class CoinDetailsFragment extends BaseFragment<FragmentCoinDetailsBinding, CoinDetailsViewModel> {

    @Override
    protected FragmentCoinDetailsBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCoinDetailsBinding.inflate(inflater, container, false);
    }

    @Override
    protected CoinDetailsViewModel setViewModel() {
        return new ViewModelProvider(this).get(CoinDetailsViewModel.class);
    }
    private static final String TAG = "CoinDetailsFragment";
    String coinId;
    CoinPriceHistory.Interval chosenInterval = CoinPriceHistory.Interval.D1;

    @Inject
    RequestManager glide;
    LineChartUtil chartUtil;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        if (getArguments() != null) {
            coinId = getArguments().getString(coinArgKey);
            initChartUtil();
            bind();
        }
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.intervalGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            switch (group.getCheckedChipId()) {
                case (R.id.chip_24h):
                    chartUtil.setValueFormatter(DateAxisFormatter.shortTime);
                    chosenInterval = CoinPriceHistory.Interval.D1;
                    break;
                case (R.id.chip_7d):
                    chartUtil.setValueFormatter(DateAxisFormatter.longTime);
                    chosenInterval = CoinPriceHistory.Interval.D7;
                    break;
                case (R.id.chip_14d):
                    chartUtil.setValueFormatter(DateAxisFormatter.longTime);
                    chosenInterval = CoinPriceHistory.Interval.D14;
                    break;
                case (R.id.chip_1m):
                    chartUtil.setValueFormatter(DateAxisFormatter.longTime);
                    chosenInterval = CoinPriceHistory.Interval.M1;
                    break;
                case (R.id.chip_2m):
                    chartUtil.setValueFormatter(DateAxisFormatter.longTime);
                    chosenInterval = CoinPriceHistory.Interval.M2;
                    break;
                case (R.id.chip_6m):
                    chartUtil.setValueFormatter(DateAxisFormatter.longTime);
                    chosenInterval = CoinPriceHistory.Interval.M6;
                    break;
                case (R.id.chip_1y):
                    chartUtil.setValueFormatter(DateAxisFormatter.longTime);
                    chosenInterval = CoinPriceHistory.Interval.Y1;
                    break;
            }
            setPriceChart();
        });

        binding.coinDetailsRefresh.setOnRefreshListener(this::bind);
    }

    private void bind() {
        getCoinData();
        isCoinBookmarked();

        chartUtil.drawLineGraph();

        chartUtil.setValueFormatter(DateAxisFormatter.shortTime);
        setPriceChart();
    }

    public void setBookmarkListener() {
        binding.cbBookmark.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bookmarkCoin();
            }
            else {
                unBookmarkCoin();
            }
        });
    }

    private void getCoinData() {
        vm.getCoin(coinId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coin -> {
                            // Load logo
                            glide.load(coin.img).into(binding.imgDetailCoin);
                            // Set Name and Symbol
                            binding.setName(coin.name);
                            binding.setSymbol(coin.symbol);
                            // Set Price and Currency
                            binding.setPrice(formatDoubleDetailed(coin.priceUsd));
                            binding.setCurrency("$");
                            // Set change in %, color and check for null
                            if (coin.changePercent24Hr != null) {
                                if (coin.changePercent24Hr >= 0 ) {
                                    binding.setChSmbl(getResources().getString(R.string.arrow_up));
                                    binding.tvChange.setTextColor(getColor(R.attr.growth));
                                }
                                else {
                                    binding.setChSmbl(getResources().getString(R.string.arrow_down));
                                    binding.tvChange.setTextColor(getColor(com.google.android.material.R.attr.colorError));
                                }
                                binding.setChange(formatFloat(Math.abs(coin.changePercent24Hr)));
                            }
                            else {
                                binding.tvChange.setText("");
                            }
                            // Set fetch timestamp
                            Instant instant = Instant.ofEpochMilli(coin.timestamp);
                            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd MMM yyyy", Locale.getDefault());
                            binding.setTimestamp(localDateTime.format(formatter));
                            // Set market-cap, volume & supply
                            binding.setMarketCap(formatDoubleDetailed(coin.marketCapUsd));
                            binding.setVolume24Hr(formatDoubleDetailed(coin.volumeUsd24Hr));
                            binding.setSupply(formatDoubleDetailed(coin.supply));

                            Log.d(TAG, String.format("getCoin.onSuccess: %s was fetched successfully", coinId));
                        },
                        e -> Log.e(TAG, String.format("getCoin.onError: %s couldn't be fetched", coinId), e),
                        compositeDisposable
                );
    }

    private void isCoinBookmarked() {
        EspressoIdlingResource.increment();

        vm.isCoinBookmarked(coinId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        aBoolean -> {
                            binding.cbBookmark.setChecked(aBoolean);
                            setBookmarkListener();
                            Log.d(TAG, "isCoinBookmarked.onSuccess: bookmark state was checked successfully");
                            EspressoIdlingResource.decrement();
                        },
                        e -> {
                            setBookmarkListener();
                            Log.e(TAG, String.format("isCoinBookmarked.onError: %s couldn't be checked", coinId), e);
                            EspressoIdlingResource.decrement();
                        },
                        compositeDisposable
                );
    }

    public void setPriceChart() {
        EspressoIdlingResource.increment();
        visibilityHideGraph();

        vm.getCoinHistory(coinId, chosenInterval)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        histories -> {

                            if (histories.size() > 0) {
                                ArrayList<Entry> entries = new ArrayList<>();

                                for (int i = 0; i < histories.size(); i++) {
                                    entries.add(new Entry(histories.get(i).time, histories.get(i).priceUsd.floatValue()));
                                }
                                chartUtil.setData(entries);

                                if (chosenInterval == CoinPriceHistory.Interval.D1) {
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
                            EspressoIdlingResource.decrement();
                            },

                        e -> {
                            Log.e(TAG, String.format("setPriceChart.onError: couldn't get price history of %s, with interval %s", coinId, chosenInterval.param), e);

                            visibilityGraphError(e instanceof UnknownHostException, e.getMessage());

                            EspressoIdlingResource.decrement();
                        },
                        compositeDisposable
                );
    }

    private void bookmarkCoin() {
        EspressoIdlingResource.increment();
        vm.bookmarkCoin(coinId).subscribe(
                () -> {
                    Log.d(TAG, String.format("bookmarkCoin.onComplete: %s was bookmarked", coinId));
                    EspressoIdlingResource.decrement();
                },
                e -> {
                    Log.e(TAG, String.format("bookmarkCoin.onComplete: %s couldn't be bookmarked", coinId), e);
                    EspressoIdlingResource.decrement();
                },
                compositeDisposable
        );
    }
    private void unBookmarkCoin() {
        EspressoIdlingResource.increment();

        vm.unBookmarkCoin(coinId).subscribe(
                () -> {
                    Log.d(TAG, String.format("unBookmarkCoin.onComplete: %s was un-bookmarked", coinId));
                    EspressoIdlingResource.decrement();
                },
                e -> {
                    Log.e(TAG, String.format("unBookmarkCoin.onComplete: %s couldn't be un-bookmarked", coinId), e);
                    EspressoIdlingResource.decrement();
                },
                compositeDisposable
        );
    }
    private void visibilityHideGraph() {
        /*
        graph wifi-off icon - Gone
        graph error icon - Gone
        graph error text - Gone
        price graph - Invisible
        progress bar - Visible
         */
        binding.iconGraphWifiOff.setVisibility(View.GONE);
        binding.iconGraphError.setVisibility(View.GONE);
        binding.graphErrorText.setVisibility(View.GONE);

        binding.priceGraph.setVisibility(View.INVISIBLE);

        binding.pbGraph.setVisibility(View.VISIBLE);
    }

    private void visibilityShowGraph() {
        /*
        progress bar - Gone
        price graph - Visible
        loading layout - Gone
        coin details container - Visible
         */
        binding.priceGraph.setVisibility(View.VISIBLE);
        binding.pbGraph.setVisibility(View.GONE);

        if (binding.coinDetailsRefresh.isRefreshing()) {
            binding.coinDetailsRefresh.setRefreshing(false);
        }

        binding.coinDetailsContainer.setVisibility(View.VISIBLE);
        binding.loadingLayout.setVisibility(View.GONE);
    }

    private void visibilityGraphError(boolean wifiOff, String error) {
        /*
        visibilityShowGraph()

        progress bar - Gone
        error text - Visible
        price graph - Invisible

        icon graph wifi - Visible ||
        icon graph error - Visible
         */

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
    private void initChartUtil() {
        chartUtil = new LineChartUtil(
                binding.priceGraph,
                getColor(com.google.android.material.R.attr.colorPrimary),
                getColor(com.google.android.material.R.attr.colorOnSurface)
        );
    }

    private int getColor(int id) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = requireContext().getTheme();
        theme.resolveAttribute(id, typedValue, true);
        return typedValue.data;
    }

}

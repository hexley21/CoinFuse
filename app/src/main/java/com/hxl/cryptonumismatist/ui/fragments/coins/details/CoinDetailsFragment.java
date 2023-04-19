package com.hxl.cryptonumismatist.ui.fragments.coins.details;

import static com.hxl.cryptonumismatist.util.NumberFormatUtil.formatDoubleDetailed;
import static com.hxl.cryptonumismatist.util.NumberFormatUtil.formatFloat;

import android.annotation.SuppressLint;
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
import com.hxl.domain.model.History;
import com.hxl.presentation.viewmodels.CoinDetailsViewModel;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

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
    private static final String coinArg = "coin";
    String coinId;

    @Inject
    RequestManager glide;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    LineChartUtil chartUtil;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        if (getArguments() != null) {
            coinId = getArguments().getString(coinArg);
            initChartUtil();
            bind();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.intervalGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            switch (group.getCheckedChipId()) {
                case R.id.chip_24h:
                    chartUtil.setValueFormatter(DateAxisFormatter.shortTime);
                    setPriceChart(History.Interval.D1);
                    break;
                case R.id.chip_7d:
                    chartUtil.setValueFormatter(DateAxisFormatter.longTime);
                    setPriceChart(History.Interval.D7);
                    break;
                case R.id.chip_14d:
                    chartUtil.setValueFormatter(DateAxisFormatter.longTime);
                    setPriceChart(History.Interval.D14);
                    break;
                case R.id.chip_1m:
                    chartUtil.setValueFormatter(DateAxisFormatter.longTime);
                    setPriceChart(History.Interval.M1);
                    break;
                case R.id.chip_2m:
                    chartUtil.setValueFormatter(DateAxisFormatter.longTime);
                    setPriceChart(History.Interval.M2);
                    break;
                case R.id.chip_6m:
                    chartUtil.setValueFormatter(DateAxisFormatter.longTime);
                    setPriceChart(History.Interval.M6);
                    break;
                case R.id.chip_1y:
                    chartUtil.setValueFormatter(DateAxisFormatter.longTime);
                    setPriceChart(History.Interval.Y1);
                    break;
            }
        });
    }

    private void bind() {
        compositeDisposable.add(getCoinData());
        compositeDisposable.add(isCoinBookmarked());

        chartUtil.drawLineGraph();
        // the last and the longest call, where clear() is called
        chartUtil.setValueFormatter(DateAxisFormatter.shortTime);
        compositeDisposable.add(setPriceChart(History.Interval.D1));
    }

    public void setBookmarkListener() {
        binding.cbBookmark.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                compositeDisposable.add(bookmarkCoin());
            }
            else {
                compositeDisposable.add(unBookmarkCoin());
            }
        });
    }

    private Disposable getCoinData() {
        EspressoIdlingResource.increment();
        return vm.getCoin(coinId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coin -> {
                            glide.load(coin.img).into(binding.imgDetailCoin);
                            binding.setName(coin.name);
                            binding.setSymbol(coin.symbol);
                            binding.setPrice(formatDoubleDetailed(coin.priceUsd));
                            binding.setChange(formatFloat(Math.abs(coin.changePercent24Hr)));
                            binding.setCurrency("$");
                            if (coin.changePercent24Hr >= 0 ) {
                                binding.setChSmbl(getResources().getString(R.string.arrow_up));
                                binding.tvChange.setTextColor(getColor(R.attr.growth));
                            }
                            else {
                                binding.setChSmbl(getResources().getString(R.string.arrow_down));
                            }

                            Instant instant = Instant.ofEpochMilli(coin.timestamp);
                            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd MMM yyyy", Locale.getDefault());
                            binding.setTimestamp(localDateTime.format(formatter));

                            binding.setMarketCap(formatDoubleDetailed(coin.marketCapUsd));
                            binding.setVolume24Hr(formatDoubleDetailed(coin.volumeUsd24Hr));
                            binding.setSupply(formatDoubleDetailed(coin.supply));
                            binding.setDayHigh(String.valueOf(0));
                            binding.setDayLow(String.valueOf(0));
                            EspressoIdlingResource.decrement();
                        },
                        e -> {
                            Log.e(TAG, String.format("getCoin.onError: %s couldn't be fetched", coinId), e);
                            EspressoIdlingResource.decrement();
                        }
                );
    }

    private Disposable isCoinBookmarked() {
        EspressoIdlingResource.increment();

        return vm.isCoinBookmarked(coinId)
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
                        }
                );
    }

    public Disposable setPriceChart(History.Interval interval) {
        EspressoIdlingResource.increment();

        return vm.getCoinHistory(coinId, interval)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        histories -> {
                            ArrayList<Entry> entries = new ArrayList<>();
                            for (int i = 0; i < histories.size(); i++) {
                                entries.add(new Entry(histories.get(i).time, histories.get(i).priceUsd.floatValue()));
                            }
                            chartUtil.setData(entries);
                            if (interval == History.Interval.D1) {
                                binding.setDayLow(formatFloat(binding.lineChart.getYChartMin()));
                                binding.setDayHigh(formatFloat(binding.lineChart.getYChartMax()));
                            }
                            Log.d(TAG, "setPriceChart.onSuccess: coin price history was gathered successfully");
                            EspressoIdlingResource.decrement();
                            compositeDisposable.clear();
                            },
                        e -> {
                            Log.e(TAG, String.format("setPriceChart.onError: couldn't get price history of %s, with interval %s", coinId, interval.param), e);
                            EspressoIdlingResource.decrement();
                            compositeDisposable.clear();
                        });
    }

    private Disposable bookmarkCoin() {
        EspressoIdlingResource.increment();
        return vm.bookmarkCoin(coinId).subscribe(
                () -> {
                    Log.d(TAG, String.format("bookmarkCoin.onComplete: %s was bookmarked", coinId));
                    EspressoIdlingResource.decrement();
                    compositeDisposable.clear();
                },
                e -> {
                    Log.e(TAG, String.format("bookmarkCoin.onComplete: %s couldn't be bookmarked", coinId), e);
                    EspressoIdlingResource.decrement();
                    compositeDisposable.clear();
                }
        );
    }
    private Disposable unBookmarkCoin() {
        EspressoIdlingResource.increment();

        return vm.unBookmarkCoin(coinId).subscribe(
                () -> {
                    Log.d(TAG, String.format("unBookmarkCoin.onComplete: %s was un-bookmarked", coinId));
                    EspressoIdlingResource.decrement();
                    compositeDisposable.clear();
                },
                e -> {
                    Log.e(TAG, String.format("unBookmarkCoin.onComplete: %s couldn't be un-bookmarked", coinId), e);
                    EspressoIdlingResource.decrement();
                    compositeDisposable.clear();
                }
        );
    }
    private void initChartUtil() {
        chartUtil = new LineChartUtil(
                binding.lineChart,
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
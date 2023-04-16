package com.hxl.cryptonumismatist.ui.fragments.coins.details;

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
import com.hxl.cryptonumismatist.util.EspressoIdlingResource;
import com.hxl.domain.model.Coin;
import com.hxl.domain.model.History;
import com.hxl.presentation.viewmodels.CoinDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
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

    LineChartUtil chartUtil;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        if (getArguments() != null) {
            coinId = getArguments().getString(coinArg);
            initChartUtil();
            bind();
        }
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.cbBookmark.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                vm.bookmarkCoin(coinId)
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) { }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, String.format("bookmarkCoin.onComplete: %s was bookmarked", coinId));
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e(TAG, String.format("bookmarkCoin.onComplete: %s couldn't be bookmarked", coinId), e);
                            }
                        });
            }
            else {
                vm.unBookmarkCoin(coinId)
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) { }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, String.format("unBookmarkCoin.onComplete: %s was un-bookmarked", coinId));
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e(TAG, String.format("unBookmarkCoin.onComplete: %s couldn't be un-bookmarked", coinId), e);
                            }
                        });
            }
        });
    }

    private void bind() {
        vm.getCoin(coinId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Coin>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        EspressoIdlingResource.increment();
                    }

                    @Override
                    public void onSuccess(@NonNull Coin coin) {
                        glide.load(coin.img).into(binding.imgDetailCoin);
                        binding.setName(coin.name);
                        binding.setSymbol(coin.symbol);
                        binding.setPrice(formatDoubleDetailed(coin.priceUsd));
                        binding.setChange(formatFloat(Math.abs(coin.changePercent24Hr)));
                        binding.setCurrency("$");
                        if (coin.changePercent24Hr >= 0 ) {
                            binding.setChSmbl(getResources().getString(R.string.arrow_up));
                            TypedValue typedValue = new TypedValue();
                            Resources.Theme theme = requireContext().getTheme();
                            theme.resolveAttribute(R.attr.growth, typedValue, true);
                            binding.tvChange.setTextColor(typedValue.data);
                        }
                        else {
                            binding.setChSmbl(getResources().getString(R.string.arrow_down));
                        }

                        binding.setMarketCap(formatDoubleDetailed(coin.marketCapUsd));
                        binding.setVolume24Hr(formatDoubleDetailed(coin.volumeUsd24Hr));
                        binding.setSupply(formatDoubleDetailed(coin.supply));
                        binding.setDayHigh(String.valueOf(0));
                        binding.setDayLow(String.valueOf(0));
                        EspressoIdlingResource.decrement();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, String.format("getCoin.onError: %s couldn't be fetched", coinId), e);
                        EspressoIdlingResource.decrement();
                    }
                });

        vm.isCoinBookmarked(coinId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {}

                    @Override
                    public void onSuccess(@NonNull Boolean aBoolean) {
                        binding.cbBookmark.setChecked(aBoolean);
                        Log.d(TAG, "isCoinBookmarked.onSuccess: bookmark state was checked successfully");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, String.format("isCoinBookmarked.onError: %s couldn't be checked", coinId), e);
                    }
                });
        chartUtil.drawLineGraph();

        setPriceChart(History.Interval.D1);
    }

    public void setPriceChart(History.Interval interval) {
        vm.getCoinHistory(coinId, interval)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<History>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) { }

                    @Override
                    public void onSuccess(@NonNull List<History> histories) {
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
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(
                                TAG,
                                String.format("setPriceChart.onError: couldn't get price history of %s, with interval %s", coinId, interval.param),
                                e
                        );
                    }
                });

    }

    private void initChartUtil() {
        TypedValue chartColor = new TypedValue();
        TypedValue textColor = new TypedValue();
        Resources.Theme theme = requireContext().getTheme();
        theme.resolveAttribute(com.google.android.material.R.attr.colorPrimary, chartColor, true);
        theme.resolveAttribute(com.google.android.material.R.attr.colorOnSurface, textColor, true);

        chartUtil = new LineChartUtil(binding.lineChart, chartColor.data, textColor.data);
    }
}
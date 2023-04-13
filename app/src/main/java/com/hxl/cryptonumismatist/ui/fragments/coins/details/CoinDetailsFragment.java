package com.hxl.cryptonumismatist.ui.fragments.coins.details;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.hxl.cryptonumismatist.util.NumberFormatUtil.*;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.RequestManager;
import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.base.BaseFragment;
import com.hxl.cryptonumismatist.databinding.FragmentCoinDetailsBinding;
import com.hxl.domain.model.Coin;
import com.hxl.presentation.viewmodels.CoinDetailsViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;

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
    Single<Coin> coin;

    @Inject
    RequestManager glide;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        if (getArguments() != null) {
            String coinId = getArguments().getString(coinArg);
            coin = vm.getCoin(coinId);
            bind();
        }
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.cbBookmark.setOnCheckedChangeListener((buttonView, isChecked) -> {
            
        });
    }

    private void bind() {
        coin.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Coin>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {}

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

                        vm.isCoinBookmarked(coin.id).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new SingleObserver<Boolean>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {}

                                    @Override
                                    public void onSuccess(@NonNull Boolean aBoolean) {
                                        binding.cbBookmark.setChecked(aBoolean);
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {}
                                });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }
}
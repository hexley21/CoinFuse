package com.hxl.coinfuse.ui.fragments.coins.details;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinImgArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinNameArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinSymbolArgKey;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.RequestManager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentCoinDetailsBinding;
import com.hxl.coinfuse.util.EspressoIdlingResource;
import com.hxl.coinfuse.util.GlideFactory;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.presentation.viewmodels.CoinDetailsViewModel;

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
    private String coinId;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        assert getArguments() != null;
        coinId = getArguments().getString(coinArgKey);

        binding.setName(getArguments().getString(coinNameArgKey));
        binding.setSymbol(getArguments().getString(coinSymbolArgKey));
        GlideFactory.createGlide(requireContext()).load(getArguments().getString(coinImgArgKey)).into(binding.imgDetailCoin);
        isCoinBookmarked();

        binding.coinDetailsPager.setAdapter(new CoinDetailsPagerAdapter(this, coinId));
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.detailsTopAppBar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
        new TabLayoutMediator(binding.coinDetailsTabs, binding.coinDetailsPager, true, true, (tab, position) -> {
            if (position == 0) {
                tab.setText(UiUtils.getString(requireContext(), R.string.coin_price_chart));
            }
            else tab.setText(UiUtils.getString(requireContext(), R.string.coin_exchanges));
        }).attach();

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

}

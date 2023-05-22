package com.hxl.coinfuse.ui.fragments.coins.details;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinImgArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinNameArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinSymbolArgKey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayoutMediator;
import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentCoinDetailsBinding;
import com.hxl.coinfuse.util.GlideFactory;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.presentation.livedata.DataState;
import com.hxl.presentation.viewmodels.CoinDetailsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CoinDetailsFragment extends BaseFragment<FragmentCoinDetailsBinding, CoinDetailsViewModel> {

    @Override
    protected FragmentCoinDetailsBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCoinDetailsBinding.inflate(inflater, container, false);
    }

    @Override
    protected Class<CoinDetailsViewModel> setViewModelClass() {
        return CoinDetailsViewModel.class;
    }

    private String coinId;
    private boolean hasClickListener = false;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        assert getArguments() != null;
        coinId = getArguments().getString(coinArgKey);

        binding.setName(getArguments().getString(coinNameArgKey));
        binding.setSymbol(getArguments().getString(coinSymbolArgKey));
        GlideFactory.createGlide(requireContext()).load(getArguments().getString(coinImgArgKey)).into(binding.imgDetailCoin);

        vm.getCurrentBookmarkState().observe(requireActivity(), bool -> {
            if (bool.getState() == DataState.SUCCESS) {
                binding.cbBookmark.setChecked(bool.getData());

                if (!hasClickListener) {
                    binding.cbBookmark.setOnCheckedChangeListener((v, isChecked) -> {
                        if (isChecked)
                            bookmarkCoin();
                        else
                            unBookmarkCoin();
                    });

                    hasClickListener = true;
                }
            }
        });

        binding.coinDetailsPager.setAdapter(new CoinDetailsPagerAdapter(this, coinId));
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchBookmarkState();

        binding.detailsTopAppBar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        new TabLayoutMediator(
                binding.coinDetailsTabs,
                binding.coinDetailsPager,
                true,
                true,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText(UiUtils.getString(requireContext(), R.string.coin_price_chart));
                    }
                    else tab.setText(UiUtils.getString(requireContext(), R.string.coin_exchanges));
                }).attach();
    }

    private void fetchBookmarkState() {
        vm.fetchBookmarkState(coinId);
    }

    private void bookmarkCoin() {
        vm.bookmarkCoin(coinId);
    }

    private void unBookmarkCoin() {
        vm.unBookmarkCoin(coinId);
    }

}

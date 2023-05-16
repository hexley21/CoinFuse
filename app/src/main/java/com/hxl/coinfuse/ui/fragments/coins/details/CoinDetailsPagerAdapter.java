package com.hxl.coinfuse.ui.fragments.coins.details;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinArgKey;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hxl.coinfuse.ui.fragments.coins.details.price.CoinPriceFragment;

public class CoinDetailsPagerAdapter extends FragmentStateAdapter {

    private final String coinId;

    public CoinDetailsPagerAdapter(@NonNull Fragment fragment, String coinId) {
        super(fragment);
        this.coinId = coinId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(coinArgKey, coinId);
        CoinPriceFragment fragment = new CoinPriceFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

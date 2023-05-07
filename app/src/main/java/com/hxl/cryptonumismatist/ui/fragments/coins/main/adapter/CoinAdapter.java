package com.hxl.cryptonumismatist.ui.fragments.coins.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;

import com.hxl.cryptonumismatist.base.BasePagingAdapter;
import com.hxl.cryptonumismatist.databinding.ItemCoinBinding;
import com.hxl.cryptonumismatist.util.CoinComparator;
import com.hxl.domain.model.Coin;

import java.util.Objects;

public class CoinAdapter extends BasePagingAdapter<Coin ,CoinViewHolder> {

    private final NavController navController;

    public CoinAdapter(NavController navController) {
        super(new CoinComparator());
        this.navController = navController;
    }

    @Override
    protected CoinViewHolder getViewHolder(ViewGroup parent, int viewType) {
        ItemCoinBinding binding = ItemCoinBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CoinViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        String coinId = Objects.requireNonNull(getItem(position)).id;
        String explorerId = Objects.requireNonNull(getItem(position)).explorer;

        CoinViewHolder.defaultOnBindViewHolder(holder, navController, coinId, explorerId);
    }
}

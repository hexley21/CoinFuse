package com.hxl.coinfuse.ui.fragments.coins.main.adapter;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.explorerArgKey;
import static com.hxl.coinfuse.util.NumberFormatUtil.formatDouble;
import static com.hxl.coinfuse.util.NumberFormatUtil.formatFloat;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.hxl.coinfuse.R;
import com.hxl.coinfuse.databinding.ItemCoinBinding;
import com.hxl.coinfuse.util.EspressoIdlingResource;
import com.hxl.coinfuse.util.GlideFactory;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.domain.model.Coin;

import java.util.function.Consumer;

public class CoinViewHolder extends RecyclerView.ViewHolder implements Consumer<Coin> {

    private static final String TAG = "CoinViewHolder";

    RequestManager glide;
    ItemCoinBinding binding;

    public CoinViewHolder(ItemCoinBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        glide = GlideFactory.createGlide(itemView.getContext());
    }

    @Override
    public void accept(Coin coin) {
        binding.setName(coin.name);
        binding.setSymbol(coin.symbol);
        bindPrice(coin.priceUsd);
        bindChange(coin.changePercent24Hr);
        binding.setRank(String.valueOf(coin.rank));
        glide.load(coin.img).into(binding.imgCoin);
    }

    private void bindPrice(Double value) {
        if (value == null) {
            binding.tvPrice.setVisibility(View.GONE);
        }
        else {
            binding.setPrice(formatDouble(value));
            binding.setCurrency("$");
        }
    }

    private void bindChange(Float value) {
        if (value == null) {
            binding.tvChange.setVisibility(View.GONE);
            binding.coinNumbers.setGravity(Gravity.CENTER);
            binding.coinNumbers.setPadding(0, 0, 0, 0);
            return;
        }

        binding.setChange(formatFloat(Math.abs(value)));
        if (value > 0) {
            binding.tvChange.setTextColor(UiUtils.getColor(itemView.getContext(), R.attr.growth));
            binding.setChSmbl(UiUtils.getString(itemView.getContext(), R.string.arrow_up));
        } else if (value < 0) {
            binding.tvChange.setTextColor(UiUtils.getColor(itemView.getContext(), com.google.android.material.R.attr.colorError));
            binding.setChSmbl(UiUtils.getString(itemView.getContext(), R.string.arrow_down));
        }
        else {
            binding.setChSmbl("~");
        }
    }

    public static void defaultOnBindViewHolder(CoinViewHolder holder, NavController navController, String coinId, String explorerId) {
        EspressoIdlingResource.increment();

        if (navController == null) {
            Log.e(TAG, "onBindViewHolder: ", new NullPointerException("NavController was null"));
            EspressoIdlingResource.decrement();
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString(coinArgKey, coinId);

        holder.itemView.setOnClickListener(v -> {
            assert navController.getCurrentDestination() != null;
            if (navController.getCurrentDestination().getId() == R.id.navigationFragment) {
                navController.navigate(R.id.navigation_to_coinDetails, bundle);
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            assert navController.getCurrentDestination() != null;
            if (navController.getCurrentDestination().getId() == R.id.navigationFragment) {
                bundle.putString(explorerArgKey, explorerId);
                navController.navigate(R.id.navigation_to_coinDialog, bundle);
            }
            return true;
        });

        EspressoIdlingResource.decrement();
    }
}
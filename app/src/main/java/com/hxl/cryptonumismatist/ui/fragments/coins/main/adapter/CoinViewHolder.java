package com.hxl.cryptonumismatist.ui.fragments.coins.main.adapter;

import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.explorerArgKey;
import static com.hxl.cryptonumismatist.util.NumberFormatUtil.formatDouble;
import static com.hxl.cryptonumismatist.util.NumberFormatUtil.formatFloat;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.databinding.ItemCoinBinding;
import com.hxl.cryptonumismatist.util.EspressoIdlingResource;
import com.hxl.cryptonumismatist.util.GlideFactory;
import com.hxl.cryptonumismatist.util.UiUtils;
import com.hxl.domain.model.Coin;

import java.util.function.Function;

public class CoinViewHolder extends RecyclerView.ViewHolder implements Function<Coin, Void> {

    RequestManager glide;
    ItemCoinBinding binding;

    public CoinViewHolder(ItemCoinBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        glide = GlideFactory.createGlide(itemView.getContext());
    }

    @Override
    public Void apply(Coin coin) {
        binding.setName(coin.name);
        binding.setSymbol(coin.symbol);
        bindPrice(coin.priceUsd);
        bindChange(coin.changePercent24Hr);
        binding.setRank(String.valueOf(coin.rank));
        glide.load(coin.img).into(binding.imgCoin);
        return null;
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

        Bundle bundle = new Bundle();
        bundle.putString(coinArgKey, coinId);

        holder.itemView.setOnClickListener(v ->
                navController.navigate(R.id.navigation_to_coinDetails, bundle));
        holder.itemView.setOnLongClickListener(v -> {
            bundle.putString(explorerArgKey, explorerId);
            navController.navigate(R.id.navigation_to_coinDialog, bundle);
            return true;
        });

        EspressoIdlingResource.decrement();
    }
}
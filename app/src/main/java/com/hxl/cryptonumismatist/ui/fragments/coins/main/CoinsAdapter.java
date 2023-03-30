package com.hxl.cryptonumismatist.ui.fragments.coins.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.hxl.cryptonumismatist.databinding.CoinItemBinding;
import com.hxl.cryptonumismatist.ui.fragments.BaseAdapter;
import com.hxl.cryptonumismatist.utils.Binder;
import com.hxl.domain.model.Coin;

import java.text.DecimalFormat;

import javax.inject.Inject;

public class CoinsAdapter extends BaseAdapter<Coin, CoinsAdapter.CoinViewHolder> {
    private final RequestManager glide;

    @Inject
    public CoinsAdapter(RequestManager glide) {
        this.glide = glide;
        DiffUtil.ItemCallback<Coin> diffCallBack = new DiffUtil.ItemCallback<Coin>() {
            @Override
            public boolean areItemsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return oldItem.id.equals(newItem.id);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return oldItem.equals(newItem);
            }
        };
        CoinsAdapter.super.differ = new AsyncListDiffer<>(this, diffCallBack);
    }

    @Override
    protected CoinViewHolder getViewHolder(ViewGroup parent, int viewType) {
        CoinItemBinding binding = CoinItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CoinViewHolder(binding);
    }

    protected class CoinViewHolder extends RecyclerView.ViewHolder implements Binder<Coin> {
        CoinItemBinding binding;
        public CoinViewHolder(CoinItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bind(Coin item) {
            binding.setName(item.name);
            binding.setSymbol(item.symbol);
            binding.setPrice(formatDouble(item.priceUsd, "$"));
            binding.setChange(formatFloat(item.changePercent24Hr, "%"));
            glide.load(item.img).into(binding.imgCoin);
        }

    }

    private static String formatDouble(Double num, String suffix) {
        if (num != null) {
            DecimalFormat df = new DecimalFormat("#");
            df.setMinimumFractionDigits(2);
            if (num > 1.0d) {
                df.setMaximumFractionDigits(2);
                return df.format(num) + suffix;
            }
            df.setMaximumFractionDigits(6);
            return df.format(num) + suffix;
        }
        return "-";
    }

    private static String formatFloat(Float num, String suffix) {
        if (num != null) {
            DecimalFormat df = new DecimalFormat("#.##");
                return df.format(num) + suffix;
        }
        return "-";
    }
}

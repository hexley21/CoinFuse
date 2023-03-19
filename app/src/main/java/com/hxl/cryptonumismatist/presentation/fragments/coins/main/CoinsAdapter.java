package com.hxl.cryptonumismatist.presentation.fragments.coins.main;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.hxl.cryptonumismatist.databinding.CoinItemBinding;
import com.hxl.cryptonumismatist.presentation.fragments.BaseAdapter;
import com.hxl.cryptonumismatist.utils.Binder;
import com.hxl.domain.model.Coin;

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

        @SuppressLint("DefaultLocale")
        @Override
        public void bind(Coin item) {
            binding.setName(item.name);
            binding.setSymbol(item.symbol);
            binding.setPrice(String.format("%,.2f$", item.priceUsd));
            binding.setChange(String.format("%.2f%%", item.changePercent24Hr));
            glide.load(item.img).into(binding.imgCoin);
        }

    }
}

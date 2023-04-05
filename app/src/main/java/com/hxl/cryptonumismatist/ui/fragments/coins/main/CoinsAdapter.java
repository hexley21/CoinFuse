package com.hxl.cryptonumismatist.ui.fragments.coins.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.hxl.cryptonumismatist.base.BaseAdapter;
import com.hxl.cryptonumismatist.base.Binder;
import com.hxl.cryptonumismatist.databinding.CoinItemBinding;
import com.hxl.domain.model.Coin;

import java.text.DecimalFormat;
import java.util.function.Function;

import javax.inject.Inject;

public class CoinsAdapter extends BaseAdapter<Coin, CoinsAdapter.CoinViewHolder> {
    private final RequestManager glide;
    protected Function<Bundle, Void> navigateToDetails;

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

    public void setNavigateToDetails(Function<Bundle, Void> navigateToDetails) {
        this.navigateToDetails = navigateToDetails;
    }

    @Override
    protected CoinViewHolder getViewHolder(ViewGroup parent, int viewType) {
        CoinItemBinding binding = CoinItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CoinViewHolder(binding);
    }

    public class CoinViewHolder extends RecyclerView.ViewHolder implements Binder<Coin> {
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

    @Override
    public void onBindViewHolder(@NonNull CoinsAdapter.CoinViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Bundle bundle = new Bundle();
        bundle.putString("coin", getList().get(position).id);

        holder.itemView.setOnClickListener( v -> navigateToDetails.apply(bundle));
    }

    private String formatDouble(Double num, String suffix) {
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

    private String formatFloat(Float num, String suffix) {
        if (num != null) {
            DecimalFormat df = new DecimalFormat("#.##");
                return df.format(num) + suffix;
        }
        return "-";
    }
}

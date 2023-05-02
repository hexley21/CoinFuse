package com.hxl.cryptonumismatist.ui.fragments.coins.main;

import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.explorerArgKey;
import static com.hxl.cryptonumismatist.util.NumberFormatUtil.formatDouble;
import static com.hxl.cryptonumismatist.util.NumberFormatUtil.formatFloat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.hxl.cryptonumismatist.base.BaseAdapter;
import com.hxl.cryptonumismatist.databinding.CoinItemBinding;
import com.hxl.domain.model.Coin;

import java.util.function.Function;

import javax.inject.Inject;

public class CoinsMenuAdapter extends BaseAdapter<Coin, CoinsMenuAdapter.CoinViewHolder> {
    private final RequestManager glide;
    protected Function<Bundle, Void> navigateToDetails;
    protected Function<Bundle, Void> onLongClick;

    @Inject
    public CoinsMenuAdapter(RequestManager glide) {
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
        CoinsMenuAdapter.super.differ = new AsyncListDiffer<>(this, diffCallBack);
    }

    public void setNavigateToDetails(Function<Bundle, Void> navigateToDetails) {
        this.navigateToDetails = navigateToDetails;
    }

    public void setOnLongClick(Function<Bundle, Void> onLongClick) {
        this.onLongClick = onLongClick;
    }

    @Override
    protected CoinViewHolder getViewHolder(ViewGroup parent, int viewType) {
        CoinItemBinding binding = CoinItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CoinViewHolder(binding);
    }

    public class CoinViewHolder extends RecyclerView.ViewHolder implements Function<Coin, Void> {
        CoinItemBinding binding;
        public CoinViewHolder(CoinItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public Void apply(Coin coin) {
            binding.setName(coin.name);
            binding.setSymbol(coin.symbol);
            binding.setPrice(formatDouble(coin.priceUsd));
            binding.setChange(formatFloat(coin.changePercent24Hr));
            binding.setRank(String.valueOf(coin.rank));
            glide.load(coin.img).into(binding.imgCoin);
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CoinsMenuAdapter.CoinViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Bundle bundle = new Bundle();
        bundle.putString(coinArgKey, getList().get(position).id);

        holder.itemView.setOnClickListener(v -> navigateToDetails.apply(bundle));
        holder.itemView.setOnLongClickListener(v -> {
            bundle.putString(explorerArgKey, getList().get(position).explorer);
            onLongClick.apply(bundle);
            return true;
        });
    }

}

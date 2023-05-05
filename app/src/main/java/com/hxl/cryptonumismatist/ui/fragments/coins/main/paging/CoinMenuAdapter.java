package com.hxl.cryptonumismatist.ui.fragments.coins.main.paging;

import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.explorerArgKey;
import static com.hxl.cryptonumismatist.util.NumberFormatUtil.formatDouble;
import static com.hxl.cryptonumismatist.util.NumberFormatUtil.formatFloat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.base.BasePagingAdapter;
import com.hxl.cryptonumismatist.databinding.CoinItemBinding;
import com.hxl.cryptonumismatist.util.EspressoIdlingResource;
import com.hxl.domain.model.Coin;

import java.util.Objects;
import java.util.function.Function;

import javax.inject.Inject;

public class CoinMenuAdapter extends BasePagingAdapter<Coin , CoinMenuAdapter.CoinViewHolder> {

    private final RequestManager glide;
    private NavController navController;

    @Inject
    public CoinMenuAdapter(RequestManager glide) {
        super(new CoinComparator());
        this.glide = glide;
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
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
    public void onBindViewHolder(@NonNull CoinViewHolder holder, int position) {
        EspressoIdlingResource.increment();
        super.onBindViewHolder(holder, position);

        Bundle bundle = new Bundle();
        bundle.putString(coinArgKey, Objects.requireNonNull(getItem(position)).id);

        if (navController != null) {
            holder.itemView.setOnClickListener(v ->
                    navController.navigate(R.id.navigationFragment_to_coinDetailsFragment, bundle));
            holder.itemView.setOnLongClickListener(v -> {
                bundle.putString(explorerArgKey, Objects.requireNonNull(getItem(position)).explorer);
                navController.navigate(R.id.action_navigationFragment_to_dialog_coin, bundle);
                return true;
            });
        }
        EspressoIdlingResource.decrement();
    }

    public static class CoinComparator extends DiffUtil.ItemCallback<Coin> {

        @Override
        public boolean areItemsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
            return oldItem.equals(newItem);
        }
    }
}

package com.hxl.cryptonumismatist.ui.fragments.coins.main;

import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.explorerArgKey;
import static com.hxl.cryptonumismatist.util.NumberFormatUtil.formatDouble;
import static com.hxl.cryptonumismatist.util.NumberFormatUtil.formatFloat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.base.BasePagingAdapter;
import com.hxl.cryptonumismatist.databinding.CoinItemBinding;
import com.hxl.cryptonumismatist.util.CoinComparator;
import com.hxl.cryptonumismatist.util.UiUtils;
import com.hxl.cryptonumismatist.util.EspressoIdlingResource;
import com.hxl.cryptonumismatist.util.GlideStandard;
import com.hxl.domain.model.Coin;

import java.util.Objects;
import java.util.function.Function;

public class CoinAdapter extends BasePagingAdapter<Coin , CoinAdapter.CoinViewHolder> {

    private final RequestManager glide;
    private final NavController navController;

    public CoinAdapter(Activity activity, int navContainerId) {
        super(new CoinComparator());
        this.glide = GlideStandard.getGlide(activity);
        navController = Navigation.findNavController(activity, navContainerId);
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
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder holder, int position) {
        EspressoIdlingResource.increment();
        super.onBindViewHolder(holder, position);

        Bundle bundle = new Bundle();
        bundle.putString(coinArgKey, Objects.requireNonNull(getItem(position)).id);

        if (navController != null) {
            holder.itemView.setOnClickListener(v ->
                    navController.navigate(R.id.coinDetailsFragment, bundle));
            holder.itemView.setOnLongClickListener(v -> {
                bundle.putString(explorerArgKey, Objects.requireNonNull(getItem(position)).explorer);
                navController.navigate(R.id.coinDialog, bundle);
                return true;
            });
        }
        EspressoIdlingResource.decrement();
    }
}

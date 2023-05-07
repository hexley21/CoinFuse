package com.hxl.cryptonumismatist.ui.fragments.coins.main.adapter;

import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.explorerArgKey;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.base.BaseAdapter;
import com.hxl.cryptonumismatist.databinding.ItemCoinSearchBinding;
import com.hxl.cryptonumismatist.util.CoinComparator;
import com.hxl.cryptonumismatist.util.EspressoIdlingResource;
import com.hxl.cryptonumismatist.util.GlideFactory;
import com.hxl.domain.model.Coin;

import java.util.function.Function;

public class CoinSearchAdapter extends BaseAdapter<Coin, CoinSearchAdapter.CoinSearchViewHolder> {
    private static final String TAG = "CoinSearchAdapter";
    private final Function<Bundle, Void> insertSearchFunction;
    private NavController navController;

    public CoinSearchAdapter(Function<Bundle, Void> insertSearchFunction) {
        super(new CoinComparator());
        this.insertSearchFunction = insertSearchFunction;
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    @Override
    protected CoinSearchViewHolder getViewHolder(ViewGroup parent, int viewType) {
        ItemCoinSearchBinding binding = ItemCoinSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CoinSearchViewHolder(binding);
    }

    public static class CoinSearchViewHolder extends RecyclerView.ViewHolder implements Function<Coin, Void> {
        ItemCoinSearchBinding binding;
        RequestManager glide;

        public CoinSearchViewHolder(ItemCoinSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.glide = GlideFactory.createGlide(itemView.getContext());
        }

        @Override
        public Void apply(Coin coin) {
            binding.setName(coin.name);
            binding.setSymbol(coin.symbol);
            binding.setRank(coin.rank);
            glide.load(coin.img).into(binding.imgCoin);
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CoinSearchViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        EspressoIdlingResource.increment();

        Bundle bundle = new Bundle();
        bundle.putString(coinArgKey, getList().get(position).id);

        if (navController == null) {
            Log.e(TAG, "onBindViewHolder: ", new NullPointerException("NavController was null"));
            EspressoIdlingResource.decrement();
            return;
        }

        holder.itemView.setOnClickListener(v -> {
            navController.navigate(R.id.navigation_to_coinDetails, bundle);
            insertSearchFunction.apply(bundle);
        });
        holder.itemView.setOnLongClickListener(v -> {
            bundle.putString(explorerArgKey, getList().get(position).explorer);
            navController.navigate(R.id.navigation_to_coinDialog, bundle);
            return false;
        });
    }
}
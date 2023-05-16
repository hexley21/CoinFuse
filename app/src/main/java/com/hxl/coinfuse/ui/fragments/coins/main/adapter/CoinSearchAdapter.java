package com.hxl.coinfuse.ui.fragments.coins.main.adapter;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinImgArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinNameArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinSymbolArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.explorerArgKey;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseAdapter;
import com.hxl.coinfuse.databinding.ItemCoinSearchBinding;
import com.hxl.coinfuse.util.comparator.CoinComparator;
import com.hxl.coinfuse.util.EspressoIdlingResource;
import com.hxl.coinfuse.util.GlideFactory;
import com.hxl.domain.model.Coin;

import java.util.function.Consumer;

public class CoinSearchAdapter extends BaseAdapter<Coin, CoinSearchAdapter.CoinSearchViewHolder> {
    private static final String TAG = "CoinSearchAdapter";
    private final Consumer<Bundle> insertSearchFunction;
    private NavController navController;

    public CoinSearchAdapter(Consumer<Bundle> insertSearchFunction) {
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

    public static class CoinSearchViewHolder extends RecyclerView.ViewHolder implements Consumer<Coin> {
        ItemCoinSearchBinding binding;
        RequestManager glide;

        public CoinSearchViewHolder(ItemCoinSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.glide = GlideFactory.createGlide(itemView.getContext());
        }

        @Override
        public void accept(Coin coin) {
            binding.setName(coin.name);
            binding.setSymbol(coin.symbol);
            binding.setRank(coin.rank);
            glide.load(coin.img).into(binding.imgCoin);
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
            bundle.putString(coinNameArgKey, getList().get(position).name);
            bundle.putString(coinSymbolArgKey, getList().get(position).symbol);
            bundle.putString(coinImgArgKey, getList().get(position).img);
            navController.navigate(R.id.navigation_to_coinDetails, bundle);
            insertSearchFunction.accept(bundle);
        });
        holder.itemView.setOnLongClickListener(v -> {
            bundle.putString(explorerArgKey, getList().get(position).explorer);
            navController.navigate(R.id.navigation_to_coinDialog, bundle);
            return false;
        });
    }
}
package com.hxl.coinfuse.ui.fragments.coins.main.adapter;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinImgArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinNameArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinSymbolArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.explorerArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.dialogCallbackArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.searchQueryArgKey;

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
import com.hxl.coinfuse.ui.dialogs.DialogCallback;
import com.hxl.coinfuse.util.GlideFactory;
import com.hxl.coinfuse.util.comparator.CoinComparator;
import com.hxl.domain.model.Coin;

import java.util.function.Consumer;

public class CoinSearchAdapter extends BaseAdapter<Coin, CoinSearchAdapter.CoinSearchViewHolder> {
    private static final String TAG = "CoinSearchAdapter";
    private final Consumer<Bundle> insertSearchCallback;
    private Consumer<Coin> onEndCallBack;
    private final Runnable fetchHistory;
    private NavController navController;

    public CoinSearchAdapter() {
        super(new CoinComparator());
        this.insertSearchCallback = null;
        fetchHistory = null;
    }

    public CoinSearchAdapter(Consumer<Bundle> insertSearchCallback) {
        super(new CoinComparator());
        this.insertSearchCallback = insertSearchCallback;
        fetchHistory = null;
    }

    public CoinSearchAdapter(Consumer<Bundle> insertSearchCallback, Runnable fetchHistory) {
        super(new CoinComparator());
        this.insertSearchCallback = insertSearchCallback;
        this.fetchHistory = fetchHistory;
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    public void setOnEndCallBack(Consumer<Coin> consumer) {
        this.onEndCallBack = consumer;
    }

    @Override
    protected CoinSearchViewHolder getViewHolder(ViewGroup parent, int viewType) {
        ItemCoinSearchBinding binding = ItemCoinSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CoinSearchViewHolder(binding);
    }

    public static class CoinSearchViewHolder extends RecyclerView.ViewHolder implements Consumer<Coin> {
        final ItemCoinSearchBinding binding;
        final RequestManager glide;

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
        Bundle bundle = new Bundle();
        bundle.putString(coinArgKey, getList().get(position).id);

        if (navController == null) {
            if (onEndCallBack != null) {
                holder.itemView.setOnClickListener(v -> onEndCallBack.accept(getList().get(position)));
            }
            return;
        }

        holder.itemView.setOnClickListener(v -> {
            if (insertSearchCallback != null) {
                assert navController.getCurrentDestination() != null;
                if (navController.getCurrentDestination().getId() == R.id.navigationFragment) {
                    bundle.putString(coinNameArgKey, getList().get(position).name);
                    bundle.putString(coinSymbolArgKey, getList().get(position).symbol);
                    bundle.putString(coinImgArgKey, getList().get(position).img);
                    navController.navigate(R.id.navigation_to_coinDetails, bundle);
                    insertSearchCallback.accept(bundle);
                }
            }
            else Log.d(TAG, "onBindViewHolder: not navigated to coin details, insertSearchCallback was null");
            if (onEndCallBack != null) onEndCallBack.accept(getList().get(position));
        });
        holder.itemView.setOnLongClickListener(v -> {
            assert navController.getCurrentDestination() != null;
            if (navController.getCurrentDestination().getId() == R.id.navigationFragment) {
                bundle.putString(explorerArgKey, getList().get(position).explorer);
                if (fetchHistory != null) {
                    bundle.putString(searchQueryArgKey, getList().get(position).id);
                    bundle.putParcelable(dialogCallbackArgKey, (DialogCallback) fetchHistory::run);
                }
                navController.navigate(R.id.navigation_to_coinDialog, bundle);
            }
            return false;
        });
    }

}
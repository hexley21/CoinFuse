package com.hxl.cryptonumismatist.ui.fragments.bookmarks;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.hxl.cryptonumismatist.base.BaseAdapter;
import com.hxl.cryptonumismatist.databinding.ItemCoinBinding;
import com.hxl.cryptonumismatist.ui.fragments.coins.main.adapter.CoinViewHolder;
import com.hxl.cryptonumismatist.util.CoinComparator;
import com.hxl.domain.model.Coin;

public class BookmarkAdapter extends BaseAdapter<Coin, CoinViewHolder> {
    private final NavController navController;

    public BookmarkAdapter(Activity activity, int navContainerId) {
        super(new CoinComparator());
        navController = Navigation.findNavController(activity, navContainerId);
    }

    @Override
    protected CoinViewHolder getViewHolder(ViewGroup parent, int viewType) {
        ItemCoinBinding binding = ItemCoinBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CoinViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        String coinId = getList().get(position).id;
        String explorerId = getList().get(position).explorer;

        CoinViewHolder.defaultOnBindViewHolder(holder, navController, coinId, explorerId);
    }

}

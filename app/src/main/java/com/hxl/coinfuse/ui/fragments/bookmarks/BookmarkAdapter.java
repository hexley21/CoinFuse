package com.hxl.coinfuse.ui.fragments.bookmarks;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;

import com.hxl.coinfuse.base.BaseAdapter;
import com.hxl.coinfuse.databinding.ItemCoinBinding;
import com.hxl.coinfuse.ui.fragments.coins.main.adapter.CoinViewHolder;
import com.hxl.coinfuse.util.comparator.CoinComparator;
import com.hxl.domain.model.Coin;

public class BookmarkAdapter extends BaseAdapter<Coin, CoinViewHolder> {
    private NavController navController = null;

    public BookmarkAdapter() {
        super(new CoinComparator());
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    public NavController getNavController() {
        return navController;
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
        String coinName = getList().get(position).name;
        String coinSymbol = getList().get(position).symbol;
        String coinImg = getList().get(position).img;
        String explorerId = getList().get(position).explorer;

        CoinViewHolder.defaultOnBindViewHolder(holder, navController, coinId, coinName, coinSymbol, coinImg, explorerId);
    }

}

package com.hxl.cryptonumismatist.ui.fragments.bookmarks;

import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.explorerArgKey;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.base.BaseAdapter;
import com.hxl.cryptonumismatist.databinding.CoinItemBinding;
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
        CoinItemBinding binding = CoinItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CoinViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Bundle bundle = new Bundle();
        bundle.putString(coinArgKey, getList().get(position).id);

        holder.itemView.setOnClickListener(v ->
                navController.navigate(R.id.navigation_to_coinDetails, bundle));
        holder.itemView.setOnLongClickListener(v -> {
            bundle.putString(explorerArgKey, getList().get(position).explorer);
            navController.navigate(R.id.navigation_to_coinDialog, bundle);
            return true;
        });

    }

}

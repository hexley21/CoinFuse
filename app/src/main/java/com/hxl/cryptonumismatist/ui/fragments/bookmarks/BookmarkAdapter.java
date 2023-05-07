package com.hxl.cryptonumismatist.ui.fragments.bookmarks;

import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.explorerArgKey;
import static com.hxl.cryptonumismatist.util.NumberFormatUtil.formatDouble;
import static com.hxl.cryptonumismatist.util.NumberFormatUtil.formatFloat;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.base.BaseAdapter;
import com.hxl.cryptonumismatist.databinding.CoinItemBinding;
import com.hxl.cryptonumismatist.util.CoinComparator;
import com.hxl.cryptonumismatist.util.GlideStandard;
import com.hxl.domain.model.Coin;

import java.util.function.Function;

public class BookmarkAdapter extends BaseAdapter<Coin, BookmarkAdapter.CoinViewHolder> {
    private final RequestManager glide;
    private final NavController navController;

    public BookmarkAdapter(Activity activity, int navContainerId) {
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
            binding.setPrice(formatDouble(coin.priceUsd));
            binding.setChange(formatFloat(coin.changePercent24Hr));
            binding.setRank(String.valueOf(coin.rank));
            glide.load(coin.img).into(binding.imgCoin);
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.CoinViewHolder holder, int position) {
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

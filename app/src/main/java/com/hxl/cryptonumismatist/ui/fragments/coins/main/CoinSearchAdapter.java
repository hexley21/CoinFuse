package com.hxl.cryptonumismatist.ui.fragments.coins.main;

import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.explorerArgKey;

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
import com.hxl.cryptonumismatist.databinding.SearchCoinItemBinding;
import com.hxl.cryptonumismatist.util.CoinComparator;
import com.hxl.cryptonumismatist.util.GlideStandard;
import com.hxl.domain.model.Coin;

import java.util.function.Function;

public class CoinSearchAdapter extends BaseAdapter<Coin, CoinSearchAdapter.SearchCoinViewHolder> {
    private final RequestManager glide;
    private final Function<Bundle, Void> insertSearchFunction;
    private final NavController navController;

    public CoinSearchAdapter(Activity activity, int navContainerId, Function<Bundle, Void> insertSearchFunction) {
        super(new CoinComparator());
        this.glide = GlideStandard.getGlide(activity);
        this.navController = Navigation.findNavController(activity, navContainerId);
        this.insertSearchFunction = insertSearchFunction;
    }

    @Override
    protected CoinSearchAdapter.SearchCoinViewHolder getViewHolder(ViewGroup parent, int viewType) {
        SearchCoinItemBinding binding = SearchCoinItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CoinSearchAdapter.SearchCoinViewHolder(binding);
    }

    public class SearchCoinViewHolder extends RecyclerView.ViewHolder implements Function<Coin, Void> {
        SearchCoinItemBinding binding;

        public SearchCoinViewHolder(SearchCoinItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public Void apply(Coin coin) {
            binding.setName(coin.name);
            binding.setSymbol(coin.symbol);
            binding.setRank("#" + coin.rank);
            glide.load(coin.img).into(binding.imgCoin);
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CoinSearchAdapter.SearchCoinViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Bundle bundle = new Bundle();
        bundle.putString(coinArgKey, getList().get(position).id);

        holder.itemView.setOnClickListener(v -> {
            navController.navigate(R.id.coinDetailsFragment, bundle);
            insertSearchFunction.apply(bundle);
        });
        holder.itemView.setOnLongClickListener(v -> {
            bundle.putString(explorerArgKey, getList().get(position).explorer);
            navController.navigate(R.id.action_navigationFragment_to_dialog_coin, bundle);
            return false;
        });
    }
}
package com.hxl.cryptonumismatist.ui.fragments.coins.main;

import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.cryptonumismatist.ui.fragments.navigation.NavigationFragment.explorerArgKey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.base.BaseAdapter;
import com.hxl.cryptonumismatist.databinding.SearchCoinItemBinding;
import com.hxl.domain.model.Coin;

import java.util.function.Function;

import javax.inject.Inject;

public class SearchCoinsAdapter extends BaseAdapter<Coin, SearchCoinsAdapter.SearchCoinViewHolder> {
    private final RequestManager glide;
    private Function<Bundle, Void> onClick;
    private NavController navController;

    @Inject
    public SearchCoinsAdapter(RequestManager glide) {
        this.glide = glide;
        DiffUtil.ItemCallback<Coin> diffCallBack = new DiffUtil.ItemCallback<Coin>() {
            @Override
            public boolean areItemsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return oldItem.id.equals(newItem.id);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return oldItem.equals(newItem);
            }
        };
        SearchCoinsAdapter.super.differ = new AsyncListDiffer<>(this, diffCallBack);
    }

    public void setOnClick(Function<Bundle, Void> onClick) {
        this.onClick = onClick;
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    @Override
    protected SearchCoinsAdapter.SearchCoinViewHolder getViewHolder(ViewGroup parent, int viewType) {
        SearchCoinItemBinding binding = SearchCoinItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchCoinsAdapter.SearchCoinViewHolder(binding);
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
    public void onBindViewHolder(@NonNull SearchCoinsAdapter.SearchCoinViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Bundle bundle = new Bundle();
        bundle.putString(coinArgKey, getList().get(position).id);

        holder.itemView.setOnClickListener(v -> onClick.apply(bundle));
        holder.itemView.setOnLongClickListener(v -> {
            bundle.putString(explorerArgKey, getList().get(position).explorer);
            navController.navigate(R.id.action_navigationFragment_to_dialog_coin, bundle);
            return false;
        });
    }
}
package com.hxl.cryptonumismatist.ui.fragments.coins.main.adapter;

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
import com.hxl.cryptonumismatist.base.BasePagingAdapter;
import com.hxl.cryptonumismatist.databinding.CoinItemBinding;
import com.hxl.cryptonumismatist.util.CoinComparator;
import com.hxl.cryptonumismatist.util.EspressoIdlingResource;
import com.hxl.domain.model.Coin;

import java.util.Objects;

public class CoinAdapter extends BasePagingAdapter<Coin ,CoinViewHolder> {

    private final NavController navController;

    public CoinAdapter(Activity activity, int navContainerId) {
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

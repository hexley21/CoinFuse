package com.hxl.coinfuse.ui.fragments.exchanges;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeNameArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangePairsArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeUrlArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeVolumeArgKey;
import static com.hxl.coinfuse.util.NumberFormatUtil.formatBigDouble;
import static com.hxl.coinfuse.util.NumberFormatUtil.formatDouble;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseAdapter;
import com.hxl.coinfuse.databinding.ItemExchangeBinding;
import com.hxl.coinfuse.util.comparator.ExchangeComparator;
import com.hxl.domain.model.Exchange;

import java.util.function.Consumer;

public class ExchangeAdapter extends BaseAdapter<Exchange, ExchangeAdapter.ExchangeViewHolder> {
    private static final String TAG = "ExchangeAdapter";
    private NavController navController = null;

    public ExchangeAdapter() {
        super(new ExchangeComparator());
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    public NavController getNavController() {
        return navController;
    }

    @Override
    protected ExchangeViewHolder getViewHolder(ViewGroup parent, int viewType) {
        ItemExchangeBinding binding = ItemExchangeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ExchangeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExchangeViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final String exchangeId = getList().get(position).exchangeId;
        final String exchangeUrl = getList().get(position).exchangeUrl;
        final String exchangeName = getList().get(position).name;
        final String exchangeVolume = formatDouble(getList().get(position).volumeUsd);
        final Integer exchangePairs = getList().get(position).tradingPairs;

        if (navController == null) {
            Log.e(TAG, "onBindViewHolder: ", new NullPointerException("NavController was null"));
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString(exchangeArgKey, exchangeId);
        bundle.putString(exchangeUrlArgKey, exchangeUrl);

        holder.itemView.setOnClickListener(v -> {
                    bundle.putString(exchangeNameArgKey, exchangeName);
                    bundle.putString(exchangeVolumeArgKey, exchangeVolume);
                    bundle.putInt(exchangePairsArgKey, exchangePairs);
                    navigateToDetails(bundle);
                }
        );

        holder.itemView.setOnLongClickListener(v -> openDialog(bundle));

    }

    private void navigateToDetails(Bundle bundle) {
        assert navController.getCurrentDestination() != null;
        if (navController.getCurrentDestination().getId() != R.id.navigationFragment) return;

        navController.navigate(R.id.navigation_to_ExchangeDetails, bundle);
    }

    private boolean openDialog(Bundle bundle) {
        assert navController.getCurrentDestination() != null;
        if (navController.getCurrentDestination().getId() != R.id.navigationFragment) return false;

        navController.navigate(R.id.navigation_to_exchangeDialog, bundle);
        return true;
    }

    static final class ExchangeViewHolder extends RecyclerView.ViewHolder implements Consumer<Exchange> {
        ItemExchangeBinding binding;

        public ExchangeViewHolder(ItemExchangeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void accept(Exchange exchange) {
            binding.setName(exchange.name);
            binding.setRank(String.valueOf(exchange.rank));
            binding.setPrice(formatBigDouble(exchange.volumeUsd));
            binding.setPairs(String.valueOf(exchange.tradingPairs));
            binding.setCurrency("$");
        }
    }
}

package com.hxl.coinfuse.ui.fragments.exchanges.details;

import static com.hxl.coinfuse.util.NumberFormatUtil.formatBigDouble;
import static com.hxl.coinfuse.util.NumberFormatUtil.formatDouble;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.hxl.coinfuse.base.BaseAdapter;
import com.hxl.coinfuse.databinding.ItemTradeBinding;
import com.hxl.coinfuse.util.comparator.TradeComparator;
import com.hxl.domain.model.Trade;

import java.util.function.Consumer;

public class TradesAdapter extends BaseAdapter<Trade, TradesAdapter.TradesViewHolder> {

    protected boolean hasExchange;
    public TradesAdapter(boolean hasExchange) {
        super(new TradeComparator());
        this.hasExchange = hasExchange;
    }

    @Override
    protected TradesAdapter.TradesViewHolder getViewHolder(ViewGroup parent, int viewType) {
        ItemTradeBinding binding = ItemTradeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TradesViewHolder(binding);
    }

    public class TradesViewHolder extends RecyclerView.ViewHolder implements Consumer<Trade> {

        ItemTradeBinding binding;
        public TradesViewHolder(ItemTradeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void accept(Trade trade) {
            binding.setPairs(trade.baseSymbol + "\n" + trade.quoteSymbol);
            binding.setCurrency("$");
            binding.setPrice(formatDouble(trade.priceUsd));
            binding.setVol24h(formatBigDouble(trade.volumeUsd24Hr));

            if (hasExchange) {
                binding.tvExchange.setVisibility(View.VISIBLE);
                binding.setExchange(trade.exchangeId);
            }
        }
    }
}

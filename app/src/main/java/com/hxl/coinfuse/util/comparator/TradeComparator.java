package com.hxl.coinfuse.util.comparator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.hxl.domain.model.Trade;

public class TradeComparator extends DiffUtil.ItemCallback<Trade> {
    @Override
    public boolean areItemsTheSame(@NonNull Trade oldItem, @NonNull Trade newItem) {
        return ((oldItem.exchangeId.equals(newItem.exchangeId)) &&
                (oldItem.baseSymbol.equals(newItem.baseSymbol)) &&
                (oldItem.quoteSymbol.equals(newItem.quoteSymbol)));
    }

    @Override
    public boolean areContentsTheSame(@NonNull Trade oldItem, @NonNull Trade newItem) {
        return oldItem.equals(newItem);
    }
}

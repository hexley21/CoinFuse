package com.hxl.coinfuse.util.comparator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.hxl.domain.model.Exchange;

public class ExchangeComparator extends DiffUtil.ItemCallback<Exchange> {
    @Override
    public boolean areItemsTheSame(@NonNull Exchange oldItem, @NonNull Exchange newItem) {
        return oldItem.exchangeId.equals(newItem.exchangeId);
    }

    @Override
    public boolean areContentsTheSame(@NonNull Exchange oldItem, @NonNull Exchange newItem) {
        return oldItem.equals(newItem);
    }
}

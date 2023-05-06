package com.hxl.cryptonumismatist.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.hxl.domain.model.Coin;

public final class CoinComparator extends DiffUtil.ItemCallback<Coin> {
    @Override
    public boolean areItemsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
        return oldItem.id.equals(newItem.id);
    }

    @Override
    public boolean areContentsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
        return oldItem.equals(newItem);
    }
}

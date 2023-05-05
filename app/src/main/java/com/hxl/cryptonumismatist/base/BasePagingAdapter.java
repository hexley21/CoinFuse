package com.hxl.cryptonumismatist.base;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Function;

public abstract class BasePagingAdapter<T, VH extends RecyclerView.ViewHolder & Function<T, Void>> extends PagingDataAdapter<T, VH> {

    public BasePagingAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }
    protected abstract VH getViewHolder(ViewGroup parent, int viewType);

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.apply(getItem(position));
    }

}


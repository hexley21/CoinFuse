package com.hxl.cryptonumismatist.presentation.fragments;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.hxl.cryptonumismatist.utils.Binder;

import java.util.List;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder & Binder<T>> extends RecyclerView.Adapter<VH> {

    protected AsyncListDiffer<T> differ;

    public List<T> getList() {
        return differ.getCurrentList();
    }

    public void setList(List<T> list) {
        differ.submitList(list);
    }

    protected abstract VH getViewHolder(ViewGroup parent, int viewType);

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(getList().get(position));
    }

    @Override
    public int getItemCount() {
        return this.getList().size();
    }

}

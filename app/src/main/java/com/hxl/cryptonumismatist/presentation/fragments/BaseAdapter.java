package com.hxl.cryptonumismatist.presentation.fragments;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected AsyncListDiffer<T> differ;

    public List<T> getList() {
        return differ.getCurrentList();
    }

    public void setList(List<T> list) {
        differ.submitList(list);
    }

    protected abstract RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Binder<T>) holder).bind(getList().get(position));
    }

    @Override
    public int getItemCount() {
        return this.getList().size();
    }

    protected interface Binder<T> {
        void bind(T item);
    }
}

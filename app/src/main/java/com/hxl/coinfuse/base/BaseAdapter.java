package com.hxl.coinfuse.base;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Consumer;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder & Consumer<T>> extends RecyclerView.Adapter<VH> {

    protected final AsyncListDiffer<T> differ;

    public List<T> getList() {
        return differ.getCurrentList();
    }

    public void setList(List<T> list) {
        differ.submitList(list);
    }

    public void addOnDataChangeListener(AsyncListDiffer.ListListener<T> listener) {
        differ.addListListener(listener);
    }
    public BaseAdapter(DiffUtil.ItemCallback<T> comparator) {
        this.differ = new AsyncListDiffer<>(this, comparator);
    }

    protected abstract VH getViewHolder(ViewGroup parent, int viewType);

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        // binder
        holder.accept(getList().get(position));
    }

    @Override
    public int getItemCount() {
        return this.getList().size();
    }

}

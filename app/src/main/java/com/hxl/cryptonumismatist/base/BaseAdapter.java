package com.hxl.cryptonumismatist.base;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hxl.cryptonumismatist.util.EspressoIdlingResource;

import java.util.List;
import java.util.function.Function;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder & Function<T, Void>> extends RecyclerView.Adapter<VH> {

    protected final AsyncListDiffer<T> differ;

    public List<T> getList() {
        return differ.getCurrentList();
    }

    public BaseAdapter(DiffUtil.ItemCallback<T> comparator) {
        this.differ = new AsyncListDiffer<>(this, comparator);
    }

    public void setList(List<T> list) {
        EspressoIdlingResource.increment();
        differ.submitList(list);
        EspressoIdlingResource.decrement();
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
        holder.apply(getList().get(position));
    }

    @Override
    public int getItemCount() {
        return this.getList().size();
    }

}

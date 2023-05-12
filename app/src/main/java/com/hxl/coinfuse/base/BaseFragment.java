package com.hxl.coinfuse.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public abstract class BaseFragment<VB extends ViewBinding, VM extends ViewModel> extends Fragment {
    protected VB binding;
    protected VM vm;
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected abstract VB setViewBinding(LayoutInflater inflater, ViewGroup container);
    protected abstract VM setViewModel();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = setViewBinding(inflater, container);
        vm = setViewModel();
        onCreateView(savedInstanceState);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
        Log.d("Fragment " + getId(), "onDestroyView: CompositeDisposable was cleared");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        Log.d("Fragment " + getId(), "onDestroy: CompositeDisposable was disposed");
    }

    protected void onCreateView(Bundle savedInstanceState){}

}

package com.hxl.cryptonumismatist.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;


public abstract class BaseFragment<VB extends ViewBinding, VM extends ViewModel> extends Fragment {
    protected VB binding;
    protected VM vm;

    protected abstract VB setViewBinding(LayoutInflater inflater, ViewGroup container);
    protected abstract VM setViewModel();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = setViewBinding(inflater, container);
        vm = setViewModel();
        return binding.getRoot();
    }
}

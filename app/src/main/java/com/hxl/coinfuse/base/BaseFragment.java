package com.hxl.coinfuse.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.snackbar.Snackbar;


public abstract class BaseFragment<VB extends ViewBinding, VM extends ViewModel> extends Fragment {
    protected VB binding;
    protected VM vm;

    protected abstract VB setViewBinding(LayoutInflater inflater, ViewGroup container);
    protected abstract Class<VM> setViewModelClass();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = setViewBinding(inflater, container);
        vm = new ViewModelProvider(this).get(setViewModelClass());
        onCreateView(savedInstanceState);
        return binding.getRoot();
    }

    protected void onCreateView(Bundle savedInstanceState){}

    protected void showSnackBar(String message) {
        Snackbar.make(requireContext(), binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

}

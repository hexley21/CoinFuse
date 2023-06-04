package com.hxl.coinfuse.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseDialog<VB extends ViewBinding> extends BottomSheetDialogFragment {

    protected VB binding;
    private Integer defaultOrientation;
    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected abstract VB setViewBinding(LayoutInflater inflater, ViewGroup container);
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = setViewBinding(inflater, container);
        onCreateView(savedInstanceState);
        return binding.getRoot();
    }

    protected void onCreateView(Bundle savedInstanceState){}

    @Override
    public void onResume() {
        super.onResume();
        if (defaultOrientation == null) {
            defaultOrientation = requireActivity().getRequestedOrientation();
        }
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().setRequestedOrientation(defaultOrientation);
    }
}

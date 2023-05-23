package com.hxl.coinfuse.base;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.hxl.coinfuse.R;
import com.hxl.coinfuse.util.UiUtils;


public abstract class BasePopUpDialog extends AppCompatDialogFragment {
    private int checkedItem;
    private Integer defaultOrientation;

    protected abstract String setTitle();
    protected abstract String[] setChoice();
    protected abstract int setCheckedItem();
    protected abstract void positiveListener(int checkedItem);
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkedItem = setCheckedItem();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new MaterialAlertDialogBuilder(requireContext())
                .setTitle(setTitle())
                .setSingleChoiceItems(setChoice(), checkedItem, (dialog, which) -> checkedItem = which)
                .setPositiveButton(UiUtils.getString(requireContext(), R.string.positive_btn), (dialog, which) -> positiveListener(checkedItem))
                .setNegativeButton(UiUtils.getString(requireContext(), R.string.negative_btn), (dialog, which) -> dismiss())
                .create();
    }

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

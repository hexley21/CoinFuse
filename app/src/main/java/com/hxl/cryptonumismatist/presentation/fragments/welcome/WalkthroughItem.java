package com.hxl.cryptonumismatist.presentation.fragments.welcome;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.hxl.cryptonumismatist.databinding.ItemWalkthroughBinding;

public class WalkthroughItem extends Fragment {
    private ItemWalkthroughBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ItemWalkthroughBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            binding.setTitle(getStringResource(getArguments().getInt("title")));
            binding.setDescription(getStringResource(getArguments().getInt("description")));
            binding.imgWalkthrough.setImageDrawable(getDrawableResource(getArguments().getInt("image")));
        }
    }

    private String getStringResource(int id) {
        return getResources().getString(id);
    }

    private Drawable getDrawableResource(int id) {
        return ContextCompat.getDrawable(requireContext(), id);
    }

}

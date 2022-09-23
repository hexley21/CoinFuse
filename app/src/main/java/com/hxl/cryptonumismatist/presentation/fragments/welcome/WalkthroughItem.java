package com.hxl.cryptonumismatist.presentation.fragments.welcome;

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
            binding.setTitle(getArguments().getString("title"));
            binding.setDescription(getArguments().getString("description"));
            binding.imgWalkthrough.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), getArguments().getInt("image"))
            );
        }
    }

}

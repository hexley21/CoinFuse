package com.hxl.coinfuse.ui.fragments.welcome.walkthrough;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hxl.coinfuse.databinding.ItemWalkThroughBinding;

public class WalkThroughItem extends Fragment {
    private ItemWalkThroughBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ItemWalkThroughBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            binding.setTitle(getStringResource(getArguments().getInt("title")));
            binding.setDescription(getStringResource(getArguments().getInt("description")));
            binding.imgWalkThrough.setAnimation(getArguments().getInt("image"));
        }
    }

    private String getStringResource(int id) {
        return getResources().getString(id);
    }
}

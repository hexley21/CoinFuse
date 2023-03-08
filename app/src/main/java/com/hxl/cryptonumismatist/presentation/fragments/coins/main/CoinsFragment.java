package com.hxl.cryptonumismatist.presentation.fragments.coins.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hxl.cryptonumismatist.databinding.FragmentCoinsBinding;

public class CoinsFragment extends Fragment {
    private FragmentCoinsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCoinsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}
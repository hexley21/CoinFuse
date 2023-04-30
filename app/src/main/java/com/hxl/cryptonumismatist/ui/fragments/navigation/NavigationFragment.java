package com.hxl.cryptonumismatist.ui.fragments.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hxl.cryptonumismatist.databinding.FragmentNavigationBinding;

public class NavigationFragment extends Fragment {

    public static final String coinArgKey = "coin";
    FragmentNavigationBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNavigationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}

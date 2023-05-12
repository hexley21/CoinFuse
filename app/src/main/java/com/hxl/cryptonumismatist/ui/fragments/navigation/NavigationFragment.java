package com.hxl.cryptonumismatist.ui.fragments.navigation;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.databinding.FragmentNavigationBinding;

public class NavigationFragment extends Fragment {

    public static final String coinArgKey = "coinId";
    public static final String explorerArgKey = "explorerId";
    public static final String isTimeSortableArgKey = "isTimeSortable";
    public static final String coinSortCallbackArgKey = "coinSortCallback";
    public static final String orderByArgKey = "orderBy";
    public static final String sortByArgKey = "sortBy";
    FragmentNavigationBinding binding;

    NavController navController;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNavigationBinding.inflate(inflater, container, false);
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.fragment_main_container);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.bottomNavigation.setOnItemSelectedListener( item -> {
            switch (item.getItemId()) {
                case (R.id.menu_coins):
                    navController.navigate(R.id.coinsMenuFragment);
                    return true;
                case (R.id.menu_bookmarks):
                    navController.navigate(R.id.bookmarksFragment);
                    return true;
            }
            return false;
        });
    }
}

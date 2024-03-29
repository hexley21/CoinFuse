package com.hxl.coinfuse.ui.fragments.navigation;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.databinding.FragmentNavigationBinding;

public class NavigationFragment extends Fragment {

    public static final String coinArgKey = "coinId";
    public static final String coinNameArgKey = "coinName";
    public static final String coinSymbolArgKey = "coinSymbol";
    public static final String coinImgArgKey = "coinImg";
    public static final String exchangeArgKey = "exchangeId";
    public static final String explorerArgKey = "explorerId";
    public static final String searchQueryArgKey = "searchQuery";
    public static final String exchangeUrlArgKey = "exchangeUrl";
    public static final String exchangeNameArgKey = "exchangeName";
    public static final String exchangeVolumeArgKey = "exchangeVolume";
    public static final String exchangePairsArgKey = "exchangePairs";
    public static final String isTimeSortableArgKey = "isTimeSortable";
    public static final String sortCallbackArgKey = "sortCallback";
    public static final String dialogCallbackArgKey = "dialogCallback";
    public static final String checkedItemArgKey = "checkedItem";
    public static final String consumerArgKey = "consumer";
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
                case (R.id.menu_exchanges):
                    navController.navigate(R.id.exchangeFragment);
                    return true;
                case (R.id.menu_profit_calculator):
                    navController.navigate(R.id.profitCalculatorFragment);
                    return true;
                case (R.id.menu_settings):
                    navController.navigate(R.id.settingsFragment);
                    return true;
            }
            return false;
        });
    }
}

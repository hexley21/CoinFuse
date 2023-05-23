package com.hxl.coinfuse.ui.fragments.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.hxl.coinfuse.BuildConfig;
import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentSettingsBinding;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.presentation.viewmodels.SettingsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsFragment extends BaseFragment<FragmentSettingsBinding, SettingsViewModel> {
    @Override
    protected FragmentSettingsBinding setViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentSettingsBinding.inflate(inflater, container, false);
    }

    @Override
    protected Class<SettingsViewModel> setViewModelClass() {
        return SettingsViewModel.class;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);

        if (!vm.getCurrentEraseCache().hasObservers()) {
            vm.getCurrentEraseCache().observe(requireActivity(), state -> {
                if (state) {
                    showSnackBar(UiUtils.getString(requireContext(), R.string.clear_cache_success));
                    return;
                }
                showSnackBar(UiUtils.getString(requireContext(), R.string.clear_cache_fail));
            });
        }

        if (!vm.getCurrentEraseBookmarks().hasObservers()) {
            vm.getCurrentEraseBookmarks().observe(requireActivity(), state -> {
                if (state) {
                    showSnackBar(UiUtils.getString(requireContext(), R.string.clear_bookmarks_success));
                    return;
                }
                showSnackBar(UiUtils.getString(requireContext(), R.string.clear_bookmarks_fail));
            });
        }

        if (!vm.getCurrentEraseSearchHistory().hasObservers()) {
            vm.getCurrentEraseSearchHistory().observe(requireActivity(), state -> {
                if (state) {
                    showSnackBar(UiUtils.getString(requireContext(), R.string.clear_history_success));
                    return;
                }
                showSnackBar(UiUtils.getString(requireContext(), R.string.clear_history_fail));
            });
        }

        if (!vm.getCurrentEraseStorage().hasObservers()) {
            vm.getCurrentEraseStorage().observe(requireActivity(), state -> {
                if (state) {
                    showSnackBar(UiUtils.getString(requireContext(), R.string.clear_storage_success));
                    return;
                }
                showSnackBar(UiUtils.getString(requireContext(), R.string.clear_storage_fail));
            });
        }

        binding.setTheme(getThemeString(vm.getTheme()));
        binding.setLanguage(vm.getLanguage());
        binding.setCurrency(vm.getCurrency());
        binding.setVersion(BuildConfig.VERSION_NAME);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvClearBookmarks.setOnClickListener(v -> vm.eraseBookmarks());
        binding.tvClearSearch.setOnClickListener(v -> vm.eraseCoinSearchHistory());
        binding.tvClearCache.setOnClickListener(v -> vm.eraseCache());
        binding.tvClearStorage.setOnClickListener(v -> vm.eraseStorage());
    }

    private String getThemeString(int theme) {
        switch(theme) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                return UiUtils.getString(requireContext(), R.string.theme_light);
            case AppCompatDelegate.MODE_NIGHT_YES:
                return UiUtils.getString(requireContext(), R.string.theme_dark);
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                return UiUtils.getString(requireContext(), R.string.theme_system);
        }
        return "-";
    }
}

package com.hxl.coinfuse.ui.fragments.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

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

        binding.setTheme(getThemeString(vm.getTheme()));
        binding.setLanguage(vm.getLanguage());
        binding.setCurrency(vm.getCurrency());
        binding.setVersion(BuildConfig.VERSION_NAME);
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

package com.hxl.coinfuse.ui.fragments.settings;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.checkedItemArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.consumerArgKey;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.hxl.coinfuse.BuildConfig;
import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BaseFragment;
import com.hxl.coinfuse.databinding.FragmentSettingsBinding;
import com.hxl.coinfuse.ui.dialogs.ParcelableConsumer;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.domain.model.PrefKeys;
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

    private NavController navController;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);

        if (navController == null) {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_main);
        }

        binding.setTheme(getThemeString(vm.getTheme()));
        binding.setLanguage(vm.getLanguage().toUpperCase());
        binding.setVersion(BuildConfig.VERSION_NAME);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.prefTheme.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(checkedItemArgKey, vm.getTheme());
            bundle.putParcelable(consumerArgKey, (ParcelableConsumer<Integer>) this::changeTheme);

            navController.navigate(R.id.navigation_to_themeDialog, bundle);
        });

        binding.prefLanguage.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            if (!vm.getLanguage().equals(PrefKeys.LANGUAGE.def)) {
                bundle.putInt(checkedItemArgKey, 1);
            } else bundle.putInt(checkedItemArgKey, 0);

            bundle.putParcelable(consumerArgKey, (ParcelableConsumer<Integer>) val ->
                    changeLanguage(UiUtils.getStringArray(requireContext(), R.array.language_save)[val])
            );

            navController.navigate(R.id.navigation_to_languageDialog, bundle);
        });

        binding.tvClearBookmarks.setOnClickListener(v -> vm.eraseBookmarks(
                () -> showSnackBar(UiUtils.getString(requireContext(), R.string.clear_bookmarks_success)),
                () -> showSnackBar(UiUtils.getString(requireContext(), R.string.clear_bookmarks_fail))
        ));
        binding.tvClearSearch.setOnClickListener(v -> vm.eraseCoinSearchHistory(
                () -> showSnackBar(UiUtils.getString(requireContext(), R.string.clear_history_success)),
                () -> showSnackBar(UiUtils.getString(requireContext(), R.string.clear_history_fail))
        ));
        binding.tvClearCache.setOnClickListener(v -> vm.eraseCache(
                () -> showSnackBar(UiUtils.getString(requireContext(), R.string.clear_cache_success)),
                () -> showSnackBar(UiUtils.getString(requireContext(), R.string.clear_cache_fail))
        ));
        binding.tvClearStorage.setOnClickListener(v -> vm.eraseStorage(
                () -> showSnackBar(UiUtils.getString(requireContext(), R.string.clear_storage_success)),
                () -> showSnackBar(UiUtils.getString(requireContext(), R.string.clear_storage_fail))
        ));

        binding.tvAboutUs.setOnClickListener(v -> navController.navigate(R.id.navigation_to_aboutUsFragment));
        binding.tvContact.setOnClickListener(v -> navController.navigate(R.id.navigation_to_contactFragment));
        binding.tvPrivacyPolicy.setOnClickListener(v ->
                requireActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(UiUtils.getString(requireContext(), R.string.privacy_policy_link))))
        );

        binding.tvRateApp.setOnClickListener(v -> {
                    try {
                        startActivity(
                                new Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(String.format("market://details?id=%s", requireActivity().getPackageName()))
                                )
                        );
                    } catch (ActivityNotFoundException e) {
                        startActivity(
                                new Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(String.format("http://play.google.com/store/apps/details?id=%s", requireActivity().getPackageName()))
                                )
                        );
                    }
                });
    }

    private void changeTheme(int mode) {
        vm.saveTheme(mode);
        requireActivity().recreate();
    }

    private void changeLanguage(String lang) {
        vm.saveLanguage(lang);
        requireActivity().recreate();
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

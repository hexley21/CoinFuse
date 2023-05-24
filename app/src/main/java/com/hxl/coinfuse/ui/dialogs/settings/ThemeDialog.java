package com.hxl.coinfuse.ui.dialogs.settings;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.checkedItemArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.consumerArgKey;

import androidx.appcompat.app.AppCompatDelegate;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.base.BasePopUpDialog;
import com.hxl.coinfuse.ui.dialogs.ParcelableConsumer;
import com.hxl.coinfuse.util.UiUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ThemeDialog extends BasePopUpDialog {
    @Override
    protected String setTitle() {
        return UiUtils.getString(requireContext(), R.string.preference_theme_title);
    }

    @Override
    protected String[] setChoice() {
        return UiUtils.getStringArray(requireContext(), R.array.theme_choice);
    }

    @Override
    protected int setCheckedItem() {
        assert getArguments() != null;
        switch (getArguments().getInt(checkedItemArgKey)) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                return 0;
            case AppCompatDelegate.MODE_NIGHT_YES:
                return 1;
        }
        return 2;
    }

    @Override
    protected void positiveListener(int checkedItem) {
        assert getArguments() != null;
        final ParcelableConsumer<Integer> callback = getArguments().getParcelable(consumerArgKey);
        assert callback != null;
        callback.accept(UiUtils.getIntArray(requireContext(), R.array.theme_save)[checkedItem]);
    }

}

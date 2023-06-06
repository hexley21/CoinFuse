package com.hxl.coinfuse.ui.dialogs.settings;

import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.checkedItemArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.consumerArgKey;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.ui.dialogs.ParcelableConsumer;
import com.hxl.coinfuse.base.BasePopUpDialog;
import com.hxl.coinfuse.util.UiUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LanguageDialog extends BasePopUpDialog {
    @Override
    protected String setTitle() {
        return UiUtils.getString(requireContext(), R.string.preference_theme_language);
    }

    @Override
    protected String[] setChoice() {
        return UiUtils.getStringArray(requireContext(), R.array.language_choice);
    }

    @Override
    protected int setCheckedItem() {
        assert getArguments() != null;
        return getArguments().getInt(checkedItemArgKey);
    }

    @Override
    protected void positiveListener(int checkedItem) {
        assert getArguments() != null;
        final ParcelableConsumer<Integer> callback = getArguments().getParcelable(consumerArgKey);
        assert callback != null;
        callback.accept(checkedItem);
    }
}

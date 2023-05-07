package com.hxl.cryptonumismatist.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import androidx.annotation.ColorInt;

public final class UiUtils {

    @ColorInt
    public static int getColor(Context context, int colorId) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(colorId, typedValue, true);
        return typedValue.data;
    }

    public static String getString(Context context, int stringId) {
        return context.getString(stringId);
    }
}

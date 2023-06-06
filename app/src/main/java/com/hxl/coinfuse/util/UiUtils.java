package com.hxl.coinfuse.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.Locale;

public final class UiUtils {

    public static int getColor(Context context, int colorId) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(colorId, typedValue, true);
        return typedValue.data;
    }

    public static String getString(Context context, int stringId) {
        return context.getString(stringId);
    }

    public static String[] getStringArray(Context context, int stringId) {
        return context.getResources().getStringArray(stringId);
    }

    public static int[] getIntArray(Context context, int stringId) {
        return context.getResources().getIntArray(stringId);
    }

    public static Drawable getDrawable(Context context, int drawableId){
        return AppCompatResources.getDrawable(context, drawableId);
    }

    public static void setTheme(int mode) {
        AppCompatDelegate.setDefaultNightMode(mode);
    }

    public static void setLanguage(Activity activity, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}

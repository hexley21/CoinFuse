package com.hxl.cryptonumismatist.conf;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import com.hxl.cryptonumismatist.HiltTestActivity;

import java.util.Objects;

public final class HiltExt {
    public static <T extends Fragment> void launchFragmentInHiltContainer(Class<T> fragmentClass, Bundle args, int themeId) {
        Intent startActivityIntent = Intent.makeMainActivity(
                new ComponentName(ApplicationProvider.getApplicationContext(), HiltTestActivity.class)
        );

        startActivityIntent.putExtra(
                "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
                themeId
        );
        ActivityScenario.<HiltTestActivity>launch(startActivityIntent)
                .onActivity(activity -> {
                    Fragment fragment = activity.getSupportFragmentManager().getFragmentFactory().instantiate(
                            Objects.requireNonNull(fragmentClass.getClassLoader()),
                            fragmentClass.getName()
                    );

                    fragment.setArguments(args);
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .add(android.R.id.content, fragment, "")
                            .commitNow();
                });

    }

    public static <T extends Fragment> void launchFragmentInHiltContainer(Class<T> fragmentClass, Bundle args) {
        launchFragmentInHiltContainer(fragmentClass, args, androidx.appcompat.R.style.Theme_AppCompat_Empty);
    }
}
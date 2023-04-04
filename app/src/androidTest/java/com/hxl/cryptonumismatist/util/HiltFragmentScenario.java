package com.hxl.cryptonumismatist.util;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import com.hxl.cryptonumismatist.HiltTestActivity;

import java.util.Objects;
import java.util.function.Function;

public final class HiltFragmentScenario {
    public static <T extends Fragment> void launchFragmentInHiltContainer(
            Class<T> fragmentClass,
            Bundle args,
            Function<Fragment, Void> function
    ) {
        Intent startActivityIntent = Intent.makeMainActivity(
                new ComponentName(ApplicationProvider.getApplicationContext(), HiltTestActivity.class)
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

                    function.apply(fragment);
                });

    }
}
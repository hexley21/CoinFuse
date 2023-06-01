package com.hxl.coinfuse.conf;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import com.hxl.coinfuse.HiltTestActivity;

import java.util.Objects;

public final class HiltFragmentScenario {

    public static <T extends Fragment> ActivityScenario<HiltTestActivity> launchFragmentInHiltContainer(
            Class<T> fragmentClass,
            Bundle args
    ) {
        Intent startActivityIntent = Intent.makeMainActivity(
                new ComponentName(ApplicationProvider.getApplicationContext(), HiltTestActivity.class)
        );

        return ActivityScenario.<HiltTestActivity>launch(startActivityIntent)
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

    public static <T extends Fragment> ActivityScenario<HiltTestActivity> launchFragmentInHiltContainer(
            Class<T> fragmentClass
    ) {
        return launchFragmentInHiltContainer(fragmentClass, new Bundle());
    }

}
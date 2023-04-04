package com.hxl.cryptonumismatist;


import static com.hxl.cryptonumismatist.util.HiltFragmentScenario.launchFragmentInHiltContainer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.hxl.cryptonumismatist.ui.fragments.welcome.WelcomeFragment;
import com.hxl.domain.interactors.prefs.GetWelcome;
import com.hxl.domain.interactors.prefs.SaveWelcome;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Function;

import javax.inject.Inject;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

@RunWith(AndroidJUnit4.class)
@HiltAndroidTest
public class WelcomeFragmentTest {

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Inject
    GetWelcome getWelcome;
    @Inject
    SaveWelcome saveWelcome;

    @Before
    public void setUp() {
        hiltRule.inject();
    }

    @Test
    public void isFragmentVisible() {
        Function<Fragment, Void> x = fragment -> {
            TestNavHostController navController = new TestNavHostController(ApplicationProvider.getApplicationContext());
            navController.setGraph(R.navigation.nav_root);
            navController.setCurrentDestination(R.id.welcomeFragment);
            Navigation.setViewNavController(fragment.requireView(), navController);

            return null;
        };

        launchFragmentInHiltContainer(WelcomeFragment.class, new Bundle(), x);
        onView(withId(R.id.walk_through_pager)).check(matches(isDisplayed()));

    }

}

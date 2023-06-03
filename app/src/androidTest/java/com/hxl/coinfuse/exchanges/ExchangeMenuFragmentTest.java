package com.hxl.coinfuse.exchanges;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToLastPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.hxl.coinfuse.conf.HiltFragmentScenario.launchFragmentInHiltContainer;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.FlakyTest;
import androidx.test.filters.SmallTest;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.ui.fragments.exchanges.ExchangeFragment;
import com.hxl.coinfuse.util.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

@SmallTest
@HiltAndroidTest
@RunWith(AndroidJUnit4.class)
public class ExchangeMenuFragmentTest {


    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Before
    public void setUp() {
        hiltRule.inject();
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource);
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource);
    }

    @Test
    @FlakyTest
    public void fragmentBehavesAsExpected() {
        final NavController navController = mock(NavController.class);
        final NavDestination navDestination = mock(NavDestination.class);
        navDestination.setId(R.id.navigationFragment);

        when(navController.getCurrentDestination()).thenReturn(navDestination);

        launchFragmentInHiltContainer(ExchangeFragment.class, fragment -> ((ExchangeFragment) fragment).setNavController(navController));

        onView(withId(R.id.rv_exchanges)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rv_exchanges)).perform(scrollToLastPosition());
        onView(withId(R.id.rv_exchanges)).perform(scrollToPosition(0));
        onView(withId(R.id.srl_exchanges)).perform(swipeDown());

        onView(withId(R.id.chip_exchange_sort)).perform(click());

        verify(navController, times(2)).navigate(anyInt(), any(Bundle.class));

    }
}

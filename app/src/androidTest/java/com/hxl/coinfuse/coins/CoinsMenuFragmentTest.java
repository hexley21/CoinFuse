package com.hxl.coinfuse.coins;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToLastPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.hxl.coinfuse.conf.HiltFragmentScenario.launchFragmentInHiltContainer;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.ui.fragments.coins.main.CoinsMenuFragment;
import com.hxl.coinfuse.util.EspressoIdlingResource;
import com.hxl.domain.interactors.coins.InsertCoinSearchQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

@SmallTest
@HiltAndroidTest
@RunWith(AndroidJUnit4.class)
public class CoinsMenuFragmentTest {

    @Rule
    public final HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Inject
    public InsertCoinSearchQuery insertCoinSearchQuery;

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
    @SmallTest
    public void fragmentBehavesAsExpected() {
        insertCoinSearchQuery.invoke("bitcoin").test()
                .awaitCount(1)
                .assertNoErrors()
                .assertComplete();

        NavController navController = mock(NavController.class);
        NavDestination navDestination = mock(NavDestination.class);

        navDestination.setId(R.id.navigationFragment);
        when(navController.getCurrentDestination()).thenReturn(navDestination);

        launchFragmentInHiltContainer(CoinsMenuFragment.class, fragment -> ((CoinsMenuFragment) fragment).setNavController(navController));

        // Check Coins Fragment basic properties
        onView(withId(R.id.shimmer_coins)).check(matches(not(isDisplayed())));
        onView(withId(R.id.search_bar)).check(matches(isDisplayed()));


        onView(withId(R.id.rv_coins)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rv_coins)).perform(scrollToLastPosition());
        onView(withId(R.id.rv_coins)).perform(scrollToPosition(0));
        onView(withId(R.id.srl_coins)).perform(swipeDown());

        // Perform actions on Search Bar
        onView(withId(R.id.search_bar)).perform(click());
        onView(withId(R.id.search_view)).check(matches(isDisplayed()));

        // Perform search on the Search View
        onView(withId(com.google.android.material.R.id.search_view_edit_text))
                .perform(typeText("bitcoin"), pressKey(KeyEvent.KEYCODE_ENTER));

        // Perform actions on search recycler view
        onView(withId(R.id.rv_coin_search)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_coin_search)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rv_coin_search)).perform(scrollToLastPosition());
        onView(withId(R.id.rv_coin_search)).perform(scrollToPosition(0));

        // perform click on SearchView's clear button
        onView(withId(com.google.android.material.R.id.search_view_clear_button)).perform(click());
        onView(withId(com.google.android.material.R.id.search_view_edit_text)).check(matches(withText("")));
        onView(withId(R.id.rv_coin_search)).check(matches(not(isDisplayed())));

        // perform search-history item click
        onView(withId(R.id.rv_coin_history)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rv_coin_history)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_coin_history)).perform(scrollToLastPosition());
        onView(withId(R.id.rv_coin_history)).perform(scrollToPosition(0));

        // perform click on SearchView's back button
        onView(withContentDescription(com.google.android.material.R.string.searchview_navigation_content_description)).perform(click());

        verify(navController, times(3)).navigate(eq(R.id.navigation_to_coinDetails), any(Bundle.class));
    }
}

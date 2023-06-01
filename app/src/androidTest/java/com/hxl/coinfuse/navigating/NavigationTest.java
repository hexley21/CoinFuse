package com.hxl.coinfuse.navigating;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.pressBackUnconditionally;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinNameArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinSymbolArgKey;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.view.KeyEvent;

import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.app.MainActivity;
import com.hxl.coinfuse.util.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

@RunWith(AndroidJUnit4.class)
@SmallTest
@HiltAndroidTest
public class NavigationTest {

    @Rule(order = 0)
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    NavController rootNavController;
    NavController mainNavController;

    @Before
    public void setUp() {
        hiltRule.inject();
        scenarioRule.getScenario().onActivity(activity -> rootNavController = Navigation.findNavController(activity, R.id.nav_host_fragment_main));
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource);
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource);
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void testAppNavigation() {
        // App starts with welcome fragment
        assertEquals(rootNavController.getCurrentDestination().getId(), R.id.welcomeFragment);

        // Welcome -> Navigation(CoinsMenu)
        onView(withId(R.id.tv_skip)).perform(ViewActions.click());
        scenarioRule.getScenario().onActivity(activity -> mainNavController = Navigation.findNavController(activity.findViewById(R.id.fragment_main_container)));
        assertRootNav(R.id.navigationFragment);
        assertMainNav(R.id.coinsMenuFragment);

        // Coin-menu -> Coin-details
        onView(withId(R.id.rv_coins)).perform(actionOnItemAtPosition(0, ViewActions.click()));
        assertRootNav(R.id.coinDetailsFragment, coinArgKey, coinNameArgKey, coinSymbolArgKey, coinSymbolArgKey);
        assertMainNav(R.id.coinsMenuFragment);

        // Coin-menu <- Coin-details
        pressBack();
        assertRootNav(R.id.navigationFragment);
        assertMainNav(R.id.coinsMenuFragment);

        // Coins-menu(search) -> Coin-details
        onView(withId(R.id.search_bar)).perform(click());
        onView(withId(R.id.search_view)).check(matches(isDisplayed()));

        onView(withId(com.google.android.material.R.id.search_view_edit_text))
                .perform(typeText("bitcoin"), pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.rv_coin_search)).perform(actionOnItemAtPosition(0, ViewActions.click()));

        assertRootNav(R.id.coinDetailsFragment, coinArgKey, coinNameArgKey, coinSymbolArgKey, coinSymbolArgKey);
        assertMainNav(R.id.coinsMenuFragment);

        // Coin-menu(search) <- Coin-details
        pressBack();
        assertRootNav(R.id.navigationFragment);
        assertEquals(mainNavController.getCurrentDestination().getId(), R.id.coinsMenuFragment);
        onView(withId(R.id.search_view)).check(matches(isDisplayed()));

        // Coin-menu(search-history) -> Coin-details
        onView(withId(com.google.android.material.R.id.search_view_clear_button)).perform(click());
        onView(withId(R.id.rv_coin_history)).perform(actionOnItemAtPosition(0, ViewActions.click()));

        assertRootNav(R.id.coinDetailsFragment, coinArgKey, coinNameArgKey, coinSymbolArgKey, coinSymbolArgKey);
        assertMainNav(R.id.coinsMenuFragment);

        // Coin-menu(search-history) <- Coin-details
        pressBack();
        assertRootNav(R.id.navigationFragment);
        assertMainNav(R.id.coinsMenuFragment);

        // Coin-menu closes search view
        onView(withId(R.id.search_view)).check(matches(isDisplayed()));
        onView(withContentDescription(com.google.android.material.R.string.searchview_navigation_content_description)).perform(click());
        assertRootNav(R.id.navigationFragment);
        assertMainNav(R.id.coinsMenuFragment);

        // App closes
        pressBackUnconditionally();
        assertEquals(Lifecycle.State.CREATED, scenarioRule.getScenario().getState());
    }

    private void assertMainNav(int id) {
        assert mainNavController.getCurrentDestination() != null;
        assertEquals(mainNavController.getCurrentDestination().getId(), id);
    }

    private void assertMainNav(int id, String... bundleId) {
        assertMainNav(id);
        assert mainNavController.getCurrentBackStackEntry() != null;
        assert mainNavController.getCurrentBackStackEntry().getArguments() != null;

        for (String i: bundleId) {
            assertNotNull(mainNavController.getCurrentBackStackEntry().getArguments().getString(i));
        }
    }

    private void assertRootNav(int id) {
        assert rootNavController.getCurrentDestination() != null;
        assertEquals(rootNavController.getCurrentDestination().getId(), id);
    }

    private void assertRootNav(int id, String... bundleId) {
        assertRootNav(id);
        assert rootNavController.getCurrentBackStackEntry() != null;
        assert rootNavController.getCurrentBackStackEntry().getArguments() != null;

        for (String i: bundleId) {
            assertNotNull(rootNavController.getCurrentBackStackEntry().getArguments().getString(i));
        }
    }
}

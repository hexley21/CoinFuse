package com.hxl.coinfuse.navigating;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.pressBackUnconditionally;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
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
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.consumerArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeNameArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeUrlArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeVolumeArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.explorerArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.orderByArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.searchQueryArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.sortByArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.sortCallbackArgKey;
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
import androidx.test.filters.LargeTest;
import androidx.test.filters.MediumTest;
import androidx.test.filters.SmallTest;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.app.MainActivity;
import com.hxl.coinfuse.util.EspressoIdlingResource;
import com.hxl.domain.interactors.coins.BookmarkCoin;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

@LargeTest
@HiltAndroidTest
@RunWith(AndroidJUnit4.class)
public class NavigationTest {

    @Rule(order = 0)
    public final HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public final ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Inject
    public BookmarkCoin bookmarkCoin;

    private NavController rootNavController;
    private NavController mainNavController;

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
    @MediumTest
    @SuppressWarnings("ConstantConditions")
    public void testCoinNavigation() {
        startTest();
        assertRootNav(R.id.navigationFragment);
        assertMainNav(R.id.coinsMenuFragment);

        // Coin-menu -> Coin-details
        onView(withId(R.id.rv_coins)).perform(actionOnItemAtPosition(0, ViewActions.click()));
        assertRootNav(R.id.coinDetailsFragment, coinArgKey, coinNameArgKey, coinSymbolArgKey, coinSymbolArgKey);
        assertMainNav(R.id.coinsMenuFragment);

        // Coin-details: Coin-prices <-> Coin-exchanges
        onView(withId(R.id.coin_details_pager)).perform(ViewActions.slowSwipeLeft());
        onView(withId(R.id.coin_details_pager)).perform(ViewActions.swipeRight());
        // Coin-menu <- Coin-details
        pressBack();
        assertRootNav(R.id.navigationFragment);
        assertMainNav(R.id.coinsMenuFragment);

        // Coin-menu -> Coin-dialog
        onView(withId(R.id.rv_coins)).perform(actionOnItemAtPosition(0, longClick()));
        assertRootNav(R.id.coinDialog, coinArgKey, explorerArgKey);
        assertMainNav(R.id.coinsMenuFragment);

        // Coin-menu <- Coin-dialog
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

        // Coin-menu(search) -> Coin-dialog
        onView(withId(R.id.rv_coin_search)).perform(actionOnItemAtPosition(0, longClick()));
        assertRootNav(R.id.coinDialog, coinArgKey, explorerArgKey);
        assertMainNav(R.id.coinsMenuFragment);

        // Coin-menu(search) <- Coin-dialog
        pressBack();
        assertRootNav(R.id.navigationFragment);
        assertMainNav(R.id.coinsMenuFragment);

        // Coin-menu(search-history) -> Coin-details
        onView(withId(com.google.android.material.R.id.search_view_clear_button)).perform(click());
        onView(withId(R.id.rv_coin_history)).perform(actionOnItemAtPosition(0, ViewActions.click()));

        assertRootNav(R.id.coinDetailsFragment, coinArgKey, coinNameArgKey, coinSymbolArgKey, coinSymbolArgKey);
        assertMainNav(R.id.coinsMenuFragment);

        // Coin-menu(search-history) <- Coin-details
        pressBack();
        assertRootNav(R.id.navigationFragment);
        assertMainNav(R.id.coinsMenuFragment);

        // Coin-menu(search-history) -> Coin-dialog
        onView(withId(R.id.rv_coin_history)).perform(actionOnItemAtPosition(0, longClick()));
        assertRootNav(R.id.coinDialog, coinArgKey, explorerArgKey, searchQueryArgKey);
        assertMainNav(R.id.coinsMenuFragment);

        // Coin-menu(search-history) <- Coin-dialog(remove-history)
        onView(withId(R.id.dialog_coin_delete_search)).perform(ViewActions.click());
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

    @Test
    @SmallTest
    @SuppressWarnings("ConstantConditions")
    public void testExchangesNavigation() {
        startTest();
        onView(withId(R.id.menu_exchanges)).perform(ViewActions.click());
        assertMainNav(R.id.exchangeFragment);

        // Exchange-menu -> Exchange-details
        onView(withId(R.id.rv_exchanges)).perform(actionOnItemAtPosition(0, click()));
        assertRootNav(R.id.exchangeDetailsFragment, exchangeArgKey, exchangeNameArgKey, exchangeVolumeArgKey, exchangeUrlArgKey);
        assertMainNav(R.id.exchangeFragment);

        // Exchange-menu <- Exchange-details
        pressBack();
        assertMainNav(R.id.exchangeFragment);
        assertRootNav(R.id.navigationFragment);

        // Exchange-menu -> Exchange-dialog
        onView(withId(R.id.rv_exchanges)).perform(actionOnItemAtPosition(0, longClick()));
        assertMainNav(R.id.exchangeFragment);
        assertRootNav(R.id.exchangeDialog, exchangeArgKey, exchangeUrlArgKey);

        // Exchange-menu <- Exchange-dialog
        pressBack();
        assertMainNav(R.id.exchangeFragment);
        assertRootNav(R.id.navigationFragment);

        // Exchange-menu -> Exchange-dialog(sort)
        onView(withId(R.id.chip_exchange_sort)).perform(click());
        assertMainNav(R.id.exchangeFragment);
        assertRootNav(R.id.exchangeSortDialog);
        assertNotNull(rootNavController.getCurrentBackStackEntry().getArguments().getParcelable(sortCallbackArgKey));
        assertNotNull(rootNavController.getCurrentBackStackEntry().getArguments().getSerializable(orderByArgKey));
        assertNotNull(rootNavController.getCurrentBackStackEntry().getArguments().getSerializable(sortByArgKey));

        // Exchange-menu <- Exchange-dialog(sort)
        pressBack();
        assertMainNav(R.id.exchangeFragment);
        assertRootNav(R.id.navigationFragment);
    }

    @Test
    @SmallTest
    @SuppressWarnings("ConstantConditions")
    public void testProfitCalculatorNavigation() {
        startTest();
        onView(withId(R.id.menu_profit_calculator)).perform(ViewActions.click());
        assertMainNav(R.id.profitCalculatorFragment);

        // Profit-calculator -> Coin-search
        onView(withId(R.id.profit_search_bar)).perform(click());
        assertMainNav(R.id.profitCalculatorFragment);
        assertRootNav(R.id.profitCalculatorDialog);
        assertNotNull(rootNavController.getCurrentBackStackEntry().getArguments().getParcelable(consumerArgKey));

        // Profit-calculator <- Coin-search
        onView(withId(R.id.rv_profit_search)).perform(actionOnItemAtPosition(0, click()));
        assertMainNav(R.id.profitCalculatorFragment);
        assertRootNav(R.id.navigationFragment);

    }

    @Test
    @SmallTest
    @SuppressWarnings("ConstantConditions")
    public void testBookmarksNavigation() {
        bookmarkCoin.invoke("bitcoin").test()
                .awaitCount(1)
                .assertNoErrors()
                .assertComplete();

        startTest();
        onView(withId(R.id.menu_bookmarks)).perform(click());
        assertMainNav(R.id.bookmarksFragment);
        assertRootNav(R.id.navigationFragment);

        // Bookmarks -> Coin-details
        onView(withId(R.id.rv_coin_bookmarks)).perform(actionOnItemAtPosition(0, click()));
        assertMainNav(R.id.bookmarksFragment);
        assertRootNav(R.id.coinDetailsFragment, coinArgKey, coinNameArgKey, coinSymbolArgKey, coinSymbolArgKey);

        // Bookmarks <- Coin-details
        pressBack();
        assertMainNav(R.id.bookmarksFragment);
        assertRootNav(R.id.navigationFragment);

        // Bookmarks -> Sort-dialog
        onView(withId(R.id.chip_coin_bookmark_sort)).perform(click());
        assertMainNav(R.id.bookmarksFragment);
        assertRootNav(R.id.coinSortDialog);
        assertNotNull(rootNavController.getCurrentBackStackEntry().getArguments().getParcelable(sortCallbackArgKey));
        assertNotNull(rootNavController.getCurrentBackStackEntry().getArguments().getSerializable(orderByArgKey));
        assertNotNull(rootNavController.getCurrentBackStackEntry().getArguments().getSerializable(sortByArgKey));

        // Bookmarks <- Sort-dialog
        onView(withId(R.id.sort_coin_apply)).perform(click());
        assertMainNav(R.id.bookmarksFragment);
        assertRootNav(R.id.navigationFragment);
    }

    @Test
    @MediumTest
    @SuppressWarnings("ConstantConditions")
    public void testSettingsFragmentNavigation() {
        startTest();
        onView(withId(R.id.menu_settings)).perform(click());
        assertMainNav(R.id.settingsFragment);
        assertRootNav(R.id.navigationFragment);

        // Settings -> Theme-dialog
        onView(withId(R.id.pref_theme)).perform(click());
        assertMainNav(R.id.settingsFragment);
        assertRootNav(R.id.themeDialog);
        assertNotNull(rootNavController.getCurrentBackStackEntry().getArguments().getParcelable(consumerArgKey));

        // Settings <- Theme-dialog
        pressBack();
        assertMainNav(R.id.settingsFragment);
        assertRootNav(R.id.navigationFragment);

        // Settings -> Language-dialog
        onView(withId(R.id.pref_language)).perform(click());
        assertMainNav(R.id.settingsFragment);
        assertRootNav(R.id.languageDialog);
        assertNotNull(rootNavController.getCurrentBackStackEntry().getArguments().getParcelable(consumerArgKey));

        // Settings <- Language-dialog
        pressBack();
        assertMainNav(R.id.settingsFragment);
        assertRootNav(R.id.navigationFragment);

        // Settings -> About-us fragment
        onView(withId(R.id.tv_about_us)).perform(click());
        assertMainNav(R.id.settingsFragment);
        assertRootNav(R.id.aboutUsFragment);

        // Settings <- About-us fragment
        pressBack();
        assertMainNav(R.id.settingsFragment);
        assertRootNav(R.id.navigationFragment);

        // Settings -> Contact-fragment
        onView(withId(R.id.tv_contact)).perform(click());
        assertMainNav(R.id.settingsFragment);
        assertRootNav(R.id.contactFragment);

        // Settings <- Contact-fragment
        pressBack();
        assertMainNav(R.id.settingsFragment);
        assertRootNav(R.id.navigationFragment);

        // Settings -> Credits-fragment
        onView(withId(R.id.tv_credits)).perform(click());
        assertMainNav(R.id.settingsFragment);
        assertRootNav(R.id.creditsFragment);

        // Settings <- Contact-fragment
        pressBack();
        assertMainNav(R.id.settingsFragment);
        assertRootNav(R.id.navigationFragment);
    }

    private void startTest() {
        assert rootNavController.getCurrentDestination() != null;
        // App starts with welcome fragment
        assertEquals(rootNavController.getCurrentDestination().getId(), R.id.welcomeFragment);

        // Welcome -> Navigation(exchanges)
        onView(withId(R.id.tv_skip)).perform(click());
        scenarioRule.getScenario().onActivity(activity -> mainNavController = Navigation.findNavController(activity.findViewById(R.id.fragment_main_container)));
    }

    private void assertMainNav(int id) {
        assert mainNavController.getCurrentDestination() != null;
        assertEquals(mainNavController.getCurrentDestination().getId(), id);
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

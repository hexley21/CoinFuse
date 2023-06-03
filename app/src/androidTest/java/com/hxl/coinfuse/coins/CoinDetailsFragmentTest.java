package com.hxl.coinfuse.coins;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.slowSwipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.hxl.coinfuse.conf.HiltFragmentScenario.launchFragmentInHiltContainer;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinImgArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinNameArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.coinSymbolArgKey;

import android.os.Bundle;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.FlakyTest;
import androidx.test.filters.SmallTest;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.ui.fragments.coins.details.CoinDetailsFragment;
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
public class CoinDetailsFragmentTest {

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
    public void coinDetailsFragmentBehavesAsExpected() {

        Bundle bundle = new Bundle();
        bundle.putString(coinArgKey, "bitcoin");
        bundle.putString(coinNameArgKey, "Bitcoin");
        bundle.putString(coinSymbolArgKey, "BTC");
        bundle.putString(coinImgArgKey, "btc");
        launchFragmentInHiltContainer(CoinDetailsFragment.class, bundle);

        onView(withId(R.id.chip_7d)).perform(click());
        onView(withId(R.id.chip_14d)).perform(scrollTo(), click());
        onView(withId(R.id.chip_1m)).perform(scrollTo(), click());
        onView(withId(R.id.chip_2m)).perform(scrollTo(), click());
        onView(withId(R.id.chip_6m)).perform(scrollTo(), click());
        onView(withId(R.id.chip_1y)).perform(scrollTo(), click());
        onView(withId(R.id.chip_24h)).perform(scrollTo(), click());

        onView(withId(R.id.coin_details_pager)).perform(slowSwipeLeft());
        onView(withId(R.id.coin_details_pager)).perform(swipeRight());
    }
}

package com.hxl.coinfuse.exchanges;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.hxl.coinfuse.conf.HiltFragmentScenario.launchFragmentInHiltContainer;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeNameArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangePairsArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeUrlArgKey;
import static com.hxl.coinfuse.ui.fragments.navigation.NavigationFragment.exchangeVolumeArgKey;

import android.os.Bundle;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.FlakyTest;
import androidx.test.filters.SmallTest;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.ui.fragments.exchanges.details.ExchangeDetailsFragment;
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
public class ExchangeDetailsTest {


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
        final Bundle bundle = new Bundle();
        bundle.putString(exchangeArgKey, "binance");
        bundle.putString(exchangeUrlArgKey, "binance.com");
        bundle.putString(exchangeNameArgKey, "Binance");
        bundle.putString(exchangeVolumeArgKey, "$1B");
        bundle.putInt(exchangePairsArgKey, 123);

        launchFragmentInHiltContainer(ExchangeDetailsFragment.class, bundle);

        onView(withId(R.id.rv_exchange_trades)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_exchange_volume)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_exchange_volume_val)).check(matches(isDisplayed()));
        onView(withId(R.id.exchanges_top_appbar)).check(matches(isDisplayed()));

    }
}
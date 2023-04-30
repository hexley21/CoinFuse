package com.hxl.cryptonumismatist.coins;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.hxl.cryptonumismatist.conf.fake.FakeDataFactory.COIN;
import static com.hxl.cryptonumismatist.util.HiltFragmentScenario.launchFragmentInHiltContainer;
import static com.hxl.cryptonumismatist.util.NumberFormatUtil.formatDoubleDetailed;
import static com.hxl.cryptonumismatist.util.NumberFormatUtil.formatFloat;

import android.content.SharedPreferences;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.hxl.cryptonumismatist.R;
import com.hxl.cryptonumismatist.conf.fake.di.FakeCoinRepository;
import com.hxl.cryptonumismatist.di.DataModule;
import com.hxl.cryptonumismatist.ui.fragments.coins.details.CoinDetailsFragment;
import com.hxl.cryptonumismatist.util.EspressoIdlingResource;
import com.hxl.data.PreferenceRepositoryImpl;
import com.hxl.data.repository.pref.PreferenceLocal;
import com.hxl.domain.repository.CoinRepository;
import com.hxl.domain.repository.PreferenceRepository;
import com.hxl.local.PreferenceLocalImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import dagger.hilt.components.SingletonComponent;

@RunWith(AndroidJUnit4.class)
@SmallTest
@UninstallModules(DataModule.class)
@HiltAndroidTest
public class CoinDetailsFragmentTest {

    @Module
    @InstallIn(SingletonComponent.class)
    public static class TestDataModule {

        // region prefs
        @Provides
        @Singleton
        PreferenceLocal providePreferenceLocal(SharedPreferences sharedPreferences) {
            return new PreferenceLocalImpl(sharedPreferences);
        }

        @Provides
        @Singleton
        PreferenceRepository providePreferenceRepository(PreferenceLocal preferenceLocal) {
            return new PreferenceRepositoryImpl(preferenceLocal);

        }
        // endregion

        @Provides
        @Singleton
        public CoinRepository provideCoinRepository() {
            return new FakeCoinRepository();
        }
    }

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
    public void coinDetailsFragmentBehavesAsExpected() {
        launchFragmentInHiltContainer(CoinDetailsFragment.class);

        onView(withId(R.id.tv_name))
                .check(matches(withText(COIN.name)));
        onView(withId(R.id.tv_symbol))
                .check(matches(withText(
                        String.format("(%s)", COIN.symbol)
                )));
        onView(withId(R.id.tv_price))
                .check(matches(withText(
                        String.format("%s$",formatDoubleDetailed(COIN.priceUsd))
                )));
        onView(withId(R.id.tv_change))
                .check(matches(withText(
                        String.format("▴%s%%",formatFloat(COIN.changePercent24Hr))
                )));

        onView(withId(R.id.tv_market_cap_val))
                .check(matches(withText(
                        String.format("%s$", formatDoubleDetailed(COIN.marketCapUsd))
                )));
        onView(withId(R.id.tv_volume_24_val))
                .check(matches(withText(
                        String.format("%s$", formatDoubleDetailed(COIN.volumeUsd24Hr))
                )));
        onView(withId(R.id.tv_supply_val))
                .check(matches(withText(
                        String.format("%s (%s)", formatDoubleDetailed(COIN.supply), COIN.symbol)
                )));
    }
}

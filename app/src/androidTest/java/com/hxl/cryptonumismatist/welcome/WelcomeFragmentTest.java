package com.hxl.cryptonumismatist.welcome;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.hxl.cryptonumismatist.util.HiltFragmentScenario.launchFragmentInHiltContainer;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.hxl.cryptonumismatist.R;
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
@SmallTest
@HiltAndroidTest
public class WelcomeFragmentTest {

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Inject
    GetWelcome getWelcome;
    @Inject
    SaveWelcome saveWelcome;

    TestNavHostController navController;

    @Before
    public void setUp() {
        hiltRule.inject();
        navController = new TestNavHostController(ApplicationProvider.getApplicationContext());
    }

    @Test
    public void fragmentBehavesAsExpected() {
        // Arrange
        Function<Fragment, Void> setNavController = fragment -> {
            navController.setGraph(R.navigation.nav_root);
            navController.setCurrentDestination(R.id.welcomeFragment);
            Navigation.setViewNavController(fragment.requireView(), navController);

            return null;
        };
        // Act
        launchFragmentInHiltContainer(WelcomeFragment.class, setNavController);
        // Assert
        onView(withId(R.id.walk_through_pager)).check(matches(isDisplayed()));

        onView(withId(R.id.btn_next)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_skip)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_get_started)).check(matches(not(isDisplayed())));

        onView(withId(R.id.btn_next)).perform(ViewActions.click());
        onView(withId(R.id.walk_through_pager)).perform(ViewActions.slowSwipeLeft());

        onView(withId(R.id.btn_next)).check(matches(not(isDisplayed())));
        onView(withId(R.id.tv_skip)).check(matches(not(isDisplayed())));
        onView(withId(R.id.btn_get_started)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_get_started)).perform(ViewActions.click());

        assertEquals(navController.getCurrentDestination().getId(), R.id.navigationFragment);
        assertFalse(getWelcome.invoke());
    }

}

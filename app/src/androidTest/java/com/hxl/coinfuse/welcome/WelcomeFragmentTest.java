package com.hxl.coinfuse.welcome;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.hxl.coinfuse.util.HiltFragmentScenario.launchFragmentInHiltContainer;
import static org.hamcrest.CoreMatchers.not;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.ui.fragments.welcome.WelcomeFragment;
import com.hxl.domain.interactors.prefs.GetWelcome;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

    @Before
    public void setUp() {
        hiltRule.inject();
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void fragmentBehavesAsExpected() {
        // Act
        launchFragmentInHiltContainer(WelcomeFragment.class);
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
    }

}

package com.hxl.coinfuse.profit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.hxl.coinfuse.conf.HiltFragmentScenario.launchFragmentInHiltContainer;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.ui.fragments.profit_cal.ProfitCalculatorFragment;
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
public class ProfitCalculatorTest {

    @Rule
    public final HiltAndroidRule hiltRule = new HiltAndroidRule(this);

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
        launchFragmentInHiltContainer(ProfitCalculatorFragment.class);

        onView(allOf(
                isDescendantOfA(withId(R.id.tf_profit_buy)),
                withClassName(endsWith("EditText"))))
                .perform(typeText("1"));
        onView(allOf(
                isDescendantOfA(withId(R.id.tf_profit_investment)),
                withClassName(endsWith("EditText"))))
                .perform(typeText("6"));
        onView(allOf(
                isDescendantOfA(withId(R.id.tf_profit_sell)),
                withClassName(endsWith("EditText"))))
                .perform(typeText("2"));
        onView(allOf(
                isDescendantOfA(withId(R.id.tf_profit_in_fee)),
                withClassName(endsWith("EditText"))))
                .perform(typeText("2"));
        onView(allOf(
                isDescendantOfA(withId(R.id.tf_profit_ex_fee)),
                withClassName(endsWith("EditText"))))
                .perform(typeText("2"));

        pressBack();

        onView(withId(R.id.profit_exit)).check(matches(withText("$10.00")));
        onView(withId(R.id.profit_investment)).check(matches(withText("$8.00")));
        onView(withId(R.id.profit_result)).check(matches(withText("$2.00")));
        onView(withId(R.id.profit_change)).check(matches(withText("33.33%")));

    }

}

package com.hxl.cryptonumismatist;

import com.hxl.domain.interactors.prefs.GetWelcome;
import com.hxl.domain.interactors.prefs.SaveWelcome;

import org.junit.Before;
import org.junit.Rule;

import javax.inject.Inject;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

@HiltAndroidTest
public class WelcomeFragmentTest {

    @Rule
    HiltAndroidRule hiltRule = new HiltAndroidRule(this);
    @Inject
    GetWelcome getWelcome;
    @Inject
    SaveWelcome saveWelcome;

    @Before
    public void setUp() {
        hiltRule.inject();
    }

}

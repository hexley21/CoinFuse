package com.hxl.local;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static com.hxl.local.fake.LocalTestConstants.*;
import static org.junit.Assert.assertEquals;

@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4.class)
public class PreferenceLocalImplTest {

    SharedPreferences sharedPreferences;
    PreferenceLocalImpl preferenceLocal;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        preferenceLocal = new PreferenceLocalImpl(sharedPreferences);
    }

    @After
    public void tearDown() {
        sharedPreferences.edit().clear().apply();
    }

    @Test
    public void get_int_returns_value() {
        // Act
        sharedPreferences.edit().putInt(KEY_INT, VAL_INT).apply();
        int result = preferenceLocal.get(KEY_INT, DEF_INT);
        // Assert
        assertEquals(result, VAL_INT);
    }

    @Test
    public void get_bool_returns_value() {
        // Act
        sharedPreferences.edit().putBoolean(KEY_BOOL, VAL_BOOL).apply();
        boolean result = preferenceLocal.get(KEY_BOOL, DEF_BOOL);
        // Assert
        assertEquals(result, VAL_BOOL);
    }

    @Test
    public void get_string_returns_value() {
        // ACT
        sharedPreferences.edit().putString(KEY_STR, VAL_STR).apply();
        String result = preferenceLocal.get(KEY_STR, DEF_STR);
        // ARRANGE
        assertEquals(result, VAL_STR);
    }
}

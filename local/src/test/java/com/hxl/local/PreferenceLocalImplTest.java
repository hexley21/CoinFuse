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
        // Arrange
        int result;
        // Act
        sharedPreferences.edit().putInt(KEY_INT, VAL_INT).apply();
        result = preferenceLocal.get(KEY_INT, DEF_INT);
        // Assert
        assertEquals(VAL_INT, result);
    }

    @Test
    public void get_bool_returns_value() {
        // Arrange
        boolean result;
        // Act
        sharedPreferences.edit().putBoolean(KEY_BOOL, VAL_BOOL).apply();
        result = preferenceLocal.get(KEY_BOOL, DEF_BOOL);
        // Assert
        assertEquals(VAL_BOOL, result);
    }

    @Test
    public void get_string_returns_value() {
        // Arrange
        String result;
        // Act
        sharedPreferences.edit().putString(KEY_STR, VAL_STR).apply();
        result = preferenceLocal.get(KEY_STR, DEF_STR);
        // Arrange
        assertEquals(VAL_STR, result);
    }

    @Test
    public void save_int_inserts_value() {
        // Arrange
        int result;
        // Act
        preferenceLocal.save(KEY_INT, VAL_INT);
        result = sharedPreferences.getInt(KEY_INT, DEF_INT);
        // Arrange
        assertEquals(VAL_INT, result);
    }

    @Test
    public void save_bool_inserts_value() {
        // Arrange
        boolean result;
        // Act
        preferenceLocal.save(KEY_BOOL, VAL_BOOL);
        result = sharedPreferences.getBoolean(KEY_BOOL, DEF_BOOL);
        // Arrange
        assertEquals(VAL_BOOL, result);
    }

    @Test
    public void save_string_inserts_value() {
        // Arrange
        String result;
        // Act
        preferenceLocal.save(KEY_STR, VAL_STR);
        result = sharedPreferences.getString(KEY_STR, DEF_STR);
        // Arrange
        assertEquals(VAL_STR, result);
    }
}

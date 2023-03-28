package com.hxl.data;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static com.hxl.data.DataTestConstants.*;

import com.hxl.data.repository.pref.PreferenceLocal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PreferenceRepositoryImplTest {

    @Mock
    PreferenceLocal preferenceLocal;

    @InjectMocks
    PreferenceRepositoryImpl repository;

    @Test
    public void get_int_returns_value() {
        // Arrange
        when(preferenceLocal.get(anyString(), anyInt())).thenReturn(VAL_INT);
        // Act
        int intPref = repository.get(KEY_INT, DEF_INT);
        // Assert
        assertEquals(VAL_INT, intPref);
        verify(preferenceLocal, times(1)).get(anyString(), anyInt());
    }

    @Test
    public void get_bool_returns_value() {
        // Arrange
        when(preferenceLocal.get(anyString(), anyBoolean())).thenReturn(VAL_BOOL);
        // Act
        boolean boolPref = repository.get(KEY_BOOL, DEF_BOOL);
        // Assert
        assertEquals(VAL_BOOL, boolPref);
        verify(preferenceLocal, times(1)).get(anyString(), anyBoolean());
    }

    @Test
    public void get_string_returns_value() {
        // Arrange
        when(preferenceLocal.get(anyString(), anyString())).thenReturn(VAL_STR);
        // Act
        String strPref = repository.get(KEY_STR, DEF_STR);
        // Assert
        assertEquals(VAL_STR, strPref);
        verify(preferenceLocal, times(1)).get(anyString(), anyString());
    }

    @Test
    public void save_int_inserts_value() {
        // Arrange
        doNothing().when(preferenceLocal).save(anyString(), anyInt());
        // Act
        repository.save(KEY_INT, DEF_INT);
        // Assert
        verify(preferenceLocal, times(1)).save(anyString(), anyInt());
    }

    @Test
    public void save_bool_inserts_value() {
        // Arrange
        doNothing().when(preferenceLocal).save(anyString(), anyBoolean());
        // Act
        repository.save(KEY_BOOL, DEF_BOOL);
        // Assert
        verify(preferenceLocal, times(1)).save(anyString(), anyBoolean());
    }

    @Test
    public void save_string_inserts_value() {
        // Arrange
        doNothing().when(preferenceLocal).save(anyString(), anyString());
        // Act
        repository.save(KEY_STR, DEF_STR);
        // Assert
        verify(preferenceLocal, times(1)).save(anyString(), anyString());
    }
}

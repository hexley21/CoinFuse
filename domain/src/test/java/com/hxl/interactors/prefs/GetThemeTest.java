package com.hxl.interactors.prefs;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.prefs.GetTheme;
import com.hxl.domain.model.PrefKeys;
import com.hxl.domain.repository.PreferenceRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetThemeTest {
    @Mock
    PreferenceRepository repository;
    @InjectMocks
    GetTheme interactor;

    @Test
    public void invokeReturnsThemeValueFromRepository() {
        // Arrange
        when(repository.get(anyString(), anyInt())).thenReturn(PrefKeys.THEME.def);
        // Act
        Integer theme = interactor.invoke();
        // Assert
        assertEquals(PrefKeys.THEME.def, theme);
        verify(repository).get(anyString(), anyInt());
    }
}

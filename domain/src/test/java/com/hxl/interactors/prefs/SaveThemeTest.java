package com.hxl.interactors.prefs;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.hxl.domain.interactors.prefs.SaveTheme;
import com.hxl.domain.model.PrefKeys;
import com.hxl.domain.repository.PreferenceRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SaveThemeTest {
    @Mock
    PreferenceRepository repository;
    @InjectMocks
    SaveTheme interactor;

    @Test
    public void invokeInsertsThemeValueToRepository() {
        // Arrange
        doNothing().when(repository).save(anyString(), anyInt());
        // Act
        interactor.invoke(PrefKeys.THEME.def);
        // Assert
        verify(repository).save(eq(PrefKeys.THEME.key), eq(PrefKeys.THEME.def));
    }
}

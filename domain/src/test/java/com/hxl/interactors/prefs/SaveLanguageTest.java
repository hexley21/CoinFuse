package com.hxl.interactors.prefs;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.hxl.domain.interactors.prefs.SaveLanguage;
import com.hxl.domain.model.PrefKeys;
import com.hxl.domain.repository.PreferenceRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SaveLanguageTest {

    @Mock
    PreferenceRepository repository;
    @InjectMocks
    SaveLanguage interactor;

    @Test
    public void invokeInsertsLanguageValueToRepository() {
        // Arrange
        doNothing().when(repository).save(anyString(), anyString());
        // Act
        interactor.invoke(PrefKeys.LANGUAGE.def);
        // Assert
        verify(repository).save(eq(PrefKeys.LANGUAGE.key), eq(PrefKeys.LANGUAGE.def));
    }
}

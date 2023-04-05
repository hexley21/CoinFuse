package com.hxl.interactors.prefs;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.prefs.GetLanguage;
import com.hxl.domain.model.PrefKeys;
import com.hxl.domain.repository.PreferenceRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetLanguageTest {
    @Mock
    PreferenceRepository repository;
    @InjectMocks
    GetLanguage interactor;

    @Test
    public void invokeReturnsLanguageValueFromRepository() {
        // Arrange
        when(repository.get(anyString(), anyString())).thenReturn(PrefKeys.LANGUAGE.def);
        // Act
        String language = interactor.invoke();
        // Assert
        assertEquals(PrefKeys.LANGUAGE.def, language);
        verify(repository).get(anyString(), anyString());
    }
}

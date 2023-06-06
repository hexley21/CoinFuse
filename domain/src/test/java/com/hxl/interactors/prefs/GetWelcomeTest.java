package com.hxl.interactors.prefs;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.prefs.GetWelcome;
import com.hxl.domain.model.PrefKeys;
import com.hxl.domain.repository.PreferenceRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetWelcomeTest {
    @Mock
    PreferenceRepository repository;
    @InjectMocks
    GetWelcome interactor;

    @Test
    public void invokeReturnsWelcomeValueFromRepository() {
        // Arrange
        when(repository.get(anyString(), anyBoolean())).thenReturn(PrefKeys.WELCOME.def);
        // Act
        Boolean welcome = interactor.invoke();
        // Assert
        assertEquals(PrefKeys.WELCOME.def, welcome);
        verify(repository).get(anyString(), anyBoolean());
    }
}

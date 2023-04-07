package com.hxl.interactors.prefs;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.hxl.domain.interactors.prefs.SaveWelcome;
import com.hxl.domain.model.PrefKeys;
import com.hxl.domain.repository.PreferenceRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SaveWelcomeTest {
    @Mock
    PreferenceRepository repository;
    @InjectMocks
    SaveWelcome interactor;

    @Test
    public void invokeInsertsWelcomeValueToRepository() {
        // Arrange
        doNothing().when(repository).save(anyString(), anyBoolean());
        // Act
        interactor.invoke(PrefKeys.WELCOME.def);
        // Assert
        verify(repository).save(eq(PrefKeys.WELCOME.key), eq(PrefKeys.WELCOME.def));
    }
}

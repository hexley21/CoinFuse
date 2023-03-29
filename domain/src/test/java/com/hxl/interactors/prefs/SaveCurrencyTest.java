package com.hxl.interactors.prefs;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.hxl.domain.interactors.prefs.SaveCurrency;
import com.hxl.domain.model.PrefKeys;
import com.hxl.domain.repository.PreferenceRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SaveCurrencyTest {

    @Mock
    PreferenceRepository repository;
    @InjectMocks
    SaveCurrency interactor;

    @Test
    public void invokeInsertsCurrencyValueToRepository() {
        // Arrange
        doNothing().when(repository).save(anyString(), anyString());
        // Act
        interactor.invoke(PrefKeys.CURRENCY.def);
        // Assert
        verify(repository).save(eq(PrefKeys.CURRENCY.key), eq(PrefKeys.CURRENCY.def));
    }
}

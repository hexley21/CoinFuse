package com.hxl.interactors.prefs;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.prefs.GetCurrency;
import com.hxl.domain.model.PrefKeys;
import com.hxl.domain.repository.PreferenceRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetCurrencyTest {
    @Mock
    PreferenceRepository repository;
    @InjectMocks
    GetCurrency interactor;

    @Test
    public void invokeReturnsCurrencyValueFromRepository() {
        // Arrange
        when(repository.get(anyString(), anyString())).thenReturn(PrefKeys.CURRENCY.def);
        // Act
        String currency = interactor.invoke();
        // Assert
        assertEquals(PrefKeys.CURRENCY.def, currency);
        verify(repository).get(anyString(), anyString());
    }
}

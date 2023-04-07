package com.hxl.presentation.viewmodels;

import static com.hxl.presentation.fakes.AppTestConstants.DEF_BOOL;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.prefs.GetWelcome;
import com.hxl.domain.interactors.prefs.SaveWelcome;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class WelcomeViewModelTest {

    @Mock
    GetWelcome getWelcome;
    @Mock
    SaveWelcome saveWelcome;
    @InjectMocks
    WelcomeViewModel viewModel;

    @Test
    public void getWelcomeReturnsPreferenceFromInteractor() {
        // Arrange
        when(getWelcome.invoke()).thenReturn(DEF_BOOL);
        // Act
        boolean viewModelWelcome =  viewModel.getWelcome.invoke();
        // Assert
        assertEquals(DEF_BOOL, viewModelWelcome);
        verify(getWelcome).invoke();
    }

    @Test
    public void saveWelcomeInsertsPreferenceFromInteractor() {
        // Arrange
        doNothing().when(saveWelcome).invoke(anyBoolean());
        // Act
        saveWelcome.invoke(DEF_BOOL);
        // Assert
        verify(saveWelcome).invoke(anyBoolean());
    }
}

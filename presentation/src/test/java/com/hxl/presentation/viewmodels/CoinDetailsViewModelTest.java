package com.hxl.presentation.viewmodels;

import static com.hxl.presentation.fakes.PresentationTestConstants.ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.hxl.domain.interactors.coins.IsCoinBookmarked;
import com.hxl.presentation.LogcatSetup;
import com.hxl.presentation.livedata.DataState;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class CoinDetailsViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @ClassRule
    public static LogcatSetup logcatSetup = new LogcatSetup();

    @Mock
    private IsCoinBookmarked isCoinBookmarked;

    @InjectMocks
    private CoinDetailsViewModel viewModel;

    private final Exception exception = new Exception("Fake error");


    @Test
    public void fetchBookmarkStateSuccess() {
        // Arrange
        when(isCoinBookmarked.invoke(anyString())).thenReturn(Single.just(true));
        // Act
        viewModel.fetchBookmarkState(ID);
        // Assert
        assertNotNull(viewModel.getCurrentBookmarkState().getValue());
        assertEquals(DataState.SUCCESS, viewModel.getCurrentBookmarkState().getValue().getState());
        assertTrue(viewModel.getCurrentBookmarkState().getValue().getData());

        verify(isCoinBookmarked).invoke(ID);
    }

    @Test
    public void fetchBookmarkStateError() {
        // Arrange
        when(isCoinBookmarked.invoke(anyString())).thenReturn(Single.error(exception));
        // Act
        viewModel.fetchBookmarkState(ID);
        // Assert
        assertNotNull(viewModel.getCurrentBookmarkState().getValue());
        assertEquals(DataState.ERROR, viewModel.getCurrentBookmarkState().getValue().getState());
        assertEquals(exception, viewModel.getCurrentBookmarkState().getValue().getError());

        verify(isCoinBookmarked).invoke(ID);
    }

}
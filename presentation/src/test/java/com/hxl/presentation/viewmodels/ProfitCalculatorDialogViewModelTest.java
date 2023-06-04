package com.hxl.presentation.viewmodels;

import static com.hxl.presentation.fakes.FakeDataFactory.getFakeCoins;
import static com.hxl.presentation.fakes.PresentationTestConstants.ID;
import static com.hxl.presentation.fakes.PresentationTestConstants.SIZE;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.hxl.domain.interactors.coins.SearchCoins;
import com.hxl.domain.model.Coin;
import com.hxl.presentation.ViewModelSetup;
import com.hxl.presentation.livedata.DataState;
import com.hxl.presentation.livedata.StateLiveData;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class ProfitCalculatorDialogViewModelTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @ClassRule
    public static final ViewModelSetup viewModelSetup = new ViewModelSetup();

    @Mock
    private SearchCoins searchCoins;

    @InjectMocks
    private ProfitCalculatorDialogViewModel viewModel;

    private final Exception exception = new Exception("Fake error");

    @After
    public void tearDown() {
        viewModel.onCleared();
    }

    @Test
    public void fetchSearchSuccess() {
        // Arrange
        final List<Coin> fakeCoins = getFakeCoins(SIZE);
        when(searchCoins.invoke(anyString())).thenReturn(Single.just(fakeCoins));
        // Act
        viewModel.fetchSearch(ID);
        final StateLiveData<List<Coin>> getCurrentSearch = viewModel.getCurrentSearch();
        // Assert
        assertNotNull(getCurrentSearch.getValue());
        assertEquals(DataState.SUCCESS, getCurrentSearch.getValue().getState());
        assertEquals(fakeCoins, getCurrentSearch.getValue().getData());
    }

    @Test
    public void fetchSearchError() {
        // Arrange
        when(searchCoins.invoke(anyString())).thenReturn(Single.error(exception));
        // Act
        viewModel.fetchSearch(ID);
        final StateLiveData<List<Coin>> getCurrentSearch = viewModel.getCurrentSearch();
        // Assert
        assertNotNull(getCurrentSearch.getValue());
        assertEquals(DataState.ERROR, getCurrentSearch.getValue().getState());
        assertEquals(exception, getCurrentSearch.getValue().getError());
    }
}
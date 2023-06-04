package com.hxl.presentation.viewmodels;

import static com.hxl.presentation.fakes.FakeDataFactory.getFakeCoins;
import static com.hxl.presentation.fakes.PresentationTestConstants.ID;
import static com.hxl.presentation.fakes.PresentationTestConstants.SIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.hxl.domain.interactors.coins.GetCoinsBySearchHistory;
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
public class CoinsMenuViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @ClassRule
    public static ViewModelSetup viewModelSetup = new ViewModelSetup();

    @Mock
    private SearchCoins searchCoins;
    @Mock
    private GetCoinsBySearchHistory coinsBySearchHistory;

    @InjectMocks
    private CoinsMenuViewModel viewModel;

    private final Exception exception = new Exception("Fake error");

    @After
    public void tearDown() {
        viewModel.onCleared();
    }

    @Test
    public void fetchCoinSearchSuccess() {
        // Arrange
        final List<Coin> fakeCoins = getFakeCoins(SIZE);
        when(searchCoins.invoke(anyString())).thenReturn(Single.just(fakeCoins));
        // Act
        viewModel.fetchCoinSearch(ID);
        final StateLiveData<List<Coin>> getCurrentCoinSearch = viewModel.getCurrentCoinSearch();
        // Assert
        assertNotNull(getCurrentCoinSearch.getValue());
        assertEquals(DataState.SUCCESS, getCurrentCoinSearch.getValue().getState());
        assertEquals(fakeCoins, getCurrentCoinSearch.getValue().getData());
    }

    @Test
    public void fetchCoinSearchError() {
        // Arrange
        when(searchCoins.invoke(anyString())).thenReturn(Single.error(exception));
        // Act
        viewModel.fetchCoinSearch(ID);
        final StateLiveData<List<Coin>> getCurrentCoinSearch = viewModel.getCurrentCoinSearch();
        // Assert
        assertNotNull(getCurrentCoinSearch.getValue());
        assertEquals(DataState.ERROR, getCurrentCoinSearch.getValue().getState());
        assertEquals(exception, getCurrentCoinSearch.getValue().getError());
    }

    @Test
    public void fetchCoinSearchHistorySuccess() {
        // Arrange
        final List<Coin> fakeCoins = getFakeCoins(SIZE);
        when(coinsBySearchHistory.invoke()).thenReturn(Single.just(fakeCoins));
        // Act
        viewModel.fetchCoinSearchHistory();
        final StateLiveData<List<Coin>> getCurrentCoinsSearchHistory = viewModel.getCurrentCoinsSearchHistory();
        // Assert
        assertNotNull(getCurrentCoinsSearchHistory.getValue());
        assertEquals(DataState.SUCCESS, getCurrentCoinsSearchHistory.getValue().getState());
        assertEquals(fakeCoins, getCurrentCoinsSearchHistory.getValue().getData());
    }

    @Test
    public void fetchCoinSearchHistoryError() {
        // Arrange
        when(coinsBySearchHistory.invoke()).thenReturn(Single.error(exception));
        // Act
        viewModel.fetchCoinSearchHistory();
        final StateLiveData<List<Coin>> getCurrentCoinsSearchHistory = viewModel.getCurrentCoinsSearchHistory();
        // Assert
        assertNotNull(getCurrentCoinsSearchHistory.getValue());
        assertEquals(DataState.ERROR, getCurrentCoinsSearchHistory.getValue().getState());
        assertEquals(exception, getCurrentCoinsSearchHistory.getValue().getError());
    }

}
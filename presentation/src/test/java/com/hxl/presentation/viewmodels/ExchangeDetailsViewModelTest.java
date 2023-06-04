package com.hxl.presentation.viewmodels;

import static com.hxl.presentation.fakes.FakeDataFactory.getExchange;
import static com.hxl.presentation.fakes.FakeDataFactory.getFakeTrades;
import static com.hxl.presentation.fakes.PresentationTestConstants.ID;
import static com.hxl.presentation.fakes.PresentationTestConstants.SIZE;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.hxl.domain.interactors.exchanges.GetExchange;
import com.hxl.domain.interactors.exchanges.GetTrades;
import com.hxl.domain.model.Exchange;
import com.hxl.domain.model.Trade;
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

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeDetailsViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @ClassRule
    public static ViewModelSetup viewModelSetup = new ViewModelSetup();

    @Mock
    private GetExchange getExchange;
    @Mock
    private GetTrades getTrades;

    @InjectMocks
    private ExchangeDetailsViewModel viewModel;

    private final Exception exception = new Exception("Fake error");

    @After
    public void tearDown() {
        viewModel.onCleared();
    }

    @Test
    public void fetchExchangeSuccess() {
        // Arrange
        Exchange fakeExchange = getExchange();
        when(getExchange.invoke(anyString())).thenReturn(Single.just(fakeExchange));
        // Act
        viewModel.fetchExchange(ID);
        final StateLiveData<Exchange> getCurrentExchange = viewModel.getCurrentExchange();
        // Assert
        assertNotNull(getCurrentExchange.getValue());
        assertEquals(DataState.SUCCESS, getCurrentExchange.getValue().getState());
        assertEquals(fakeExchange, getCurrentExchange.getValue().getData());
    }

    @Test
    public void fetchExchangeError() {
        // Arrange
        when(getExchange.invoke(anyString())).thenReturn(Single.error(exception));
        // Act
        viewModel.fetchExchange(ID);
        final StateLiveData<Exchange> getCurrentExchange = viewModel.getCurrentExchange();
        // Assert
        assertNotNull(getCurrentExchange.getValue());
        assertEquals(DataState.ERROR, getCurrentExchange.getValue().getState());
        assertEquals(exception, getCurrentExchange.getValue().getError());
    }

    @Test
    public void fetchTradesSuccess() {
        // Arrange
        final List<Trade> fakeTrades = getFakeTrades(SIZE);
        when(getTrades.invoke(anyMap())).thenReturn(Single.just(fakeTrades));
        // Act
        viewModel.fetchTrades(new HashMap<>());
        final StateLiveData<List<Trade>> getCurrentTrades = viewModel.getCurrentTrades();
        // Assert
        assertNotNull(getCurrentTrades.getValue());
        assertEquals(DataState.SUCCESS, getCurrentTrades.getValue().getState());
        assertEquals(fakeTrades, getCurrentTrades.getValue().getData());
    }

    @Test
    public void fetchTradesError() {
        // Arrange
        when(getTrades.invoke(anyMap())).thenReturn(Single.error(exception));
        // Act
        viewModel.fetchTrades(new HashMap<>());
        final StateLiveData<List<Trade>> getCurrentTrades = viewModel.getCurrentTrades();
        // Assert
        assertNotNull(getCurrentTrades.getValue());
        assertEquals(DataState.ERROR, getCurrentTrades.getValue().getState());
        assertEquals(exception, getCurrentTrades.getValue().getError());
    }
}
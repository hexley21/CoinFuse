package com.hxl.presentation.viewmodels;

import static com.hxl.presentation.fakes.FakeDataFactory.getTrade;
import static com.hxl.presentation.fakes.PresentationTestConstants.ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.hxl.domain.interactors.coins.GetTradesByCoin;
import com.hxl.domain.model.Trade;
import com.hxl.presentation.ViewModelSetup;
import com.hxl.presentation.livedata.DataState;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class CoinExchangesViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @ClassRule
    public static ViewModelSetup viewModelSetup = new ViewModelSetup();

    @Mock
    private GetTradesByCoin getTradesByCoin;

    @InjectMocks
    private CoinExchangesViewModel viewModel;

    private final Exception exception = new Exception("Fake error");

    @After
    public void tearDown() {
        viewModel.onCleared();
    }

    @Test
    public void fetchTradesSuccess() {
        // Arrange
        List<Trade> fakeTrades = new ArrayList<>();
        fakeTrades.add(getTrade());
        fakeTrades.add(getTrade());
        when(getTradesByCoin.invoke(anyString())).thenReturn(Single.just(fakeTrades));
        // Act
        viewModel.fetchTrades(ID);
        // Assert
        assertNotNull(viewModel.getCurrentTrades().getValue());
        assertEquals(DataState.SUCCESS, viewModel.getCurrentTrades().getValue().getState());
        assertEquals(fakeTrades, viewModel.getCurrentTrades().getValue().getData());

        verify(getTradesByCoin).invoke(ID);

    }

    @Test
    public void fetchTradesError() {
        // Arrange
        when(getTradesByCoin.invoke(anyString())).thenReturn(Single.error(exception));
        // Act
        viewModel.fetchTrades(ID);
        // Assert
        assertNotNull(viewModel.getCurrentTrades().getValue());
        assertEquals(DataState.ERROR, viewModel.getCurrentTrades().getValue().getState());
        assertEquals(exception, viewModel.getCurrentTrades().getValue().getError());

        verify(getTradesByCoin).invoke(ID);

    }
}

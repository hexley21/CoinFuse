package com.hxl.presentation.viewmodels;

import static com.hxl.presentation.fakes.FakeDataFactory.getCoin;
import static com.hxl.presentation.fakes.FakeDataFactory.getFakeHistory;
import static com.hxl.presentation.fakes.PresentationTestConstants.ID;
import static com.hxl.presentation.fakes.PresentationTestConstants.SIZE;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.hxl.domain.interactors.coins.GetCoin;
import com.hxl.domain.interactors.coins.GetCoinPriceHistory;
import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;
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
public class CoinPriceViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @ClassRule
    public static ViewModelSetup viewModelSetup = new ViewModelSetup();

    @Mock
    private GetCoin getCoin;
    @Mock
    private GetCoinPriceHistory getCoinPriceHistory;

    @InjectMocks
    private CoinPriceViewModel viewModel;

    private final Exception exception = new Exception("Fake error");

    @After
    public void tearDown(){
        viewModel.onCleared();
    }
    @Test
    public void fetchCoinSuccess() {
        // Arrange
        final Coin fakeCoin = getCoin();
        when(getCoin.invoke(anyString())).thenReturn(Single.just(fakeCoin));
        // Act
        viewModel.fetchCoin(ID);
        final StateLiveData<Coin> getCurrentCoin = viewModel.getCurrentCoin();
        // Assert
        assertNotNull(getCurrentCoin.getValue());
        assertEquals(DataState.SUCCESS, getCurrentCoin.getValue().getState());
        assertEquals(fakeCoin, getCurrentCoin.getValue().getData());
    }

    @Test
    public void fetchCoinError() {
        // Arrange
        when(getCoin.invoke(anyString())).thenReturn(Single.error(exception));
        // Act
        viewModel.fetchCoin(ID);
        final StateLiveData<Coin> getCurrentCoin = viewModel.getCurrentCoin();
        // Assert
        assertNotNull(getCurrentCoin.getValue());
        assertEquals(DataState.ERROR, getCurrentCoin.getValue().getState());
        assertEquals(exception, getCurrentCoin.getValue().getError());
    }


    @Test
    public void fetchPriceHistorySuccess() {
        // Arrange
        final List<CoinPriceHistory> fakePrices = getFakeHistory(SIZE);
        when(getCoinPriceHistory.invoke(anyString(), any(CoinPriceHistory.Interval.class))).thenReturn(Single.just(fakePrices));
        // Act
        viewModel.fetchPriceHistory(ID, CoinPriceHistory.Interval.D1);
        final StateLiveData<List<CoinPriceHistory>> getCurrentPriceHistory = viewModel.getCurrentCoinPriceHistory();
        // Assert
        assertNotNull(getCurrentPriceHistory.getValue());
        assertEquals(DataState.SUCCESS, getCurrentPriceHistory.getValue().getState());
        assertEquals(fakePrices, getCurrentPriceHistory.getValue().getData());
    }

    @Test
    public void fetchPriceHistoryError() {
        // Arrange
        when(getCoinPriceHistory.invoke(anyString(), any(CoinPriceHistory.Interval.class))).thenReturn(Single.error(exception));
        // Act
        viewModel.fetchPriceHistory(ID, CoinPriceHistory.Interval.D1);
        final StateLiveData<List<CoinPriceHistory>> getCurrentPriceHistory = viewModel.getCurrentCoinPriceHistory();
        // Assert
        assertNotNull(getCurrentPriceHistory.getValue());
        assertEquals(DataState.ERROR, getCurrentPriceHistory.getValue().getState());
        assertEquals(exception, getCurrentPriceHistory.getValue().getError());
    }
}
package com.hxl.presentation.viewmodels;

import static com.hxl.presentation.fakes.FakeDataFactory.getTrade;
import static com.hxl.presentation.fakes.PresentationTestConstants.ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.hxl.domain.interactors.coins.GetTradesByCoin;
import com.hxl.domain.model.Trade;
import com.hxl.presentation.livedata.DataState;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class CoinExchangesViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private GetTradesByCoin getTradesByCoin;

    @InjectMocks
    private CoinExchangesViewModel viewModel;

    @SuppressLint("CheckResult")
    @BeforeClass
    public static void classSetUp() {
        mockStatic(Log.class);
        doAnswer(invocation -> {
            String tag = invocation.getArgument(0);
            String message = invocation.getArgument(1);
            Throwable throwable = invocation.getArgument(2);
            System.out.println(tag + ": " + message);
            throwable.printStackTrace();
            return 0;
        }).when(Log.class);
        Log.e(anyString(), anyString(), any(Throwable.class));

        Scheduler immediateScheduler = Schedulers.trampoline();
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediateScheduler);
    }

    @Test
    public void fetchTrades_success() {
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
    public void fetchTrades_error() {
        // Arrange
        Throwable fakeError = new Exception();
        when(getTradesByCoin.invoke(anyString())).thenReturn(Single.error(fakeError));
        // Act
        viewModel.fetchTrades(ID);
        // Assert
        assertNotNull(viewModel.getCurrentTrades().getValue());
        assertEquals(DataState.ERROR, viewModel.getCurrentTrades().getValue().getState());
        assertEquals(fakeError, viewModel.getCurrentTrades().getValue().getError());

        verify(getTradesByCoin).invoke(ID);

    }
}

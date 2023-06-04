package com.hxl.presentation.viewmodels;

import static com.hxl.presentation.fakes.FakeDataFactory.getFakeExchanges;
import static com.hxl.presentation.fakes.PresentationTestConstants.SIZE;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.hxl.domain.interactors.exchanges.GetExchanges;
import com.hxl.domain.model.Exchange;
import com.hxl.presentation.OrderBy;
import com.hxl.presentation.ViewModelSetup;
import com.hxl.presentation.exchange.ExchangeComparatorFactory;
import com.hxl.presentation.exchange.ExchangeSortBy;
import com.hxl.presentation.livedata.DataState;
import com.hxl.presentation.livedata.StateLiveData;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class ExchangesViewModelTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @ClassRule
    public static final ViewModelSetup viewModelSetup = new ViewModelSetup();

    @Mock
    private GetExchanges getExchanges;
    @InjectMocks
    private ExchangesViewModel viewModel;

    private final Exception exception = new Exception("Fake error");

    @After
    public void tearDown() {
        viewModel.onCleared();
    }

    @Test
    public void fetchExchangesSuccess() {
        // Arrange
        final List<Exchange> fakeExchanges = getFakeExchanges(SIZE);
        final List<Exchange> sortedExchanges = fakeExchanges.stream()
                .sorted(ExchangeComparatorFactory.createComparator(ExchangeSortBy.NAME, OrderBy.DESC))
                .collect(Collectors.toList());
        when(getExchanges.invoke()).thenReturn(Single.just(fakeExchanges));
        // Act
        viewModel.fetchExchanges(ExchangeSortBy.NAME, OrderBy.DESC);
        final StateLiveData<List<Exchange>> getCurrentExchanges = viewModel.getCurrentExchanges();
        // Assert
        assertNotNull(getCurrentExchanges.getValue());
        assertEquals(DataState.SUCCESS, getCurrentExchanges.getValue().getState());
        assertEquals(sortedExchanges, getCurrentExchanges.getValue().getData());
    }


    @Test
    public void fetchExchangesError() {
        // Arrange
        when(getExchanges.invoke()).thenReturn(Single.error(exception));
        // Act
        viewModel.fetchExchanges(ExchangeSortBy.NAME, OrderBy.DESC);
        final StateLiveData<List<Exchange>> getCurrentExchanges = viewModel.getCurrentExchanges();
        // Assert
        assertNotNull(getCurrentExchanges.getValue());
        assertEquals(DataState.ERROR, getCurrentExchanges.getValue().getState());
        assertEquals(exception, getCurrentExchanges.getValue().getError());
    }
}
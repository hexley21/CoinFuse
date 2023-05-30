package com.hxl.data;

import static com.hxl.data.fakes.DataTestConstants.ID;
import static com.hxl.data.fakes.DataTestConstants.LIMIT;
import static com.hxl.data.fakes.DataTestConstants.OFFSET;
import static com.hxl.data.fakes.DataTestConstants.SIZE;
import static com.hxl.data.fakes.FakeDataFactory.emptyList;
import static com.hxl.data.fakes.FakeDataFactory.getExchange;
import static com.hxl.data.fakes.FakeDataFactory.getFakeExchanges;
import static com.hxl.data.fakes.FakeDataFactory.getFakeTrades;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.data.fakes.FakeDataFactory;
import com.hxl.data.repository.exchange.ExchangeLocal;
import com.hxl.data.repository.exchange.ExchangeRemote;
import com.hxl.domain.model.Exchange;
import com.hxl.domain.model.Trade;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRepositoryTest {

    @Mock
    private ExchangeRemote exchangeRemote;
    @Mock
    private ExchangeLocal exchangeLocal;
    @InjectMocks
    private ExchangeRepositoryImpl repository;

    private final UnknownHostException unknownHostException = new UnknownHostException("No internet connection");

    // region getExchanges()
    @Test
    public void getExchangesReturnsExchangesFromRemote() {
        // Arrange
        when(exchangeRemote.getExchanges()).thenReturn(Single.just(getFakeExchanges(SIZE)));
        when(exchangeLocal.getExchanges()).thenReturn(Single.just(emptyList()));
        when(exchangeLocal.insertExchange(any(Exchange[].class))).thenReturn(Completable.complete());
        // Act
        final Single<List<Exchange>> getExchanges = repository.getExchanges();
        // Assert
        getExchanges.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);

        verify(exchangeRemote, times(1)).getExchanges();
        verify(exchangeLocal, times(1)).getExchanges();
        verify(exchangeLocal, times(1)).insertExchange(any(Exchange[].class));
    }

    @Test
    public void getExchangesReturnsExchangesFromLocal() {
        // Arrange
        when(exchangeRemote.getExchanges()).thenReturn(Single.error(unknownHostException));
        when(exchangeLocal.getExchanges()).thenReturn(Single.just(getFakeExchanges(SIZE)));
        // Act
        final Single<List<Exchange>> getExchanges = repository.getExchanges();
        // Assert
        getExchanges.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);

        verify(exchangeRemote, times(1)).getExchanges();
        verify(exchangeLocal, times(1)).getExchanges();
        verify(exchangeLocal, never()).insertExchange(any(Exchange[].class));
    }

    @Test
    public void getExchangesReturnsExceptionOnNoData() {
        // Arrange
        when(exchangeRemote.getExchanges()).thenReturn(Single.error(unknownHostException));
        when(exchangeLocal.getExchanges()).thenReturn(Single.just(emptyList()));
        // Act
        final Single<List<Exchange>> getExchanges = repository.getExchanges();
        // Assert
        getExchanges.test()
                .awaitCount(1)
                .assertError(UnknownHostException.class);

        verify(exchangeRemote, times(1)).getExchanges();
        verify(exchangeLocal, times(1)).getExchanges();
        verify(exchangeLocal, never()).insertExchange(any(Exchange[].class));
    }
    // endregion

    // region getExchanges(int limit, int offset)
    @Test
    public void getExchangesWithLimitAndOffsetReturnsExchangesFromRemote() {
        // Arrange
        List<Exchange> fakeExchanges = getFakeExchanges(SIZE);
        when(exchangeRemote.getExchanges(anyInt(), anyInt())).thenReturn(Single.just(fakeExchanges));
        when(exchangeLocal.getExchanges(anyInt(), anyInt())).thenReturn(Single.just(fakeExchanges));
        when(exchangeLocal.insertExchange(any(Exchange[].class))).thenReturn(Completable.complete());
        // Act
        final Single<List<Exchange>> getExchanges = repository.getExchanges(LIMIT, OFFSET);
        // Assert
        getExchanges.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);

        verify(exchangeRemote, times(1)).getExchanges(anyInt(), anyInt());
        verify(exchangeLocal, times(1)).getExchanges(anyInt(), anyInt());
        verify(exchangeLocal, times(1)).insertExchange(any(Exchange[].class));
    }

    @Test
    public void getExchangesWithLimitAndOffsetReturnsExchangesFromLocal() {
        // Arrange
        when(exchangeRemote.getExchanges(anyInt(), anyInt())).thenReturn(Single.error(unknownHostException));
        when(exchangeLocal.getExchanges(anyInt(), anyInt())).thenReturn(Single.just(getFakeExchanges(SIZE)));
        // Act
        final Single<List<Exchange>> getExchanges = repository.getExchanges(LIMIT, OFFSET);
        // Assert
        getExchanges.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);

        verify(exchangeRemote, times(1)).getExchanges(anyInt(), anyInt());
        verify(exchangeLocal, times(1)).getExchanges(anyInt(), anyInt());
        verify(exchangeLocal, never()).insertExchange(any(Exchange[].class));
    }

    @Test
    public void getExchangesWithLimitAndOffsetReturnsExceptionOnNoData() {
        // Arrange
        when(exchangeRemote.getExchanges(anyInt(), anyInt())).thenReturn(Single.error(unknownHostException));
        when(exchangeLocal.getExchanges(anyInt(), anyInt())).thenReturn(Single.just(emptyList()));
        // Act
        final Single<List<Exchange>> getExchanges = repository.getExchanges(LIMIT, OFFSET);
        // Assert
        getExchanges.test()
                .awaitCount(1)
                .assertError(UnknownHostException.class);

        verify(exchangeRemote, times(1)).getExchanges(anyInt(), anyInt());
        verify(exchangeLocal, times(1)).getExchanges(anyInt(), anyInt());
        verify(exchangeLocal, never()).insertExchange(any(Exchange[].class));
    }
    // endregion

    // region getExchange(String exchangeId)
    @Test
    public void getExchangeReturnsExchangeFromRemote() {
        // Arrange
        Exchange fakeExchange = FakeDataFactory.getExchange(ID);

        when(exchangeRemote.getExchange(anyString())).thenReturn(Single.just(fakeExchange));
        when(exchangeLocal.getExchange(anyString())).thenReturn(Single.just(fakeExchange));
        when(exchangeLocal.insertExchange(any(Exchange[].class))).thenReturn(Completable.complete());
        // Act
        final Single<Exchange> getExchange = repository.getExchange(ID);
        // Assert
        getExchange.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.exchangeId.equals(fakeExchange.exchangeId));

        verify(exchangeRemote, times(1)).getExchange(anyString());
        verify(exchangeLocal, times(1)).getExchange(anyString());
        verify(exchangeLocal, times(1)).insertExchange(any(Exchange[].class));
    }

    @Test
    public void getExchangeReturnsExchangeFromLocal() {
        // Arrange
        Exchange fakeExchange = FakeDataFactory.getExchange(ID);

        when(exchangeRemote.getExchange(anyString())).thenReturn(Single.error(unknownHostException));
        when(exchangeLocal.getExchange(anyString())).thenReturn(Single.just(fakeExchange));
        // Act
        final Single<Exchange> getExchange = repository.getExchange(ID);
        // Assert
        getExchange.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.exchangeId.equals(fakeExchange.exchangeId));

        verify(exchangeRemote, times(1)).getExchange(anyString());
        verify(exchangeLocal, times(1)).getExchange(anyString());
        verify(exchangeLocal, never()).insertExchange(any(Exchange[].class));
    }
    // endregion

    // region getTrades(Map<String, String> queryMap)
    @Test
    public void getTradesReturnsTradesFromRemote() {
        // Arrange
        when(exchangeRemote.getTrades(anyMap())).thenReturn(Single.just(getFakeTrades(SIZE)));
        // Act
        final Single<List<Trade>> getTrades = repository.getTrades(new HashMap<>());
        // Assert
        getTrades.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);

        verify(exchangeRemote, times(1)).getTrades(new HashMap<>());
    }
    // endregion

    // region eraseExchanges()
    @Test
    public void eraseExchangesReturnsCompletableFromLocalSource() {
        // Arrange
        when(exchangeLocal.eraseExchanges()).thenReturn(Completable.complete());
        // Act
        final Completable eraseExchanges = repository.eraseExchanges();
        // Assert
        eraseExchanges.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();

        verify(exchangeLocal, times(1)).eraseExchanges();
    }
    // endregion

}

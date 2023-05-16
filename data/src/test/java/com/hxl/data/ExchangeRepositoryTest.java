package com.hxl.data;

import static com.hxl.data.fakes.DataTestConstants.ID;
import static com.hxl.data.fakes.DataTestConstants.LIMIT;
import static com.hxl.data.fakes.DataTestConstants.OFFSET;
import static com.hxl.data.fakes.DataTestConstants.SIZE;
import static com.hxl.data.fakes.FakeDataFactory.getExchange;
import static com.hxl.data.fakes.FakeDataFactory.getFakeExchanges;
import static com.hxl.data.fakes.FakeDataFactory.getFakeTrades;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.data.fakes.FakeDataFactory;
import com.hxl.data.source.exchange.ExchangeLocalSource;
import com.hxl.data.source.exchange.ExchangeRemoteSource;
import com.hxl.data.source.exchange.ExchangeSourceFactory;
import com.hxl.domain.model.Exchange;
import com.hxl.domain.model.Trade;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;


import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRepositoryTest {

    @Mock
    private ExchangeRemoteSource exchangeRemote;
    @Mock
    private ExchangeLocalSource exchangeLocal;
    private ExchangeSourceFactory sourceFactory;
    private ExchangeRepositoryImpl repository;

    @Before
    public void setUp() {
        sourceFactory = spy(new ExchangeSourceFactory(exchangeRemote, exchangeLocal));
        repository = spy(new ExchangeRepositoryImpl(sourceFactory));
    }

    // region getExchanges()
    @Test
    public void getExchangesReturnsExchangesFromRemote() {
        // Arrange
        List<Exchange> fakeExchanges = getFakeExchanges(SIZE);
        // Act
        when(sourceFactory.getDataSource(anyBoolean())).thenReturn(exchangeRemote);
        when(exchangeRemote.getExchanges()).thenReturn(Single.just(fakeExchanges));
        // Assert
        repository.getExchanges().test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);

        verify(exchangeRemote, times(1)).getExchanges();
        verify(exchangeLocal, never()).getExchanges();
    }

    @Test
    public void getExchangesReturnsExchangesFromLocal() {
        // Arrange
        List<Exchange> fakeExchanges = getFakeExchanges(SIZE);
        // Act
        when(sourceFactory.getDataSource(anyBoolean())).thenReturn(exchangeLocal);
        when(exchangeLocal.getExchanges()).thenReturn(Single.just(fakeExchanges));
        // Assert
        repository.getExchanges().test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);

        verify(exchangeLocal, times(1)).getExchanges();
        verify(exchangeRemote, never()).getExchanges();
    }
    // endregion

    // region getExchanges(int limit, int offset)
    @Test
    public void getExchangesWithLimitAndOffsetReturnsExchangesFromRemote() {
        // Arrange
        List<Exchange> fakeExchanges = getFakeExchanges(SIZE);
        // Act
        when(sourceFactory.getDataSource(anyBoolean())).thenReturn(exchangeRemote);
        when(exchangeRemote.getExchanges(anyInt(), anyInt())).thenReturn(Single.just(fakeExchanges));
        // Assert
        repository.getExchanges(LIMIT, OFFSET).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(exchangeRemote, times(1)).getExchanges(LIMIT, OFFSET);
        verify(exchangeLocal, never()).getExchanges(LIMIT, OFFSET);
    }

    @Test
    public void getExchangesWithLimitAndOffsetReturnsExchangesFromLocal() {
        // Arrange
        List<Exchange> fakeExchanges = getFakeExchanges(SIZE);
        // Act
        when(sourceFactory.getDataSource(anyBoolean())).thenReturn(exchangeLocal);
        when(exchangeLocal.getExchanges(anyInt(), anyInt())).thenReturn(Single.just(fakeExchanges));
        // Assert
        repository.getExchanges(LIMIT, OFFSET).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(exchangeLocal, times(1)).getExchanges(LIMIT, OFFSET);
        verify(exchangeRemote, never()).getExchanges(LIMIT, OFFSET);
    }
    // endregion

    // region getExchange(String exchangeId)
    @Test
    public void getExchangeReturnsExchangeFromRemote() {
        // Arrange
        Exchange fakeExchange = FakeDataFactory.getExchange(ID);
        // Act
        when(sourceFactory.getDataSource(anyBoolean())).thenReturn(exchangeRemote);
        when(exchangeRemote.getExchange(anyString())).thenReturn(Single.just(fakeExchange));
        // Assert
        repository.getExchange(ID).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.exchangeId.equals(fakeExchange.exchangeId));

        verify(exchangeRemote, times(1)).getExchange(ID);
        verify(exchangeLocal, never()).getExchange(ID);
    }

    @Test
    public void getExchangeReturnsExchangeFromLocal() {
        // Arrange
        Exchange fakeExchange = FakeDataFactory.getExchange(ID);
        // Act
        when(sourceFactory.getDataSource(anyBoolean())).thenReturn(exchangeLocal);
        when(exchangeLocal.getExchange(anyString())).thenReturn(Single.just(fakeExchange));
        // Assert
        repository.getExchange(ID).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.exchangeId.equals(fakeExchange.exchangeId));

        verify(exchangeLocal, times(1)).getExchange(ID);
        verify(exchangeRemote, never()).getExchange(ID);
    }
    // endregion

    // region getTrades(Map<String, String> queryMap)
    @Test
    public void getTradesReturnsTradesFromRemote() {
        // Arrange
        List<Trade> fakeTrades = getFakeTrades(SIZE);
        // Act
        when(repository.getTrades(anyMap())).thenReturn(Single.just(fakeTrades));
        // Assert
        repository.getTrades(new HashMap<>()).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(sourceFactory, times(1)).getRemote();
        verify(sourceFactory, never()).getDataSource(true);
        verify(sourceFactory, never()).getLocal();
        verify(exchangeRemote, times(1)).getTrades(new HashMap<>());
        verify(exchangeLocal, never()).getTrades(new HashMap<>());
    }
    // endregion

    // region insertExchange(List<Exchange> exchanges)
    @Test
    public void insertExchangeByListReturnsCompletableFromLocalSource() {
        // Arrange
        List<Exchange> fakeExchanges = getFakeExchanges(SIZE);
        // Act
        when(sourceFactory.getLocal()).thenReturn(exchangeLocal);
        when(exchangeLocal.insertExchange(anyList())).thenReturn(Completable.complete());
        //Assert
        repository.insertExchange(fakeExchanges).test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        verify(exchangeLocal, times(1)).insertExchange(fakeExchanges);
        verify(exchangeRemote, never()).insertExchange(fakeExchanges);
    }
    // endregion

    // region insertExchange(Exchange exchange)
    @Test
    public void insertExchangeByExchangeReturnsCompletableFromLocalSource() {
        // Arrange
        Exchange fakeExchange = getExchange();
        // Act
        when(sourceFactory.getLocal()).thenReturn(exchangeLocal);
        when(exchangeLocal.insertExchange(any(Exchange.class))).thenReturn(Completable.complete());
        //Assert
        repository.insertExchange(fakeExchange).test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        verify(exchangeLocal, times(1)).insertExchange(fakeExchange);
        verify(exchangeRemote, never()).insertExchange(fakeExchange);
    }
    // endregion

    // region eraseExchanges()
    @Test
    public void eraseExchangesReturnsCompletableFromLocalSource() {
        // Act
        when(sourceFactory.getLocal()).thenReturn(exchangeLocal);
        when(exchangeLocal.eraseExchanges()).thenReturn(Completable.complete());
        //Assert
        repository.eraseExchanges().test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        verify(exchangeLocal, times(1)).eraseExchanges();
        verify(exchangeRemote, never()).eraseExchanges();
    }
    // endregion

}

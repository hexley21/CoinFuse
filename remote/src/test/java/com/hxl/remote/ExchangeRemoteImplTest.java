package com.hxl.remote;

import static com.hxl.remote.fake.FakeRemoteDataFactory.getFakeExchangeResponse;
import static com.hxl.remote.fake.FakeRemoteDataFactory.getFakeTradeResponse;
import static com.hxl.remote.fake.RemoteTestConstants.ID;
import static com.hxl.remote.fake.RemoteTestConstants.LIMIT;
import static com.hxl.remote.fake.RemoteTestConstants.OFFSET;
import static com.hxl.remote.fake.RemoteTestConstants.SIZE;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.hxl.remote.exchange.ExchangeRemoteImpl;
import com.hxl.remote.exchange.api.ExchangeService;
import com.hxl.remote.exchange.model.ExchangeDTO;
import com.hxl.remote.exchange.model.TradeDTO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRemoteImplTest {

    @Mock
    ExchangeService service;
    @InjectMocks
    ExchangeRemoteImpl remoteSource;

    @Test
    public void getExchangesReturnsExchangesFromRemote() {
        // Arrange
        Response<List<ExchangeDTO>> fakeResponse = getFakeExchangeResponse(SIZE);
        // Act
        when(service.getExchanges()).thenReturn(Single.just(fakeResponse));
        // Assert
        remoteSource.getExchanges().test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
    }

    @Test
    public void getExchangesWithLimitAndOffsetReturnsExchangesFromRemote() {
        // Arrange
        Response<List<ExchangeDTO>> fakeResponse = getFakeExchangeResponse(SIZE);
        // Act
        when(service.getExchanges(anyInt(), anyInt())).thenReturn(Single.just(fakeResponse));
        // Assert
        remoteSource.getExchanges(LIMIT, OFFSET).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
    }

    @Test
    public void getExchangesWithExchangeIdReturnsExchangeFromRemote() {
        // Arrange
        Response<ExchangeDTO> fakeResponse = getFakeExchangeResponse(ID);
        // Act
        when(service.getExchange(anyString())).thenReturn(Single.just(fakeResponse));
        // Assert
        remoteSource.getExchange(ID).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.exchangeId.equals(fakeResponse.data.exchangeId));
    }

    @Test
    public void getTradesReturnsResponseFromRemote() {
        // Arrange
        Response<List<TradeDTO>> fakeResponse = getFakeTradeResponse(SIZE);
        // Act
        when(service.getTrades(anyMap())).thenReturn(Single.just(fakeResponse));
        // Assert
        remoteSource.getTrades(new HashMap<>()).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
    }

}

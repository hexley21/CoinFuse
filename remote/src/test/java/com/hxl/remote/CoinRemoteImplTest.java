package com.hxl.remote;

import static com.hxl.remote.fake.FakeRemoteDataFactory.getFakeTradeResponse;
import static com.hxl.remote.fake.FakeRemoteDataFactory.getHistoryResponse;
import static com.hxl.remote.fake.RemoteTestConstants.ASSET_URL;
import static com.hxl.remote.fake.RemoteTestConstants.ID;
import static com.hxl.remote.fake.RemoteTestConstants.KEY;
import static com.hxl.remote.fake.RemoteTestConstants.KEY_RESPONSE_SIZE;
import static com.hxl.remote.fake.RemoteTestConstants.LIMIT;
import static com.hxl.remote.fake.RemoteTestConstants.OFFSET;
import static com.hxl.remote.fake.RemoteTestConstants.SIZE;
import static com.hxl.remote.fake.RemoteTestConstants.TIMESTAMP;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.remote.coin.CoinRemoteImpl;
import com.hxl.remote.coin.api.CoinService;
import com.hxl.remote.coin.mapper.CoinDTOMapper;
import com.hxl.remote.coin.model.CoinDTO;
import com.hxl.remote.coin.model.HistoryDTO;
import com.hxl.remote.exchange.model.TradeDTO;
import com.hxl.remote.fake.FakeRemoteDataFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class CoinRemoteImplTest {

    @Mock
    CoinService coinService;
    final CoinDTOMapper mapper = spy(new CoinDTOMapper(ASSET_URL));

    @InjectMocks
    CoinRemoteImpl coinSource;

    @Test
    public void getCoinsReturnsResponseFromRemote() {
        // Arrange
        Single<Response<List<CoinDTO>>> response = Single.just(FakeRemoteDataFactory.getResponse(SIZE));
        when(coinService.getCoins()).thenReturn(response);
        // Act
        Single<List<Coin>> coins = coinSource.getCoins();
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(r -> r.size() == SIZE)
                .assertValue(r -> r.get(0).timestamp == TIMESTAMP);

        verify(coinService).getCoins();
        verify(mapper, times(SIZE)).mapFromDTO(any(), eq(TIMESTAMP));
    }

    @Test
    public void getCoinsByLimitAndOffsetReturnsFilteredResponseFromRemote() {
        // Arrange
        Single<Response<List<CoinDTO>>> response = Single.just(FakeRemoteDataFactory.getResponse(LIMIT));
        when(coinService.getCoins(LIMIT, OFFSET)).thenReturn(response);
        // Act
        Single<List<Coin>> coins = coinSource.getCoins(LIMIT, OFFSET);
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(r -> r.size() == LIMIT);

        verify(coinService).getCoins(LIMIT, OFFSET);
        verify(mapper, times(LIMIT)).mapFromDTO(any(), eq(TIMESTAMP));
    }

    @Test
    public void searchCoinReturnsFilteredResponseFromRemote() {
        // Arrange
        String[] keyArray = KEY.split(",");
        Single<Response<List<CoinDTO>>> response = Single.just(FakeRemoteDataFactory.getResponse(keyArray));
        when(coinService.searchCoins(KEY)).thenReturn(response);
        // Act
        Single<List<Coin>> coin = coinSource.searchCoins(KEY);
        // Assert
        coin.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(r -> r.size() == KEY_RESPONSE_SIZE)
                .assertValue(r -> r.get(0).id.equals(keyArray[0]));

        verify(coinService).searchCoins(KEY);
        verify(mapper, times(KEY_RESPONSE_SIZE)).mapFromDTO(any(), eq(TIMESTAMP));
    }

    @Test
    public void getCoinByIdReturnsResponseFromRemote() {
        // Arrange
        Single<Response<CoinDTO>> response = Single.just(FakeRemoteDataFactory.getResponse(ID));
        when(coinService.getCoin(ID)).thenReturn(response);
        // Act
        Single<Coin> coin = coinSource.getCoin(ID);
        // Assert
        coin.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(r -> r.id.equals(ID));

        verify(coinService).getCoin(ID);
        verify(mapper, times(1)).mapFromDTO(any(), eq(TIMESTAMP));
    }

    @Test
    public void getCoinHistoryReturnsResponseFromRemote() {
        // Arrange
        Single<Response<List<HistoryDTO>>> response = Single.just(getHistoryResponse(SIZE));
        // Act
        when(coinService.getCoinHistory(anyString(), anyString())).thenReturn(response);
        Single<List<CoinPriceHistory>> history = coinSource.getCoinPriceHistory(ID, CoinPriceHistory.Interval.D1);
        // Assert
        history.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(r -> r.size() == SIZE);

        verify(coinService).getCoinHistory(ID, CoinPriceHistory.Interval.D1.param);
    }

    @Test
    public void getTradesByIdReturnsResponseFromRemote() {
        // Arrange
        Response<List<TradeDTO>> response = getFakeTradeResponse(SIZE);
        // Act
        when(coinService.getTradesByCoin(anyString())).thenReturn(Single.just(response));
        // Assert
        coinSource.getTradesByCoin(ID).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
    }

    @Test
    public void getTradesByCoinByIdAndLimitAndOffsetReturnsResponseFromRemote() {
        // Arrange
        Response<List<TradeDTO>> response = getFakeTradeResponse(SIZE);
        // Act
        when(coinService.getTradesByCoin(anyString(), anyInt(), anyInt())).thenReturn(Single.just(response));
        // Assert
        coinSource.getTradesByCoin(ID, LIMIT, OFFSET).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
    }
}
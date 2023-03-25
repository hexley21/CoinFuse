package com.hxl.remote;

import static com.hxl.remote.fake.TestConstants.ASSET_URL;
import static com.hxl.remote.fake.TestConstants.ID;
import static com.hxl.remote.fake.TestConstants.LIMIT;
import static com.hxl.remote.fake.TestConstants.OFFSET;
import static com.hxl.remote.fake.TestConstants.SIZE;
import static com.hxl.remote.fake.TestConstants.TIMESTAMP;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.model.Coin;
import com.hxl.remote.api.CoinService;
import com.hxl.remote.fake.FakeRemoteDataFactory;
import com.hxl.remote.model.CoinDTO;
import com.hxl.remote.model.CoinDTOMapper;
import com.hxl.remote.model.Response;

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
    CoinDTOMapper mapper = spy(new CoinDTOMapper(ASSET_URL));

    @InjectMocks
    CoinRemoteImpl coinSource;

    @Test
    public void testGetCoins() {
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

        verify(mapper, times(SIZE)).mapFromDTO(any(), eq(TIMESTAMP));
    }

    @Test
    public void testGetCoinsByLimitAndOffset() {
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
        verify(mapper, times(LIMIT)).mapFromDTO(any(), eq(TIMESTAMP));
    }

    @Test
    public void testGetCoinById() {
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
        verify(mapper, times(1)).mapFromDTO(any(), eq(TIMESTAMP));
    }
}
package com.hxl.interactors.coins;

import static com.hxl.fakes.DomainTestConstants.ID;
import static com.hxl.fakes.DomainTestConstants.SIZE;
import static com.hxl.fakes.FakeDomainFactory.getFakeHistory;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.coins.GetCoinHistory;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.domain.repository.CoinRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class GetCoinCoinPriceHistoryTest {

    @Mock
    CoinRepository repository;

    @InjectMocks
    GetCoinHistory interactor;

    @Test
    public void invokeReturnsListFromRepository() {
        // Arrange
        Single<List<CoinPriceHistory>> history = Single.just(getFakeHistory(SIZE));
        when(repository.getCoinHistory(anyString(), any(CoinPriceHistory.Interval.class))).thenReturn(history);
        // Act
        Single<List<CoinPriceHistory>> coinHistory = interactor.invoke(ID, CoinPriceHistory.Interval.D1);
        // Assert
        coinHistory.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(repository).getCoinHistory(ID, CoinPriceHistory.Interval.D1);
    }

}

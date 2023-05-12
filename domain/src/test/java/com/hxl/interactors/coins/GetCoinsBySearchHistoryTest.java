package com.hxl.interactors.coins;

import com.hxl.domain.interactors.coins.GetCoinsBySearchHistory;
import com.hxl.domain.model.Coin;
import com.hxl.domain.repository.CoinRepository;
import com.hxl.fakes.FakeDomainFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.hxl.fakes.DomainTestConstants.SIZE;

import static org.mockito.Mockito.when;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class GetCoinsBySearchHistoryTest {

    @Mock
    private CoinRepository repository;
    @InjectMocks
    private GetCoinsBySearchHistory interactor;

    @Test
    public void invokeReturnsCoinsFromRepositoryBySearchHistory() {
        // Arrange
        List<Coin> fakeCoins = FakeDomainFactory.getFakeCoins(SIZE);
        // Act
        when(repository.getCoinsBySearchHistory()).thenReturn(Single.just(fakeCoins));
        // Assert
        interactor.invoke().test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
    }
}

package com.hxl.interactors.coins;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static com.hxl.interactors.fakes.DomainTestConstants.SIZE;
import static com.hxl.interactors.fakes.DomainTestConstants.ID;
import static com.hxl.interactors.fakes.FakeDomainFactory.getFakeCoins;

import com.hxl.domain.interactors.coins.SearchCoins;
import com.hxl.domain.model.Coin;
import com.hxl.domain.repository.CoinRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class SearchCoinsTest {
    @Mock
    CoinRepository coinRepository;
    @InjectMocks
    SearchCoins interactor;

    @Test
    public void invokeReturnsFilteredWithKeyListFromRepository() {
        // Arrange
        when(coinRepository.searchCoins(anyString())).thenReturn(Single.just(getFakeCoins(SIZE)));
        // Act
        Single<List<Coin>> coins = interactor.invoke(ID);
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(coinRepository).searchCoins(ID);
    }
}

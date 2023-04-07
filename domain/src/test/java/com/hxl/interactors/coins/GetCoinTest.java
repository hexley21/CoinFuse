package com.hxl.interactors.coins;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static com.hxl.fakes.FakeDomainFactory.getCoin;
import static com.hxl.fakes.DomainTestConstants.ID;

import com.hxl.domain.interactors.coins.GetCoin;
import com.hxl.domain.model.Coin;
import com.hxl.domain.repository.CoinRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class GetCoinTest {
    @Mock
    CoinRepository repository;
    @InjectMocks
    GetCoin interactor;

    @Test
    public void testInvokeReturnsModelFromRepository() {
        // Arrange
        when(repository.getCoin(anyString())).thenReturn(Single.just(getCoin(ID)));
        // Act
        Single<Coin> coin = interactor.invoke(ID);
        // Assert
        coin.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.id.equals(ID));
        verify(repository).getCoin(anyString());
    }
}

package com.hxl.interactors.coins;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static com.hxl.interactors.fakes.FakeDomainFactory.*;
import static com.hxl.interactors.fakes.DomainTestConstants.*;

import com.hxl.domain.interactors.coins.GetCoins;
import com.hxl.domain.model.Coin;
import com.hxl.domain.repository.CoinRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class GetCoinsTest {
    @Mock
    CoinRepository repository;
    @InjectMocks
    GetCoins interactor;

    @Test
    public void invokeReturnsListFromRepository() {
        // Arrange
        when(repository.getCoins()).thenReturn(Single.just(getFakeCoins(SIZE)));
        // Act
        Single<List<Coin>> coins = interactor.invoke();
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(repository).getCoins();
    }

    @Test
    public void invokeWithIdsReturnsFilteredListFromRepository() {
        // Arrange
        when(repository.getCoins(anyList())).thenReturn(Single.just(getFakeCoins(SIZE)));
        // Act
        Single<List<Coin>> coins = interactor.invoke(new ArrayList<>());
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(repository).getCoins(anyList());
    }

    @Test
    public void invokeWithLimitAndOffsetReturnsFilteredListFromRepository() {
        // Arrange
        when(repository.getCoins(anyInt(), anyInt())).thenReturn(Single.just(getFakeCoins(SIZE)));
        // Act
        Single<List<Coin>> coins = interactor.invoke(LIMIT, OFFSET);
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(repository).getCoins(anyInt(), anyInt());
    }
}

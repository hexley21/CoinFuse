package com.hxl.interactors.coins;

import static com.hxl.interactors.fakes.DomainTestConstants.ID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.coins.UnBookmarkCoin;
import com.hxl.domain.repository.CoinRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.rxjava3.core.Completable;

@RunWith(MockitoJUnitRunner.class)
public class UnBookmarkCoinTest {

    @Mock
    CoinRepository repository;
    @InjectMocks
    UnBookmarkCoin interactor;

    @Test
    public void invokeRemovesCoinIdFromRepository() {
        // Arrange
        when(repository.unBookmarkCoin(anyString())).thenReturn(Completable.complete());
        // Act
        Completable unBookmarkCoin = interactor.invoke(ID);
        // Assert
        unBookmarkCoin.test()
                .assertComplete()
                .assertNoErrors();
        verify(repository).unBookmarkCoin(ID);
    }
}

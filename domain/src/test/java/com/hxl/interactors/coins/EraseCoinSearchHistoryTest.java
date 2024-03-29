package com.hxl.interactors.coins;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.coins.EraseCoinSearchHistory;
import com.hxl.domain.repository.CoinRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.rxjava3.core.Completable;

@RunWith(MockitoJUnitRunner.class)
public class EraseCoinSearchHistoryTest {

    @Mock
    private CoinRepository repository;

    @InjectMocks
    private EraseCoinSearchHistory interactor;

    @Test
    public void invokeDeletesCoinSearchHistoryFromRepository() {
        // Arrange
        when(repository.eraseCoinSearchHistory()).thenReturn(Completable.complete());
        // Act
        Completable deleteSearchHistory = interactor.invoke();
        // Assert
        deleteSearchHistory.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();

        verify(repository, times(1)).eraseCoinSearchHistory();
    }
}

package com.hxl.interactors.coins;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.coins.DeleteCoinSearchQuery;
import com.hxl.domain.repository.CoinRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.rxjava3.core.Completable;

import static com.hxl.fakes.DomainTestConstants.ID;


@RunWith(MockitoJUnitRunner.class)
public class DeleteCoinSearchQueryTest {
    @Mock
    private CoinRepository repository;

    @InjectMocks
    private DeleteCoinSearchQuery interactor;

    @Test
    public void invokeDeletesCoinQueryFromRepository() {
        // Arrange
        when(repository.deleteCoinSearchQuery(anyString())).thenReturn(Completable.complete());
        // Act
        Completable deleteSearchHistory = interactor.invoke(ID);
        // Assert
        deleteSearchHistory.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();

        verify(repository, times(1)).deleteCoinSearchQuery(ID);
    }
}

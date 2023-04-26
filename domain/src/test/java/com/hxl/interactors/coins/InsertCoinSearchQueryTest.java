package com.hxl.interactors.coins;

import static com.hxl.fakes.DomainTestConstants.ID;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.coins.InsertCoinSearchQuery;
import com.hxl.domain.repository.CoinRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@RunWith(MockitoJUnitRunner.class)
public class InsertCoinSearchQueryTest {

    @Mock
    private CoinRepository repository;

    @InjectMocks
    private InsertCoinSearchQuery interactor;

    @Test
    public void invokeInsertsQueryToRepository() {
        // Arrange
        when(repository.insertCoinSearchQuery(anyString())).thenReturn(Completable.complete());
        // Act
        Completable insertSearchQuery = interactor.invoke(ID);
        // Assert
        insertSearchQuery.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();

        verify(repository, times(1)).insertCoinSearchQuery(ID);
    }

    @Test
    public void invokeInsertsQueriesToRepository() {
        // Arrange
        List<String> queries = new ArrayList<>();
        queries.add(ID);
        queries.add(ID);
        queries.add(ID);
        when(repository.insertCoinSearchQuery(anyList())).thenReturn(Completable.complete());
        // Act
        Completable insertSearchQuery = repository.insertCoinSearchQuery(queries);
        // Assert
        insertSearchQuery.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();

        verify(repository, times(1)).insertCoinSearchQuery(queries);
    }
}

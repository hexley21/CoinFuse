package com.hxl.interactors.coins;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static com.hxl.fakes.FakeDomainFactory.getFakeSearchQueries;
import static com.hxl.fakes.DomainTestConstants.SIZE;

import com.hxl.domain.interactors.coins.GetCoinSearchHistory;
import com.hxl.domain.model.SearchQuery;
import com.hxl.domain.repository.CoinRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class GetCoinSearchHistoryTest {

    @Mock
    private CoinRepository repository;

    @InjectMocks
    private GetCoinSearchHistory interactor;

    @Test
    public void invokeReturnsSearchQueryListFromRepository() {
        // Arrange
        List<SearchQuery> fakeSearchQueries = getFakeSearchQueries(SIZE);
        when(repository.getCoinSearchHistory()).thenReturn(Single.just(fakeSearchQueries));
        // Act
        Single<List<SearchQuery>> searchQueries = interactor.invoke();
        // Assert
        searchQueries.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);

        verify(repository, times(1)).getCoinSearchHistory();
    }
}

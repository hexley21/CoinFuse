package com.hxl.interactors.exchanges;

import static com.hxl.fakes.DomainTestConstants.SIZE;
import static com.hxl.fakes.FakeDomainFactory.getExchange;
import static com.hxl.fakes.FakeDomainFactory.getFakeExchanges;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.exchanges.InsertExchange;
import com.hxl.domain.model.Exchange;
import com.hxl.domain.repository.ExchangeRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@RunWith(MockitoJUnitRunner.class)
public class InsertExchangeTest {

    @Mock
    private ExchangeRepository repository;
    @InjectMocks
    public InsertExchange interactor;

    @Test
    public void invokeWithListReturnsCompletableFromRepository() {
        // Arrange
        List<Exchange> fakeExchanges = getFakeExchanges(SIZE);
        // Act
        when(repository.insertExchange(anyList())).thenReturn(Completable.complete());
        // Assert
        interactor.invoke(fakeExchanges).test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
    }

    @Test
    public void invokeWithExchangeReturnsCompletableFromRepository() {
        // Arrange
        Exchange fakeExchange = getExchange();
        // Act
        when(repository.insertExchange(any(Exchange.class))).thenReturn(Completable.complete());
        // Assert
        interactor.invoke(fakeExchange).test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
    }

}

package com.hxl.interactors.exchanges;

import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.exchanges.EraseExchange;
import com.hxl.domain.repository.ExchangeRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.rxjava3.core.Completable;

@RunWith(MockitoJUnitRunner.class)
public class EraseExchangeTest {

    @Mock
    private ExchangeRepository repository;
    @InjectMocks
    private EraseExchange interactor;


    @Test
    public void invokeReturnsCompletableFromRepository() {
        // Act
        when(repository.eraseExchanges()).thenReturn(Completable.complete());
        // Assert
        interactor.invoke().test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
    }

}

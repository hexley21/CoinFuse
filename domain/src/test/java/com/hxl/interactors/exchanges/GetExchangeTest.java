package com.hxl.interactors.exchanges;

import static com.hxl.fakes.DomainTestConstants.ID;
import static com.hxl.fakes.FakeDomainFactory.getExchange;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.exchanges.GetExchange;
import com.hxl.domain.model.Exchange;
import com.hxl.domain.repository.ExchangeRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class GetExchangeTest {

    @Mock
    private ExchangeRepository repository;
    @InjectMocks
    private GetExchange interactor;

    @Test
    public void invokeReturnsExchangeFromRepository() {
        // Arrange
        Exchange fakeExchange = getExchange();
        // Act
        when(repository.getExchange(anyString())).thenReturn(Single.just(fakeExchange));
        // Assert
        interactor.invoke(ID).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x == fakeExchange);
    }

}

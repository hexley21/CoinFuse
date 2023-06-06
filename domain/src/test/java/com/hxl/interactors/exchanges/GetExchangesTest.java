package com.hxl.interactors.exchanges;

import static com.hxl.fakes.DomainTestConstants.SIZE;
import static com.hxl.fakes.FakeDomainFactory.getFakeExchanges;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.exchanges.GetExchanges;
import com.hxl.domain.model.Exchange;
import com.hxl.domain.repository.ExchangeRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class GetExchangesTest {

    @Mock
    private ExchangeRepository repository;
    @InjectMocks
    public GetExchanges interactor;

    @Test
    public void invokeReturnsExchangeListFromRepository() {
        // Arrange
        List<Exchange> exchanges = getFakeExchanges(SIZE);
        // Act
        when(repository.getExchanges()).thenReturn(Single.just(exchanges));
        // Assert
        interactor.invoke().test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
    }

    @Test
    public void invokeWithLimitAndOffsetReturnsExchangeListFromRepository() {
        // Arrange
        List<Exchange> exchanges = getFakeExchanges(SIZE);
        // Act
        when(repository.getExchanges(anyInt(), anyInt())).thenReturn(Single.just(exchanges));
        // Assert
        interactor.invoke(SIZE, SIZE).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
    }

}

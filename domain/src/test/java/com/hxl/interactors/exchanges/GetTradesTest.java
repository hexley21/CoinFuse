package com.hxl.interactors.exchanges;

import static com.hxl.fakes.DomainTestConstants.SIZE;
import static com.hxl.fakes.FakeDomainFactory.getFakeTrades;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.exchanges.GetTrades;
import com.hxl.domain.model.Trade;
import com.hxl.domain.repository.ExchangeRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class GetTradesTest {

    @Mock
    private ExchangeRepository repository;
    @InjectMocks
    private GetTrades getTrades;

    @Test
    public void invokeReturnsTradesFromRepository() {
        // Arrange
        List<Trade> fakeTrades = getFakeTrades(SIZE);
        // Act
        when(repository.getTrades(anyMap())).thenReturn(Single.just(fakeTrades));
        // Assert
        getTrades.invoke(new HashMap<>()).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
    }
}

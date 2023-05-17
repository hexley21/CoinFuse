package com.hxl.interactors.coins;

import static com.hxl.fakes.DomainTestConstants.ID;
import static com.hxl.fakes.DomainTestConstants.LIMIT;
import static com.hxl.fakes.DomainTestConstants.OFFSET;
import static com.hxl.fakes.DomainTestConstants.SIZE;
import static com.hxl.fakes.FakeDomainFactory.getFakeTrades;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.coins.GetTradesByCoin;
import com.hxl.domain.model.Trade;
import com.hxl.domain.repository.CoinRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class GetTradesByCoinTest {

    @Mock
    private CoinRepository repository;
    @InjectMocks
    private GetTradesByCoin interactor;

    @Test
    public void invokeByIdReturnsTradesFromRepository()  {
        // Arrange
        List<Trade> fakeTrades = getFakeTrades(SIZE);
        // Act
        when(repository.getTradesByCoin(anyString())).thenReturn(Single.just(fakeTrades));
        // Assert
        interactor.invoke(ID).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
    }

    @Test
    public void invokeByIdAndLimitAndOffsetReturnsTradesFromRepository()  {
        // Arrange
        List<Trade> fakeTrades = getFakeTrades(SIZE);
        // Act
        when(repository.getTradesByCoin(anyString(), anyInt(), anyInt())).thenReturn(Single.just(fakeTrades));
        // Assert
        interactor.invoke(ID, LIMIT, OFFSET).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
    }

}

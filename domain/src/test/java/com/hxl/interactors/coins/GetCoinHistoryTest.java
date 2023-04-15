package com.hxl.interactors.coins;

import static com.hxl.fakes.DomainTestConstants.ID;
import static com.hxl.fakes.DomainTestConstants.SIZE;
import static com.hxl.fakes.FakeDomainFactory.getFakeHistory;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.coins.GetCoinHistory;
import com.hxl.domain.model.History;
import com.hxl.domain.repository.CoinRepository;
import com.hxl.fakes.FakeDomainFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class GetCoinHistoryTest {

    @Mock
    CoinRepository repository;

    @InjectMocks
    GetCoinHistory interactor;

    @Test
    public void invokeReturnsListFromRepository() {
        // Arrange
        Single<List<History>> history = Single.just(getFakeHistory(SIZE));
        when(repository.getCoinHistory(anyString(), any(History.Interval.class))).thenReturn(history);
        // Act
        Single<List<History>> coinHistory = interactor.invoke(ID, History.Interval.D1);
        // Assert
        coinHistory.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(repository).getCoinHistory(ID, History.Interval.D1);
    }

}

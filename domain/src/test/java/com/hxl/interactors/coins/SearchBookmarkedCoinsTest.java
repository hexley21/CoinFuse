package com.hxl.interactors.coins;

import com.hxl.domain.interactors.coins.SearchBookmarkedCoins;
import com.hxl.domain.model.Coin;
import com.hxl.domain.repository.CoinRepository;
import com.hxl.fakes.FakeDomainFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import static com.hxl.fakes.DomainTestConstants.SIZE;
import static com.hxl.fakes.DomainTestConstants.ID;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchBookmarkedCoinsTest {

    @Mock
    private CoinRepository repository;
    @InjectMocks
    private SearchBookmarkedCoins interactor;


    @Test
    public void invokeReturnsBookmarkedCoinsBySearchQuery() {
        // Arrange
        List<Coin> fakeCoins = FakeDomainFactory.getFakeCoins(SIZE);
        // Act
        when(repository.searchBookmarkedCoins(anyString())).thenReturn(Single.just(fakeCoins));
        // Assert
        interactor.invoke(ID).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
    }
}

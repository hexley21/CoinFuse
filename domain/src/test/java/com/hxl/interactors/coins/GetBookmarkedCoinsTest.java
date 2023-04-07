package com.hxl.interactors.coins;

import static com.hxl.fakes.DomainTestConstants.SIZE;
import static com.hxl.fakes.FakeDomainFactory.getFakeCoins;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.coins.GetBookmarkedCoins;
import com.hxl.domain.model.Coin;
import com.hxl.domain.repository.CoinRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class GetBookmarkedCoinsTest {

    @Mock
    CoinRepository repository;
    @InjectMocks
    GetBookmarkedCoins interactor;

    @Test
    public void invokeReturnsListOfBookmarkedCoinsFromRepository() {
        // Arrange
        when(repository.getBookmarkedCoins()).thenReturn(Single.just(getFakeCoins(SIZE)));
        // Act
        Single<List<Coin>> bookmarkedCoins = interactor.invoke();
        // Assert
        bookmarkedCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(repository).getBookmarkedCoins();
    }
}

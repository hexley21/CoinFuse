package com.hxl.interactors.coins;

import static com.hxl.fakes.DomainTestConstants.ID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.coins.BookmarkCoin;
import com.hxl.domain.repository.CoinRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.rxjava3.core.Completable;

@RunWith(MockitoJUnitRunner.class)
public class BookmarkCoinTest {

    @Mock
    CoinRepository repository;
    @InjectMocks
    BookmarkCoin interactor;

    @Test
    public void invokeInsertsCoinIdToRepository() {
        // Arrange
        when(repository.bookmarkCoin(anyString())).thenReturn(Completable.complete());
        // Act
        Completable bookmarkCoin = interactor.invoke(ID);
        // Assert
        bookmarkCoin.test()
                .assertComplete()
                .assertNoErrors();
        verify(repository).bookmarkCoin(anyString());
    }
}

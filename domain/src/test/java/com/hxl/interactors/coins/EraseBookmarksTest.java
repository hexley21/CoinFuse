package com.hxl.interactors.coins;

import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.coins.EraseBookmarks;
import com.hxl.domain.repository.CoinRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.rxjava3.core.Completable;

@RunWith(MockitoJUnitRunner.class)
public class EraseBookmarksTest {

    @Mock
    private CoinRepository repository;

    @InjectMocks
    private EraseBookmarks interactor;

    @Test
    public void invokeReturnsCompletableFromRepository() {
        // Act
        when(repository.eraseBookmarks()).thenReturn(Completable.complete());
        // Assert
        interactor.invoke().test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
    }

}

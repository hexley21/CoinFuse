package com.hxl.interactors.coins;

import static com.hxl.fakes.DomainTestConstants.ID;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.coins.IsCoinBookmarked;
import com.hxl.domain.repository.CoinRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class IsCoinBookmarkedTest {

    @Mock
    CoinRepository repository;
    @InjectMocks
    IsCoinBookmarked interactor;

    @Test
    public void IsCoinBookmarkedReturnsBooleanFromRepository() {
        // Arrange
        when(interactor.invoke(anyString())).thenReturn(Single.just(Boolean.TRUE));
        // Act
        Single<Boolean> isBookmarked = repository.isCoinBookmarked(ID);
        // Assert
        isBookmarked.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x);
        verify(repository).isCoinBookmarked(ID);
    }
}

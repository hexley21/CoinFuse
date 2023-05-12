package com.hxl.presentation.viewmodels;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static com.hxl.presentation.fakes.PresentationTestConstants.ID;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.BookmarkCoin;
import com.hxl.domain.interactors.coins.IsCoinBookmarked;
import com.hxl.domain.interactors.coins.UnBookmarkCoin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class CoinDialogViewModelTest extends ViewModel {

    @Mock
    private IsCoinBookmarked isCoinBookmarked;
    @Mock
    private BookmarkCoin bookmarkCoin;
    @Mock
    private UnBookmarkCoin unBookmarkCoin;
    @InjectMocks
    private CoinDialogViewModel viewModel;

    @Test
    public void isCoinBookmarkedReturnsBooleanFromInteractor() {
        // Act
        when(isCoinBookmarked.invoke(anyString())).thenReturn(Single.just(true));
        // Assert
        viewModel.isCoinBookmarked(ID).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x);
    }

    @Test
    public void bookmarkCoinReturnsCompletableFromInteractor() {
        // Act
        when(bookmarkCoin.invoke(anyString())).thenReturn(Completable.complete());
        // Assert
        viewModel.bookmarkCoin(ID).test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
    }

    @Test
    public void unBookmarkCoinReturnsCompletableFromInteractor() {
        // Act
        when(unBookmarkCoin.invoke(anyString())).thenReturn(Completable.complete());
        // Assert
        viewModel.unBookmarkCoin(ID).test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
    }

}

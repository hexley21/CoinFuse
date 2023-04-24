package com.hxl.presentation.viewmodels;

import static com.hxl.presentation.fakes.AppTestConstants.ID;
import static com.hxl.presentation.fakes.AppTestConstants.SIZE;
import static com.hxl.presentation.fakes.FakeDataFactory.getFakeHistory;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.coins.BookmarkCoin;
import com.hxl.domain.interactors.coins.GetCoin;
import com.hxl.domain.interactors.coins.GetCoinHistory;
import com.hxl.domain.interactors.coins.IsCoinBookmarked;
import com.hxl.domain.interactors.coins.UnBookmarkCoin;
import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.presentation.fakes.FakeDataFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class CoinDetailsViewModelTest {

    @Mock
    GetCoin getCoin;
    @Mock
    GetCoinHistory getCoinHistory;
    @Mock
    IsCoinBookmarked isCoinBookmarked;
    @Mock
    BookmarkCoin bookmarkCoin;
    @Mock
    UnBookmarkCoin unBookmarkCoin;

    @InjectMocks
    CoinDetailsViewModel viewModel;

    @Test
    public void getCoinReturnsCoinFromInteractor() {
        // Arrange
        Coin fakeCoin = FakeDataFactory.getCoin();
        when(getCoin.invoke(anyString())).thenReturn(Single.just(fakeCoin));
        // Act
        Single<Coin> coin = viewModel.getCoin(ID);
        // Assert
        coin.test()
                .awaitCount(1)
                .assertNoErrors();
        verify(getCoin).invoke(ID);
    }

    @Test
    public void getCoinHistoryReturnsHistoryFromInteractor() {
        // Arrange
        List<CoinPriceHistory> fakeCoinPriceHistory = getFakeHistory(SIZE);
        when(getCoinHistory.invoke(anyString(), any(CoinPriceHistory.Interval.class))).thenReturn(Single.just(fakeCoinPriceHistory));
        // Act
        Single<List<CoinPriceHistory>> history = viewModel.getCoinHistory(ID, CoinPriceHistory.Interval.D1);
        // Assert
        history.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(getCoinHistory).invoke(ID, CoinPriceHistory.Interval.D1);
    }

    @Test
    public void isCoinBookmarkedReturnsBooleanFromRemote() {
        // Arrange
        when(isCoinBookmarked.invoke(anyString())).thenReturn(Single.just(Boolean.TRUE));
        // Act
        Single<Boolean> bookmarked = viewModel.isCoinBookmarked(ID);
        // Assert
        bookmarked.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x);
        verify(isCoinBookmarked).invoke(ID);
    }

    @Test
    public void bookmarkCoinInsertsCoinWithInteractor() {
        // Arrange
        when(bookmarkCoin.invoke(anyString())).thenReturn(Completable.complete());
        // Act
        Completable bookmark = viewModel.bookmarkCoin(ID);
        // Assert
        bookmark.test()
                .awaitCount(1)
                .assertNoErrors();
        verify(bookmarkCoin).invoke(ID);
    }

    @Test
    public void unBookmarkCoinRemovesCoinWithInteractor() {
        // Arrange
        when(unBookmarkCoin.invoke(anyString())).thenReturn(Completable.complete());
        // Act
        Completable unBookmark = viewModel.unBookmarkCoin(ID);
        // Assert
        unBookmark.test()
                .awaitCount(1)
                .assertNoErrors();
        verify(unBookmarkCoin).invoke(ID);
    }
}

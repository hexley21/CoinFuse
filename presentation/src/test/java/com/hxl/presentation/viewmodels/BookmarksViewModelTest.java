package com.hxl.presentation.viewmodels;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.coins.GetBookmarkedCoins;
import com.hxl.domain.interactors.coins.SearchBookmarkedCoins;
import com.hxl.domain.model.Coin;
import com.hxl.presentation.OrderBy;
import com.hxl.presentation.coin.CoinSortBy;
import com.hxl.presentation.fakes.FakeDataFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.hxl.presentation.fakes.PresentationTestConstants.ID;
import static com.hxl.presentation.fakes.PresentationTestConstants.SIZE;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class BookmarksViewModelTest extends ViewModel {

    @Mock
    private GetBookmarkedCoins getBookmarkedCoins;
    @Mock
    private SearchBookmarkedCoins searchBookmarkedCoins;
    @InjectMocks
    private BookmarksViewModel viewModel;

    @Test
    public void bookmarkedCoinsReturnsSortedCoinsFromInteractor() {
        // Arrange
        List<Coin> fakeCoins = FakeDataFactory.getFakeCoins(SIZE);
        // Act
        when(getBookmarkedCoins.invoke()).thenReturn(Single.just(fakeCoins));
        // Assert
        viewModel.bookmarkedCoins(CoinSortBy.RANK, OrderBy.DESC).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
    }

    @Test
    public void searchBookmarkedCoinsReturnsSortedCoinsBySearchQueryFromInteractor() {
        // Arrange
        List<Coin> fakeCoins = FakeDataFactory.getFakeCoins(SIZE);
        // Act
        when(searchBookmarkedCoins.invoke(anyString())).thenReturn(Single.just(fakeCoins));
        // Assert
        viewModel.searchBookmarkedCoins(ID, CoinSortBy.RANK, OrderBy.DESC).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
    }

}

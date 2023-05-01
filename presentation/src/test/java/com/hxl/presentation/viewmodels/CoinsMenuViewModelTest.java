package com.hxl.presentation.viewmodels;

import static com.hxl.presentation.fakes.AppTestConstants.*;
import static com.hxl.presentation.fakes.FakeDataFactory.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.coins.DeleteCoinSearchQuery;
import com.hxl.domain.interactors.coins.GetCoinSearchHistory;
import com.hxl.domain.interactors.coins.GetCoins;
import com.hxl.domain.interactors.coins.InsertCoinSearchQuery;
import com.hxl.domain.interactors.coins.SearchCoins;
import com.hxl.domain.model.Coin;
import com.hxl.domain.model.ValueAndTimestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;


@RunWith(MockitoJUnitRunner.class)
public class CoinsMenuViewModelTest {

    @Mock
    GetCoins getCoins;
    @Mock
    SearchCoins searchCoins;
    @Mock
    GetCoinSearchHistory getCoinSearchHistory;
    @Mock
    InsertCoinSearchQuery insertCoinSearchQuery;
    @Mock
    DeleteCoinSearchQuery deleteCoinSearchQuery;
    @InjectMocks
    CoinsMenuViewModel viewModel;


    @Test
    public void getCoinsReturnsListFromRepository() {
        // Arrange
        List<Coin> coins = getFakeCoins(SIZE);
        when(getCoins.invoke()).thenReturn(Single.just(coins));
        // Act
        Single<List<Coin>> viewModelCoins = viewModel.getCoins();
        // Assert
        viewModelCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(getCoins).invoke();
    }

    @Test
    public void searchCoinsReturnsFilteredListFromRepository() {
        // Arrange
        List<Coin> coins = getFakeCoins(SIZE);
        when(searchCoins.invoke(anyString())).thenReturn(Single.just(coins));
        // Act
        Single<List<Coin>> viewModelCoins = viewModel.searchCoins(ID);
        // Assert
        viewModelCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(searchCoins).invoke(ID);
    }
    @Test
    public void getCoinSearchHistoryReturnsSearchQueryFromRepository() {
        // Arrange
        List<ValueAndTimestamp<String>> searchQueries = getFakeSearchQueries(SIZE);
        when(getCoinSearchHistory.invoke()).thenReturn(Single.just(searchQueries));
        // Act
        Single<List<ValueAndTimestamp<String>>> viewModelCoins = viewModel.getCoinSearchHistory();
        // Assert
        viewModelCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(getCoinSearchHistory).invoke();
    }

    @Test
    public void insertCoinSearchQueryInsertsSearchQueryToRepository() {
        // Arrange
        when(insertCoinSearchQuery.invoke(anyString())).thenReturn(Completable.complete());
        // Act
        Completable viewModelCoins = viewModel.insertCoinSearchQuery(ID);
        // Assert
        viewModelCoins.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        verify(insertCoinSearchQuery).invoke(ID);
    }

    @Test
    public void deleteCoinSearchQueryDeletesSearchQueryFromRepository() {
        // Arrange
        when(deleteCoinSearchQuery.invoke(anyString())).thenReturn(Completable.complete());
        // Act
        Completable viewModelCoins = viewModel.deleteCoinSearchQuery(ID);
        // Assert
        viewModelCoins.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        verify(deleteCoinSearchQuery).invoke(ID);
    }
}

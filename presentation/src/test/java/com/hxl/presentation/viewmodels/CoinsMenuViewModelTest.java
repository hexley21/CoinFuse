package com.hxl.presentation.viewmodels;

import static com.hxl.presentation.fakes.AppTestConstants.*;
import static com.hxl.presentation.fakes.FakeCoinFactory.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.domain.interactors.coins.GetCoins;
import com.hxl.domain.interactors.coins.SearchCoins;
import com.hxl.domain.model.Coin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.rxjava3.core.Single;


@RunWith(MockitoJUnitRunner.class)
public class CoinsMenuViewModelTest {

    @Mock
    GetCoins getCoins;
    @Mock
    SearchCoins searchCoins;
    @InjectMocks
    CoinsMenuViewModel viewModel;


    @Test
    public void getCoinsReturnsListFromInteractor() {
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
    public void searchCoinsReturnsFilteredListFromInteractor() {
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
}

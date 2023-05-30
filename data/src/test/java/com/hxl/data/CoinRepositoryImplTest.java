package com.hxl.data;

import static com.hxl.data.fakes.DataTestConstants.ID;
import static com.hxl.data.fakes.DataTestConstants.IDS;
import static com.hxl.data.fakes.DataTestConstants.LIMIT;
import static com.hxl.data.fakes.DataTestConstants.OFFSET;
import static com.hxl.data.fakes.DataTestConstants.SIZE;
import static com.hxl.data.fakes.DataTestConstants.TIMESTAMP;
import static com.hxl.data.fakes.FakeDataFactory.getCoin;
import static com.hxl.data.fakes.FakeDataFactory.getFakeCoins;
import static com.hxl.data.fakes.FakeDataFactory.getFakePriceHistory;
import static com.hxl.data.fakes.FakeDataFactory.getFakeSearchQueries;
import static com.hxl.data.fakes.FakeDataFactory.getFakeTrades;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.data.repository.coin.CoinLocal;
import com.hxl.data.repository.coin.CoinRemote;
import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.domain.model.Trade;
import com.hxl.domain.model.ValueAndTimestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class CoinRepositoryImplTest {

    @Mock
    private CoinRemote remoteSource;
    @Mock
    private CoinLocal localSource;
    @InjectMocks
    private CoinRepositoryImpl repository;
    private final UnknownHostException unknownHostException = new UnknownHostException("No internet connection");

    private static <T> List<T> emptyList() {
        return new ArrayList<>();
    }

    // region getCoins()
    @Test
    public void testGetCoinsReturnsListFromRemoteSource() {
        // Arrange
        when(remoteSource.getCoins()).thenReturn(Single.just(getFakeCoins(SIZE)));
        when(localSource.getCoins()).thenReturn(Single.just(emptyList()));
        when(localSource.saveCoins(any(Coin[].class))).thenReturn(Completable.complete());
        // Act
        Single<List<Coin>> coins = repository.getCoins();
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);

        verify(remoteSource, times(1)).getCoins();
        verify(localSource, times(1)).getCoins();
        verify(localSource, times(1)).saveCoins(any(Coin[].class));
    }

    @Test
    public void testGetCoinsReturnsListFromLocalSource() {
        // Arrange
        when(remoteSource.getCoins()).thenReturn(Single.error(unknownHostException));
        when(localSource.getCoins()).thenReturn(Single.just(getFakeCoins(SIZE)));
        // Act
        Single<List<Coin>> coins = repository.getCoins();
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);

        verify(remoteSource, times(1)).getCoins();
        verify(localSource, times(1)).getCoins();
        verify(localSource, never()).saveCoins(any(Coin[].class));
    }

    @Test
    public void testGetCoinsReturnsExceptionOnNoData() {
        // Arrange
        when(remoteSource.getCoins()).thenReturn(Single.error(unknownHostException));
        when(localSource.getCoins()).thenReturn(Single.just(emptyList()));
        // Act
        Single<List<Coin>> coins = repository.getCoins();
        // Assert
        coins.test()
                .awaitCount(1)
                .assertError(UnknownHostException.class);

        verify(remoteSource, times(1)).getCoins();
        verify(localSource, times(1)).getCoins();
        verify(localSource, never()).saveCoins(any(Coin[].class));
    }
    // endregion

    // region getCoins(int limit, int offset)
    @Test
    public void testGetCoinsByLimitAndOffsetReturnsListFromRemoteSource() {
        // Arrange
        when(remoteSource.getCoins(anyInt(), anyInt())).thenReturn(Single.just(getFakeCoins(LIMIT)));
        when(localSource.getCoins(anyInt(), anyInt())).thenReturn(Single.just(emptyList()));
        when(localSource.saveCoins(any(Coin[].class))).thenReturn(Completable.complete());
        // Act
        Single<List<Coin>> coins = repository.getCoins(LIMIT, OFFSET);
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == LIMIT);

        verify(remoteSource, times(1)).getCoins(anyInt(), anyInt());
        verify(localSource, times(1)).getCoins(anyInt(), anyInt());
        verify(localSource, times(1)).saveCoins(any(Coin[].class));
    }

    @Test
    public void testGetCoinsByLimitAndOffsetReturnsListFromLocalSource() {
        // Arrange
        when(remoteSource.getCoins(anyInt(), anyInt())).thenReturn(Single.error(unknownHostException));
        when(localSource.getCoins(anyInt(), anyInt())).thenReturn(Single.just(getFakeCoins(LIMIT)));
        // Act
        Single<List<Coin>> coins = repository.getCoins(LIMIT, OFFSET);
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == LIMIT);

        verify(remoteSource, times(1)).getCoins(anyInt(), anyInt());
        verify(localSource, times(1)).getCoins(anyInt(), anyInt());
        verify(localSource, never()).saveCoins(any(Coin[].class));
    }

    @Test
    public void testGetCoinsByLimitAndOffsetReturnsExceptionOnEmptyData() {
        // Arrange
        when(remoteSource.getCoins(anyInt(), anyInt())).thenReturn(Single.error(unknownHostException));
        when(localSource.getCoins(anyInt(), anyInt())).thenReturn(Single.just(emptyList()));
        // Act
        Single<List<Coin>> coins = repository.getCoins(LIMIT, OFFSET);
        // Assert
        coins.test()
                .awaitCount(1)
                .assertError(UnknownHostException.class);

        verify(remoteSource, times(1)).getCoins(anyInt(), anyInt());
        verify(localSource, times(1)).getCoins(anyInt(), anyInt());
        verify(localSource, never()).saveCoins(any(Coin[].class));
    }
    // endregion

    // region getCoins(List<String> ids)
    @Test
    public void testGetCoinsByIdsReturnsListFromRemoteSource() {
        // Arrange
        List<String> ids = new ArrayList<>();
        ids.add(ID);

        when(remoteSource.getCoins(anyList())).thenReturn(Single.just(getFakeCoins(SIZE)));
        when(localSource.getCoins(anyList())).thenReturn(Single.just(getFakeCoins(SIZE)));
        when(localSource.saveCoins(any(Coin[].class))).thenReturn(Completable.complete());
        // Act
        Single<List<Coin>> coins = repository.getCoins(ids);
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);

        verify(remoteSource, times(1)).getCoins(anyList());
        verify(localSource, times(1)).getCoins(anyList());
        verify(localSource, times(1)).saveCoins(any(Coin[].class));
    }

    @Test
    public void testGetCoinsByIdsReturnsListFromLocalSource() {
        // Arrange
        List<String> ids = new ArrayList<>();
        ids.add(ID);

        when(remoteSource.getCoins(anyList())).thenReturn(Single.error(unknownHostException));
        when(localSource.getCoins(anyList())).thenReturn(Single.just(getFakeCoins(SIZE)));
        // Act
        Single<List<Coin>> coins = repository.getCoins(ids);
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);

        verify(remoteSource, times(1)).getCoins(anyList());
        verify(localSource, times(1)).getCoins(anyList());
        verify(localSource, never()).saveCoins(any(Coin[].class));
    }

    @Test
    public void testGetCoinsByIdsReturnsExceptionOnNoData() {
        // Arrange
        List<String> ids = new ArrayList<>();
        ids.add(ID);

        when(remoteSource.getCoins(anyList())).thenReturn(Single.error(unknownHostException));
        when(localSource.getCoins(anyList())).thenReturn(Single.just(getFakeCoins(SIZE)));
        // Act
        Single<List<Coin>> coins = repository.getCoins(ids);
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);

        verify(remoteSource, times(1)).getCoins(anyList());
        verify(localSource, times(1)).getCoins(anyList());
        verify(localSource, never()).saveCoins(any(Coin[].class));
    }

    @Test
    public void testGetCoinsByIdsReturnsEmptyArrayOnEmptyIds() {
        // Act
        Single<List<Coin>> coins = repository.getCoins(new ArrayList<>());
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == 0);
        verify(remoteSource, never()).getCoins(anyList());
        verify(localSource, never()).saveCoins(any(Coin[].class));
        verify(localSource, never()).getCoins(anyList());
    }
    // endregion

    // region searchCoins(String key)
    @Test
    public void testSearchCoinsReturnsListFromRemoteSource() {
        // Arrange
        when(remoteSource.searchCoins(anyString())).thenReturn(Single.just(getFakeCoins(SIZE)));
        when(localSource.searchCoins(anyString())).thenReturn(Single.just(emptyList()));
        when(localSource.saveCoins(any(Coin[].class))).thenReturn(Completable.complete());
        // Act
        Single<List<Coin>> coins = repository.searchCoins(ID);
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(remoteSource, times(1)).searchCoins(ID);
        verify(localSource, times(1)).searchCoins(ID);
        verify(localSource, times(1)).saveCoins(any(Coin[].class));
    }

    @Test
    public void testSearchCoinsReturnsListFromLocalSource() {
        // Arrange
        when(remoteSource.searchCoins(anyString())).thenReturn(Single.error(unknownHostException));
        when(localSource.searchCoins(anyString())).thenReturn(Single.just(getFakeCoins(SIZE)));
        // Act
        Single<List<Coin>> coins = repository.searchCoins(ID);
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);

        verify(remoteSource, times(1)).searchCoins(ID);
        verify(localSource, times(1)).searchCoins(ID);
        verify(localSource, never()).saveCoins(any(Coin[].class));
    }
    // endregion

    // region getCoin(String id)
    @Test
    public void testGetCoinByIdReturnsModelFromRemoteSource() {
        // Arrange
        when(remoteSource.getCoin(anyString())).thenReturn(Single.just(getCoin(ID)));
        when(localSource.getCoin(anyString())).thenReturn(Single.just(getCoin("NULL")));
        when(localSource.saveCoins(any(Coin.class))).thenReturn(Completable.complete());
        // Act
        Single<Coin> coin = repository.getCoin(ID);
        // Assert
        coin.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.id.equals(ID));

        verify(remoteSource, times(1)).getCoin(anyString());
        verify(localSource, times(1)).getCoin(anyString());
        verify(localSource, times(1)).saveCoins(any(Coin.class));
    }

    @Test
    public void testGetCoinByIdReturnsModelFromLocalSource() {
        // Arrange
        when(remoteSource.getCoin(anyString())).thenReturn(Single.error(unknownHostException));
        when(localSource.getCoin(anyString())).thenReturn(Single.just(getCoin(ID)));
        // Act
        Single<Coin> coin = repository.getCoin(ID);
        // Assert
        coin.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.id.equals(ID));

        verify(remoteSource, times(1)).getCoin(anyString());
        verify(localSource, times(1)).getCoin(anyString());
        verify(localSource, never()).saveCoins(any(Coin.class));
    }
    // endregion

    // region bookmarkCoin(String id)
    @Test
    public void testBookmarkCoinInsertsIdToDatabase() {
        // Arrange
        when(localSource.bookmarkCoin(anyString())).thenReturn(Completable.complete());
        // Act
        Completable coin = repository.bookmarkCoin(ID);
        // Assert
        coin.test()
                .assertComplete()
                .assertNoErrors();
        verify(localSource, times(1)).bookmarkCoin(anyString());
    }
    // endregion

    // region unBookmarkCoin(String id)
    @Test
    public void testUnBookmarkCoinDeletesIdFromDatabase() {
        // Arrange
        when(localSource.unBookmarkCoin(anyString())).thenReturn(Completable.complete());
        // Act
        Completable coin = repository.unBookmarkCoin(ID);
        // Assert
        coin.test()
                .assertComplete()
                .assertNoErrors();
        verify(localSource, times(1)).unBookmarkCoin(anyString());
    }
    // endregion

    // region isCoinBookmarked()
    @Test
    public void isCoinBookmarkedReturnsBooleanFromLocalSource() {
        // Arrange
        when(localSource.isCoinBookmarked(anyString())).thenReturn(Single.just(Boolean.TRUE));
        // Act
        Single<Boolean> isCoinBookmarked = repository.isCoinBookmarked(ID);
        // Assert
        isCoinBookmarked.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x);
        verify(localSource, times(1)).isCoinBookmarked(ID);
    }
    // endregion

    // region getBookmarkedCoins()
    @Test
    public void testGetBookmarkedCoinsReturnsListFromRemoteSource() {
        // Arrange
        List<ValueAndTimestamp<String>> ids = new ArrayList<>();
        ids.add(new ValueAndTimestamp<>(ID, TIMESTAMP));

        List<Coin> coinList = new ArrayList<>();
        coinList.add(getCoin(ID));

        when(remoteSource.getCoins(anyList())).thenReturn(Single.just(coinList));
        when(localSource.getBookmarkedCoins()).thenReturn(Single.just(emptyList()));
        when(localSource.getBookmarkedCoinIds()).thenReturn(Single.just(ids));
        when(localSource.saveCoins(any(Coin[].class))).thenReturn(Completable.complete());
        // Act
        Single<List<Coin>> bookmarkedCoins = repository.getBookmarkedCoins();
        // Assert
        bookmarkedCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == ids.size());
        verify(remoteSource, times(1)).getCoins(anyList());
        verify(localSource, times(1)).getBookmarkedCoinIds();
        verify(localSource, times(1)).getBookmarkedCoins();
        verify(localSource, times(1)).saveCoins(any(Coin[].class));
    }

    @Test
    public void testGetBookmarkedCoinsReturnsListFromLocalSource() {
        // Arrange
        List<ValueAndTimestamp<String>> ids = new ArrayList<>();
        ids.add(new ValueAndTimestamp<>(ID, TIMESTAMP));

        List<Coin> coinList = new ArrayList<>();
        coinList.add(getCoin(ID));

        when(remoteSource.getCoins(anyList())).thenReturn(Single.error(unknownHostException));
        when(localSource.getBookmarkedCoinIds()).thenReturn(Single.just(ids));
        when(localSource.getBookmarkedCoins()).thenReturn(Single.just(coinList));
        // Act
        Single<List<Coin>> bookmarkedCoins = repository.getBookmarkedCoins();
        // Assert
        bookmarkedCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == ids.size());
        verify(remoteSource, times(1)).getCoins(anyList());
        verify(localSource, times(1)).getBookmarkedCoinIds();
        verify(localSource, times(1)).getBookmarkedCoins();
        verify(localSource, never()).saveCoins(any(Coin[].class));
    }

    @Test
    public void testGetBookmarkedCoinsReturnsEmptyListOnEmptyIds() {
        // Arrange
        when(localSource.getBookmarkedCoinIds()).thenReturn(Single.just(new ArrayList<>()));
        // Act
        Single<List<Coin>> bookmarkedCoins = repository.getBookmarkedCoins();
        // Assert
        bookmarkedCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(List::isEmpty);

        verify(localSource, times(1)).getBookmarkedCoinIds();
        verify(remoteSource, never()).getCoins(anyList());
        verify(localSource, never()).getBookmarkedCoins();
    }

    // endregion

    // region getCoinsBySearchHistory()
    @Test
    public void getCoinsBySearchHistoryReturnsCoinsFromRemoteSource() {
        // Arrange
        List<String> fakeNames = Arrays.stream(IDS).collect(Collectors.toList());
        List<ValueAndTimestamp<String>> fakeSearchQueries = getFakeSearchQueries(fakeNames);

        when(localSource.getCoinSearchHistory()).thenReturn(Single.just(fakeSearchQueries));

        when(localSource.saveCoins(any(Coin[].class))).thenReturn(Completable.complete());
        when(remoteSource.getCoins(anyList())).thenReturn(Single.just(getFakeCoins(fakeNames)));
        when(localSource.getCoins(anyList())).thenReturn(Single.just(emptyList()));
        // Act
        Single<List<Coin>> getCoinsBySearchHistory = repository.getCoinsBySearchHistory();
        // Assert
        getCoinsBySearchHistory.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors()
                .assertValue(x -> x.size() == IDS.length);

        verify(localSource, times(1)).getCoinSearchHistory();

        verify(remoteSource, times(1)).getCoins(anyList());
        verify(localSource, times(1)).getCoins(anyList());
        verify(localSource, times(1)).saveCoins(any(Coin[].class));
    }

    @Test
    public void getCoinsBySearchHistoryReturnsCoinsFromLocalSource()  {
        // Arrange
        List<String> fakeNames = Arrays.stream(IDS).collect(Collectors.toList());
        List<ValueAndTimestamp<String>> fakeSearchQueries = getFakeSearchQueries(fakeNames);

        when(localSource.getCoinSearchHistory()).thenReturn(Single.just(fakeSearchQueries));

        when(remoteSource.getCoins(anyList())).thenReturn(Single.error(unknownHostException));
        when(localSource.getCoins(anyList())).thenReturn(Single.just(getFakeCoins(fakeNames)));
        // Act
        Single<List<Coin>> getCoinsBySearchHistory = repository.getCoinsBySearchHistory();
        // Assert
        getCoinsBySearchHistory.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors()
                .assertValue(x -> x.size() == IDS.length);

        verify(localSource, times(1)).getCoinSearchHistory();

        verify(remoteSource, times(1)).getCoins(anyList());
        verify(localSource, times(1)).getCoins(anyList());
        verify(localSource, never()).saveCoins(any(Coin[].class));
    }

    @Test
    public void getCoinsBySearchHistoryReturnsEmptyList()  {
        // Arrange
        when(localSource.getCoinSearchHistory()).thenReturn(Single.just(emptyList()));
        // Act
        Single<List<Coin>> getCoinsBySearchHistory = repository.getCoinsBySearchHistory();
        // Assert
        getCoinsBySearchHistory.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors()
                .assertValue(List::isEmpty);

        verify(localSource, times(1)).getCoinSearchHistory();

        verify(remoteSource, never()).getCoins(anyList());
        verify(localSource, never()).getCoins(anyList());
        verify(localSource, never()).saveCoins(any(Coin[].class));
    }
    // endregion

    // region insertCoinSearchQuery(String query)
    @Test
    public void insertCoinSearchQueryInsertsQueryToLocalSource() {
        // Arrange
        when(localSource.insertCoinSearchQuery(anyString())).thenReturn(Completable.complete());
        // Act
        Completable insertSearchQuery = repository.insertCoinSearchQuery(ID);
        // Assert
        insertSearchQuery.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();

        verify(localSource, times(1)).insertCoinSearchQuery(ID);
    }
    // endregion

    // region insertCoinSearchQuery(List<String> query)
    @Test
    public void insertCoinSearchQueryInsertsQueriesToLocalSource() {
        // Arrange
        List<String> queries = new ArrayList<>();
        queries.add(ID);
        queries.add(ID);
        queries.add(ID);

        when(localSource.insertCoinSearchQuery(any(String[].class))).thenReturn(Completable.complete());
        // Act
        Completable insertQueries = repository.insertCoinSearchQuery(queries);
        // Assert
        insertQueries.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        verify(localSource, times(1)).insertCoinSearchQuery(any(String[].class));
    }
    // endregion

    // region deleteCoinSearchQuery(String query)
    @Test
    public void deleteCoinSearchQueryDeletesQueryFromLocalSource() {
        // Arrange
        when(localSource.deleteCoinSearchQuery(anyString())).thenReturn(Completable.complete());
        // Act
        Completable deleteSearchQuery = repository.deleteCoinSearchQuery(ID);
        // Assert
        deleteSearchQuery.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();

        verify(localSource, times(1)).deleteCoinSearchQuery(ID);
    }
    // endregion

    // region eraseCoinSearchHistory()
    @Test
    public void deleteCoinSearchHistoryDeletesQueryDataFromLocalSource() {
        // Arrange
        when(localSource.eraseCoinSearchHistory()).thenReturn(Completable.complete());
        // Act
        Completable deleteSearchQuery = repository.eraseCoinSearchHistory();
        // Assert
        deleteSearchQuery.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();

        verify(localSource, times(1)).eraseCoinSearchHistory();
    }
    // endregion

    // region eraseBookmarks()
    @Test
    public void eraseBookmarksReturnsCompletable() {
        // Arrange
        when(localSource.eraseBookmarks()).thenReturn(Completable.complete());
        // Act
        final Completable deleteSearchQuery = repository.eraseBookmarks();
        // Assert
        deleteSearchQuery.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertComplete();

        verify(localSource, times(1)).eraseBookmarks();
    }
    // endregion

    // region eraseCoinCache()
    @Test
    public void eraseCoinCacheReturnsCompletable() {
        // Arrange
        when(localSource.eraseCoinCache()).thenReturn(Completable.complete());
        // Act
        final Completable deleteSearchQuery = repository.eraseCoinCache();
        // Assert
        deleteSearchQuery.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertComplete();

        verify(localSource, times(1)).eraseCoinCache();
    }
    // endregion

    // region getCoinPriceHistory(String id, Interval interval)
    @Test
    public void getCoinPriceHistoryReturnsListFromRemoteSource() {
        // Arrange
        when(remoteSource.getCoinPriceHistory(anyString(), any(CoinPriceHistory.Interval.class))).thenReturn(Single.just(getFakePriceHistory(SIZE)));
        // Act
        Single<List<CoinPriceHistory>> history = repository.getCoinPriceHistory(ID, CoinPriceHistory.Interval.D1);
        // Assert
        history.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);

        verify(remoteSource, times(1)).getCoinPriceHistory(ID, CoinPriceHistory.Interval.D1);
    }
    // endregion

    // region getTradesByCoin(String id);
    @Test
    public void getTradesByCoinIdReturnsTradesFromRemote() {
        // Arrange
        when(remoteSource.getTradesByCoin(anyString())).thenReturn(Single.just(getFakeTrades(SIZE)));
        // Act
        final Single<List<Trade>> getTradesByCoin = repository.getTradesByCoin(ID);
        // Assert
        getTradesByCoin.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);

        verify(remoteSource, times(1)).getTradesByCoin(anyString());
    }
    // endregion

    // region getTradesByCoin(String id, int limit, int offset);
    @Test
    public void getTradesByCoinIdAndLimitAndOffsetReturnsTradesFromRemote() {
        // Arrange
        List<Trade> trades = getFakeTrades(SIZE);
        // Act
        when(remoteSource.getTradesByCoin(anyString(), anyInt(), anyInt())).thenReturn(Single.just(trades));
        //Assert
        repository.getTradesByCoin(ID, LIMIT, OFFSET).test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);

        verify(remoteSource, times(1)).getTradesByCoin(ID, LIMIT, OFFSET);
    }
    //endregion

}

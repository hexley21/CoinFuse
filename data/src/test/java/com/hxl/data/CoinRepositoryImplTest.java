package com.hxl.data;

import static com.hxl.data.fakes.DataTestConstants.ID;
import static com.hxl.data.fakes.DataTestConstants.LIMIT;
import static com.hxl.data.fakes.DataTestConstants.OFFSET;
import static com.hxl.data.fakes.DataTestConstants.SIZE;
import static com.hxl.data.fakes.DataTestConstants.TIMESTAMP;
import static com.hxl.data.fakes.FakeDataFactory.getCoin;
import static com.hxl.data.fakes.FakeDataFactory.getFakeCoins;
import static com.hxl.data.fakes.FakeDataFactory.getFakePriceHistory;
import static com.hxl.data.fakes.FakeDataFactory.getFakeSearchQueries;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hxl.data.repository.coin.CoinLocal;
import com.hxl.data.repository.coin.CoinRemote;
import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.domain.model.ValueAndTimestamp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class CoinRepositoryImplTest {

    @Mock
    CoinRemote remoteSource;
    @Mock
    CoinLocal localSource;
    CoinRepositoryImpl repository;

    @Before
    public void setUp() {
        repository = spy(new CoinRepositoryImpl(remoteSource, localSource));
    }


    // region getCoins()
    @Test
    public void testGetCoinsReturnsListFromRemoteSource() {
        // Arrange
        when(remoteSource.getCoins()).thenReturn(Single.just(getFakeCoins(SIZE)));
        when(repository.saveCoins(anyList())).thenReturn(Completable.complete());
        when(repository.isOnline()).thenReturn(true);
        // Act
        Single<List<Coin>> coins = repository.getCoins();
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(remoteSource, times(1)).getCoins();
        verify(repository, times(1)).saveCoins(anyList());
        verify(repository, times(1)).isOnline();
        verify(localSource, never()).getCoins();
    }

    @Test
    public void testGetCoinsReturnsListFromLocalSource() {
        // Arrange
        when(localSource.getCoins()).thenReturn(Single.just(getFakeCoins(SIZE)));
        when(repository.isOnline()).thenReturn(false);
        // Act
        Single<List<Coin>> coins = repository.getCoins();
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(localSource, times(1)).getCoins();
        verify(repository, times(1)).isOnline();
        verify(remoteSource, never()).getCoins(anyInt(), anyInt());
    }
    // endregion

    // region getCoins(int limit, int offset)
    @Test
    public void testGetCoinsByLimitAndOffsetReturnsListFromRemoteSource() {
        // Arrange
        when(remoteSource.getCoins(anyInt(), anyInt())).thenReturn(Single.just(getFakeCoins(SIZE)));
        when(repository.saveCoins(anyList())).thenReturn(Completable.complete());
        when(repository.isOnline()).thenReturn(true);
        // Act
        Single<List<Coin>> coins = repository.getCoins(LIMIT, OFFSET);
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(remoteSource, times(1)).getCoins(anyInt(), anyInt());
        verify(repository, times(1)).saveCoins(anyList());
        verify(repository, times(1)).isOnline();
        verify(localSource, never()).getCoins(anyInt(), anyInt());
    }

    @Test
    public void testGetCoinsByLimitAndOffsetReturnsListFromLocalSource() {
        // Arrange
        when(localSource.getCoins(anyInt(), anyInt())).thenReturn(Single.just(getFakeCoins(SIZE)));
        when(repository.isOnline()).thenReturn(false);
        // Act
        Single<List<Coin>> coins = repository.getCoins(LIMIT, OFFSET);
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(localSource, times(1)).getCoins(anyInt(), anyInt());
        verify(repository, times(1)).isOnline();
        verify(remoteSource, never()).getCoins(anyInt(), anyInt());
    }
    // endregion

    // region getCoins(List<String> ids)
    @Test
    public void testGetCoinsByIdsReturnsListFromRemoteSource() {
        // Arrange
        List<String> ids = new ArrayList<>();
        ids.add(ID);
        when(remoteSource.getCoins(anyList())).thenReturn(Single.just(getFakeCoins(SIZE)));
        when(repository.saveCoins(anyList())).thenReturn(Completable.complete());
        when(repository.isOnline()).thenReturn(true);
        // Act
        Single<List<Coin>> coins = repository.getCoins(ids);
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(remoteSource, times(1)).getCoins(anyList());
        verify(repository, times(1)).saveCoins(anyList());
        verify(repository, times(1)).isOnline();
        verify(localSource, never()).getCoins(anyList());
    }

    @Test
    public void testGetCoinsByIdsReturnsListFromLocalSource() {
        // Arrange
        List<String> ids = new ArrayList<>();
        ids.add(ID);
        when(localSource.getCoins(anyList())).thenReturn(Single.just(getFakeCoins(SIZE)));
        when(repository.isOnline()).thenReturn(false);
        // Act
        Single<List<Coin>> coins = repository.getCoins(ids);
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(localSource, times(1)).getCoins(anyList());
        verify(repository, times(1)).isOnline();
        verify(remoteSource, never()).getCoins(anyList());
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
        verify(repository, never()).saveCoins(anyList());
        verify(repository, never()).isOnline();
        verify(localSource, never()).getCoins(anyList());
    }
    // endregion

    // region searchCoins(String key)
    @Test
    public void testSearchCoinsReturnsListFromRemoteSource() {
        // Arrange
        when(remoteSource.searchCoins(anyString())).thenReturn(Single.just(getFakeCoins(SIZE)));
        when(repository.saveCoins(anyList())).thenReturn(Completable.complete());
        when(repository.isOnline()).thenReturn(true);
        // Act
        Single<List<Coin>> coins = repository.searchCoins(ID);
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(remoteSource, times(1)).searchCoins(ID);
        verify(repository, times(1)).saveCoins(anyList());
        verify(repository, times(1)).isOnline();
        verify(localSource, never()).searchCoins(ID);
    }

    @Test
    public void testSearchCoinsReturnsListFromLocalSource() {
        // Arrange
        when(localSource.searchCoins(anyString())).thenReturn(Single.just(getFakeCoins(SIZE)));
        when(repository.isOnline()).thenReturn(false);
        // Act
        Single<List<Coin>> coins = repository.searchCoins(ID);
        // Assert
        coins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(localSource, times(1)).searchCoins(anyString());
        verify(repository, times(1)).isOnline();
        verify(remoteSource, never()).searchCoins(anyString());
    }
    // endregion

    // region getCoin(String id)
    @Test
    public void testGetCoinByIdReturnsModelFromRemoteSource() {
        // Arrange
        when(remoteSource.getCoin(anyString())).thenReturn(Single.just(getCoin(ID)));
        when(repository.saveCoin(any(Coin.class))).thenReturn(Completable.complete());
        when(repository.isOnline()).thenReturn(true);
        // Act
        Single<Coin> coin = repository.getCoin(ID);
        // Assert
        coin.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.id.equals(ID));
        verify(remoteSource, times(1)).getCoin(anyString());
        verify(repository, times(1)).saveCoin(any(Coin.class));
        verify(repository, times(1)).isOnline();
        verify(localSource, never()).getCoin(anyString());
    }

    @Test
    public void testGetCoinByIdReturnsModelFromLocalSource() {
        // Arrange
        when(localSource.getCoin(anyString())).thenReturn(Single.just(getCoin(ID)));
        when(repository.isOnline()).thenReturn(false);
        // Act
        Single<Coin> coin = repository.getCoin(ID);
        // Assert
        coin.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.id.equals(ID));
        verify(localSource, times(1)).getCoin(anyString());
        verify(repository, times(1)).isOnline();
        verify(remoteSource, never()).getCoin(anyString());
    }
    // endregion

    // region saveCoins(List<Coin> coins)
    @Test
    public void saveCoinsInsertsCoinsToLocalSource() {
        // Arrange
        List<Coin> fakeCoins = getFakeCoins(SIZE);
        when(localSource.saveCoins(any(Coin[].class))).thenReturn(Completable.complete());
        // Act
        Completable saveCoins = repository.saveCoins(fakeCoins);
        // Assert
        saveCoins.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        verify(localSource, times(1)).saveCoins(fakeCoins.toArray(new Coin[0]));
    }
    // endregion

    // region saveCoin(Coin coin)
    @Test
    public void saveCoinInsertsCoinToLocalSource() {
        // Arrange
        Coin fakeCoin = getCoin(ID);
        when(localSource.saveCoins(any(Coin.class))).thenReturn(Completable.complete());
        // Act
        Completable saveCoins = repository.saveCoin(fakeCoin);
        // Assert
        saveCoins.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        verify(localSource, times(1)).saveCoins(fakeCoin);
    }
    // endregion

    // region getCoinHistory(String id, Interval interval)
    @Test
    public void getCoinHistoryReturnsListFromRemoteSource() {
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
        verify(repository, times(1)).getCoinPriceHistory(ID, CoinPriceHistory.Interval.D1);
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

    // region getBookmarkedCoins()
    @Test
    public void testGetBookmarkedCoinsReturnsListFromRemoteSource() {
        // Arrange
        List<ValueAndTimestamp<String>> ids = new ArrayList<>();
        ids.add(new ValueAndTimestamp<>(ID, TIMESTAMP));

        List<Coin> coinList = new ArrayList<>();
        coinList.add(getCoin(ID));

        when(remoteSource.getCoins(anyList())).thenReturn(Single.just(coinList));
        when(repository.saveCoins(anyList())).thenReturn(Completable.complete());
        when(localSource.getBookmarkedCoinIds()).thenReturn(Single.just(ids));
        when(repository.isOnline()).thenReturn(true);
        // Act
        Single<List<Coin>> bookmarkedCoins = repository.getBookmarkedCoins();
        // Assert
        bookmarkedCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == ids.size());
        verify(remoteSource, times(1)).getCoins(anyList());
        verify(localSource, never()).getBookmarkedCoins();
        verify(repository, times(1)).getBookmarkedCoins();
    }

    @Test
    public void testGetBookmarkedCoinsReturnsEmptyListOnEmptyIds() {
        // Arrange
        when(localSource.getBookmarkedCoinIds()).thenReturn(Single.just(new ArrayList<>()));
        when(repository.isOnline()).thenReturn(true);
        // Act
        Single<List<Coin>> bookmarkedCoins = repository.getBookmarkedCoins();
        // Assert
        bookmarkedCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(List::isEmpty);
        verify(remoteSource, never()).getCoins(anyList());
        verify(localSource, never()).getBookmarkedCoins();
        verify(repository, times(1)).getBookmarkedCoins();
    }

    @Test
    public void testGetBookmarkedCoinsReturnsListFromLocalSource() {
        // Arrange
        when(localSource.getBookmarkedCoins()).thenReturn(Single.just(getFakeCoins(SIZE)));
        when(repository.isOnline()).thenReturn(false);
        // Act
        Single<List<Coin>> bookmarkedCoins = repository.getBookmarkedCoins();
        // Assert
        bookmarkedCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        verify(localSource, times(1)).getBookmarkedCoins();
        verify(remoteSource, never()).getCoins(anyList());
        verify(repository, times(1)).getBookmarkedCoins();
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
        verify(repository, times(1)).isCoinBookmarked(ID);
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
        verify(repository, times(1)).insertCoinSearchQuery(ID);
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
        verify(localSource, times(1)).insertCoinSearchQuery(queries.toArray(new String[0]));
        verify(repository, times(1)).insertCoinSearchQuery(queries);
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
        verify(repository, times(1)).deleteCoinSearchQuery(ID);
    }
    // endregion

    // region deleteCoinSearchHistory()
    @Test
    public void deleteCoinSearchHistoryDeletesQueryDataFromLocalSource() {
        // Arrange
        when(localSource.deleteCoinSearchHistory()).thenReturn(Completable.complete());
        // Act
        Completable deleteSearchQuery = repository.deleteCoinSearchHistory();
        // Assert
        deleteSearchQuery.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();

        verify(localSource, times(1)).deleteCoinSearchHistory();
        verify(repository, times(1)).deleteCoinSearchHistory();
    }
    // endregion

}

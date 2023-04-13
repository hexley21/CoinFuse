package com.hxl.local;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.hxl.domain.model.Coin;
import com.hxl.local.database.BookmarkDao;
import com.hxl.local.database.CoinDao;
import com.hxl.local.database.CoinDatabase;
import com.hxl.local.fake.FakeLocalDataFactory;
import com.hxl.local.model.BookmarkEntity;
import com.hxl.local.model.CoinEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static com.hxl.local.fake.LocalTestConstants.*;

import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4.class)
public class CoinLocalImplTest {
    private CoinDatabase database;
    private CoinDao coinDao;
    private BookmarkDao bookmarkDao;
    private CoinLocalImpl coinSource;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, CoinDatabase.class)
                .allowMainThreadQueries()
                .build();
        coinDao = database.coinDao();
        bookmarkDao = database.bookmarkDao();
        coinSource = new CoinLocalImpl(coinDao, bookmarkDao);
    }

    @After
    public void tearDown() {
        database.clearAllTables();
        database.close();
    }

    @Test
    public void getCoinsReadsDataFomDatabase() {
        // Arrange
        List<CoinEntity> entities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(entities.toArray(new CoinEntity[0]));
        Single<List<Coin>> getCoins = coinSource.getCoins();
        // Assert
        saveCoins.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        getCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> {
                    for (int i = 0; i < KEYS.length; i++) {
                        if (!d.get(i).id.equals(KEYS[i])){
                            return false;
                        }
                    }
                    return true;
                });
    }

    @Test
    public void getCoinsByLimitAndOffsetReadsDataFromDatabase() {
        // Arrange
        List<CoinEntity> entities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(entities.toArray(new CoinEntity[0]));
        Single<List<Coin>> getCoins = coinSource.getCoins(LIMIT, OFFSET);
        // Assert
        saveCoins.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        getCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> {
                    for (int i = 0; i < LIMIT; i++) {
                        if (!d.get(i).id.equals(KEYS[i+OFFSET])){
                            return false;
                        }
                    }
                    return true;
                });
    }

    @Test
    public void getCoinsByIdsReadsData() {
        // Arrange
        List<CoinEntity> entities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(entities.toArray(new CoinEntity[0]));
        Single<List<Coin>> getCoins = coinSource.getCoins(IDS);
        // Assert
        saveCoins.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        getCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> {
                    for (int i = 0; i < IDS_LENGTH; i++) {
                        if (!d.get(i).id.equals(IDS.get(i))){
                            return false;
                        }
                    }
                    return true;
                });
    }
    @Test
    public void searchCoinsReadsDataFromDatabase() {
        // Arrange
        List<CoinEntity> entities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(entities.toArray(new CoinEntity[0]));
        Single<List<Coin>> searchCoins = coinSource.searchCoins(ID);
        // Assert
        saveCoins.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        searchCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> {
                    for (int i = 0; i < d.size(); i++) {
                        if (!d.get(i).id.equals(KEYS[i])) {
                            return false;
                        }
                    }
                    return true;
                });
    }

    @Test
    public void getCoinByIdReadsData() {
        // Arrange
        List<CoinEntity> entities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(entities.toArray(new CoinEntity[0]));
        Single<Coin> getCoin = coinSource.getCoin(ID);
        // Assert
        saveCoins.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        getCoin.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> d.id.equals(ID));
    }

    @Test
    public void bookmarkCoinInsertsIdToDatabase() {
        // Act
        Single<List<BookmarkEntity>> getBookmarkedCoin = bookmarkDao.getBookmarkedCoinIds();
        Completable bookmarkCoin = coinSource.bookmarkCoin(ID);
        // Assert
        bookmarkCoin.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        getBookmarkedCoin.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> d.get(0).id.equals(ID));
    }

    @Test
    public void unBookmarkCoinDeletesEntryFromDatabase() {
        // Arrange
        BookmarkEntity bookmarkEntity = new BookmarkEntity(ID, TIMESTAMP);
        // Act
        Single<List<BookmarkEntity>> getBookmarkedCoin = bookmarkDao.getBookmarkedCoinIds();
        Completable bookmarkCoin = bookmarkDao.bookmarkCoin(bookmarkEntity);
        Completable unBookmarkCoin = coinSource.unBookmarkCoin(ID);
        // Assert
        bookmarkCoin.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        getBookmarkedCoin.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> d.size() == 1);
        unBookmarkCoin.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        getBookmarkedCoin.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> d.size() == 0);
    }

    @Test
    public void getBookmarkedCoinsReadsCoinsFromDatabase() {
        // Arrange
        List<CoinEntity> coinEntities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(coinEntities.toArray(new CoinEntity[0]));
        Single<List<Coin>> getBookmarkedCoins = coinSource.getBookmarkedCoins();
        // Assert
        saveCoins.test().assertNoErrors();
        for (int i = 0; i < IDS_LENGTH; i++) {
            bookmarkDao.bookmarkCoin(new BookmarkEntity(IDS.get(i), TIMESTAMP*i))
                    .test()
                    .assertComplete()
                    .assertNoErrors();
        }
        getBookmarkedCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> {
                    for (int i = 0; i < IDS_LENGTH; i++) {
                        if (!d.get(i).id.equals(IDS.get(IDS_LENGTH-i-1))) {
                            return false;
                        }
                    }
                    return true;
                });
    }
    @Test
    public void getBookmarkedCoinIdsReadsIdsFromDatabase() {
        // Arrange
        List<CoinEntity> coinEntities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(coinEntities.toArray(new CoinEntity[0]));
        Single<List<String>> getBookmarkedCoinIds = coinSource.getBookmarkedCoinIds();
        // Assert
        saveCoins.test().assertNoErrors();

        for (int i = 0; i < IDS_LENGTH; i++) {
            bookmarkDao.bookmarkCoin(new BookmarkEntity(IDS.get(i), TIMESTAMP * i))
                    .test()
                    .awaitCount(1)
                    .assertComplete()
                    .assertNoErrors();
        }
        getBookmarkedCoinIds.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> {
                    for (int i = 0; i < IDS_LENGTH; i++) {
                        if (!d.get(i).equals(IDS.get(IDS_LENGTH-i-1))) {
                            return false;
                        }
                    }
                    return true;
                });
    }

    @Test
    public void saveCoinsInsertsCoinsToDatabase() {
        // Arrange
        Coin[] coins = FakeLocalDataFactory.getFakeCoins(KEYS).toArray(new Coin[0]);
        // Act
        Single<List<CoinEntity>> getCoins = coinDao.getCoins();
        Completable saveCoins = coinSource.saveCoins(coins);
        // Assert
        saveCoins.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        getCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> d.size() == KEYS.length)
                .assertValue(d -> {
                    for (int i = 0; i < KEYS.length; i++) {
                        if (!d.get(i).id.equals(KEYS[i])) {
                            return false;
                        }
                    }
                    return true;
                });
    }
}

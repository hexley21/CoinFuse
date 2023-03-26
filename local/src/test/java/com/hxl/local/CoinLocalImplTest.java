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
    public void get_coins_reads_data() {
        // Arrange
        List<CoinEntity> entities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(entities.toArray(new CoinEntity[0]));
        Single<List<Coin>> getCoins = coinSource.getCoins();
        // Assert
        saveCoins.test()
                .assertComplete()
                .assertNoErrors();
        getCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> {
                    for (int i = 0; i < d.size(); i++) {
                        if (!d.get(i).id.equals(KEYS[i])){
                            return false;
                        }
                    }
                    return true;
                });
    }

    @Test
    public void get_coins_by_limit_and_offset_reads_data() {
        // Arrange
        List<CoinEntity> entities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(entities.toArray(new CoinEntity[0]));
        Single<List<Coin>> getCoins = coinSource.getCoins(LIMIT, OFFSET);
        // Assert
        saveCoins.test()
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
    public void get_coins_by_ids_reads_data() {
        // Arrange
        List<CoinEntity> entities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(entities.toArray(new CoinEntity[0]));
        Single<List<Coin>> getCoins = coinSource.getCoins(IDS);
        // Assert
        saveCoins.test()
                .assertComplete()
                .assertNoErrors();
        getCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> {
                    for (int i = 0; i < IDS.size(); i++) {
                        if (!d.get(i).id.equals(IDS.get(i))){
                            return false;
                        }
                    }
                    return true;
                });
    }
    @Test
    public void search_coins_reads_data() {
        // Arrange
        List<CoinEntity> entities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(entities.toArray(new CoinEntity[0]));
        Single<List<Coin>> searchCoins = coinSource.searchCoins(ID);
        // Assert
        saveCoins.test()
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
    public void get_coin_by_id_reads_data() {
        // Arrange
        List<CoinEntity> entities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(entities.toArray(new CoinEntity[0]));
        Single<Coin> getCoin = coinSource.getCoin(ID);
        // Assert
        saveCoins.test()
                .assertComplete()
                .assertNoErrors();
        getCoin.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> d.id.equals(ID));
    }

    @Test
    public void bookmark_coin_inserts_id() {
        // Act
        Single<List<BookmarkEntity>> getBookmarkedCoin = bookmarkDao.getBookmarkedCoinIds();
        Completable bookmarkCoin = coinSource.bookmarkCoin(ID);
        // Assert
        bookmarkCoin.test()
                .assertComplete()
                .assertNoErrors();
        getBookmarkedCoin.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> d.get(0).id.equals(ID));
    }

    @Test
    public void un_bookmark_coin_deletes_entry() {
        // Arrange
        BookmarkEntity bookmarkEntity = new BookmarkEntity(ID, TIMESTAMP);
        // Act
        Single<List<BookmarkEntity>> getBookmarkedCoin = bookmarkDao.getBookmarkedCoinIds();
        Completable bookmarkCoin = bookmarkDao.bookmarkCoin(bookmarkEntity);
        Completable unBookmarkCoin = coinSource.unBookmarkCoin(ID);
        // Assert
        bookmarkCoin.test()
                .assertComplete()
                .assertNoErrors();
        getBookmarkedCoin.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> d.size() == 1);
        unBookmarkCoin.test()
                .assertComplete()
                .assertNoErrors();
        getBookmarkedCoin.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> d.size() == 0);
    }

    @Test
    public void get_bookmarked_coins_reads_coins() {
        // Arrange
        List<CoinEntity> entities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(entities.toArray(new CoinEntity[0]));
        Single<List<Coin>> getBookmarkedCoins = coinSource.getBookmarkedCoins();
        // Assert
        saveCoins.test()
                .assertNoErrors()
                .assertComplete();
        for (String i: IDS) {
            bookmarkDao.bookmarkCoin(new BookmarkEntity(i, System.currentTimeMillis()))
                    .test()
                    .assertComplete()
                    .assertNoErrors();
        }
        getBookmarkedCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> {
                    System.out.println(d.size());
                    for (int i = 0; i < IDS.size(); i++) {
                        if (!d.get(i).id.equals(IDS.get(IDS.size()-1-i))) {
                            return false;
                        }
                    }
                    return true;
                });
    }
    @Test
    public void get_bookmarked_coin_ids_reads_ids() {
        // Act
        Single<List<String>> getBookmarkedIds = coinSource.getBookmarkedCoinIds();
        // Assert
        for (String i: KEYS) {
            bookmarkDao.bookmarkCoin(new BookmarkEntity(i, System.currentTimeMillis()))
                    .test()
                    .assertComplete()
                    .assertNoErrors();
        }
        getBookmarkedIds.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> d.size() == KEYS.length)
                .assertValue(d -> {
                    for (int i = 0; i < KEYS.length; i++) {
                        if (!d.get(i).equals(KEYS[KEYS.length-1-i])) {
                            return false;
                        }
                    }
                    return true;
                });
    }

}

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
    public void get_coins_returns_data_from_database() {
        // Arrange
        List<CoinEntity> entities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(entities.toArray(new CoinEntity[0]));
        Single<List<Coin>> getCoins = coinSource.getCoins();
        // Assert
        saveCoins.test()
                .assertNoErrors()
                .assertComplete();
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
    public void get_coins_by_limit_and_offset_returns_data_from_database() {
        // Arrange
        List<CoinEntity> entities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(entities.toArray(new CoinEntity[0]));
        Single<List<Coin>> getCoins = coinSource.getCoins(LIMIT, OFFSET);
        // Assert
        saveCoins.test()
                .assertNoErrors()
                .assertComplete();
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
    public void get_coins_by_ids_returns_data_from_database() {
        // Arrange
        List<CoinEntity> entities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(entities.toArray(new CoinEntity[0]));
        Single<List<Coin>> getCoins = coinSource.getCoins(IDS);
        // Assert
        saveCoins.test()
                .assertNoErrors()
                .assertComplete();
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
    public void search_coins_returns_data_from_database() {
        // Arrange
        List<CoinEntity> entities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(entities.toArray(new CoinEntity[0]));
        Single<List<Coin>> getCoins = coinSource.searchCoins(ID);
        // Assert
        saveCoins.test()
                .assertNoErrors()
                .assertComplete();
        getCoins.test()
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
    public void get_coin_by_id_returns_data_from_database() {
        // Arrange
        List<CoinEntity> entities = FakeLocalDataFactory.getFakeData(KEYS);
        // Act
        Completable saveCoins = coinDao.addCoin(entities.toArray(new CoinEntity[0]));
        Single<Coin> getCoins = coinSource.getCoin(ID);
        // Assert
        saveCoins.test()
                .assertNoErrors()
                .assertComplete();
        getCoins.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(d -> d.id.equals(ID));
    }
}

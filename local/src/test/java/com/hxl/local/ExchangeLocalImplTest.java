package com.hxl.local;

import static com.hxl.local.fake.FakeLocalDataFactory.getExchangeEntity;
import static com.hxl.local.fake.FakeLocalDataFactory.getFakeExchangeEntities;
import static com.hxl.local.fake.FakeLocalDataFactory.getFakeExchanges;
import static com.hxl.local.fake.LocalTestConstants.ID;
import static com.hxl.local.fake.LocalTestConstants.LIMIT;
import static com.hxl.local.fake.LocalTestConstants.OFFSET;
import static com.hxl.local.fake.LocalTestConstants.SIZE;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.hxl.domain.model.Exchange;
import com.hxl.local.exchange.dao.ExchangeDao;
import com.hxl.local.exchange.model.ExchangeEntity;
import com.hxl.local.room.AppDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4.class)
public class ExchangeLocalImplTest {

    private AppDatabase database;
    private ExchangeDao exchangeDao;
    private ExchangeLocalImpl exchangeLocal;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        exchangeDao = database.exchangeDao();
        exchangeLocal = new ExchangeLocalImpl(exchangeDao);
    }

    @After
    public void tearDown() {
        database.clearAllTables();
        database.close();
    }

    @Test
    public void getExchangesReturnsModelsFromDatabase() {
        // Arrange
        ExchangeEntity[] fakeExchanges = getFakeExchangeEntities(SIZE);
        // Act
        Completable insertExchanges = exchangeDao.insertExchanges(fakeExchanges);
        Single<List<Exchange>> exchanges = exchangeLocal.getExchanges();
        // Assert
        insertExchanges.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        exchanges.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
    }

    @Test
    public void getExchangesWithLimitAndOffsetReturnsModelFromDatabase() {
        // Arrange
        ExchangeEntity[] fakeExchanges = getFakeExchangeEntities(SIZE);
        // Act
        Completable insertExchanges = exchangeDao.insertExchanges(fakeExchanges);
        Single<List<Exchange>> exchanges = exchangeLocal.getExchanges(LIMIT, OFFSET);
        // Assert
        insertExchanges.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        exchanges.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == LIMIT);
    }

    @Test
    public void getExchangeByExchangeIdReturnsModelFromDatabase() {
        // Arrange
        ExchangeEntity fakeExchange = getExchangeEntity(ID);
        // Act
        Completable insertExchanges = exchangeDao.insertExchanges(fakeExchange);
        Single<Exchange> exchanges = exchangeLocal.getExchange(ID);
        // Assert
        insertExchanges.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        exchanges.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.exchangeId.equals(fakeExchange.exchangeId));
    }

    @Test
    public void insertExchangesInsertsEntitiesToDatabase() {
        // Arrange
        Exchange[] fakeExchanges = getFakeExchanges(SIZE);
        // Act
        Completable insertExchanges = exchangeLocal.insertExchange(fakeExchanges);
        Single<List<ExchangeEntity>> exchanges = exchangeDao.getExchanges();
        // Assert
        exchanges.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == 0);
        insertExchanges.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        exchanges.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
    }

    @Test
    public void eraseExchangesErasesDataFromDatabase() {
        // Arrange
        ExchangeEntity[] fakeExchanges = getFakeExchangeEntities(SIZE);
        // Act
        Completable insertExchanges = exchangeDao.insertExchanges(fakeExchanges);
        Single<List<ExchangeEntity>> exchanges = exchangeDao.getExchanges();
        Completable eraseExchanges = exchangeLocal.eraseExchanges();
        // Assert
        insertExchanges.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        exchanges.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == SIZE);
        eraseExchanges.test()
                .awaitCount(1)
                .assertComplete()
                .assertNoErrors();
        exchanges.test()
                .awaitCount(1)
                .assertNoErrors()
                .assertValue(x -> x.size() == 0);
    }
}

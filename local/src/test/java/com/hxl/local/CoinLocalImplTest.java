package com.hxl.local;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.hxl.local.database.BookmarkDao;
import com.hxl.local.database.CoinDao;
import com.hxl.local.database.CoinDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;


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

}

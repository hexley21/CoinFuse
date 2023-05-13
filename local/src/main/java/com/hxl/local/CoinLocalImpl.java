package com.hxl.local;

import androidx.annotation.NonNull;

import com.hxl.data.repository.coin.CoinLocal;
import com.hxl.domain.model.Coin;
import com.hxl.domain.model.ValueAndTimestamp;
import com.hxl.local.coin.dao.BookmarkDao;
import com.hxl.local.coin.dao.CoinDao;
import com.hxl.local.coin.dao.CoinSearchDao;
import com.hxl.local.coin.mapper.BookmarkEntityMapper;
import com.hxl.local.coin.mapper.CoinSearchEntityMapper;
import com.hxl.local.coin.model.BookmarkEntity;
import com.hxl.local.coin.model.CoinEntity;
import com.hxl.local.coin.mapper.CoinEntityMapper;
import com.hxl.local.coin.model.CoinSearchEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CoinLocalImpl implements CoinLocal {

    private final CoinDao coinDao;
    private final BookmarkDao bookmarkDao;
    private final CoinSearchDao searchHistoryDao;

    public CoinLocalImpl(CoinDao coinDao, BookmarkDao bookmarkDao, CoinSearchDao searchHistoryDao) {
        this.coinDao = coinDao;
        this.bookmarkDao = bookmarkDao;
        this.searchHistoryDao = searchHistoryDao;
    }

    @Override
    public Single<List<Coin>> getCoins() {
        return coinDao.getCoins()
                .subscribeOn(Schedulers.io())
                .map(this::mapFromEntity);
    }

    @Override
    public Single<List<Coin>> getCoins(int limit, int offset) {
        return coinDao.getCoins(limit, offset)
                .subscribeOn(Schedulers.io())
                .map(this::mapFromEntity);
    }

    @Override
    public Single<List<Coin>> getCoins(List<String> ids) {
        return coinDao.getCoins(ids)
                .subscribeOn(Schedulers.io())
                .map(this::mapFromEntity);
    }

    @Override
    public Single<List<Coin>> searchCoins(String key) {
        return coinDao.searchCoins(key)
                .subscribeOn(Schedulers.io())
                .map(this::mapFromEntity);
    }

    @Override
    public Single<Coin> getCoin(String id) {
        return coinDao.getCoin(id)
                .subscribeOn(Schedulers.io())
                .map(CoinEntityMapper::mapFromEntity);
    }

    @Override
    public Completable bookmarkCoin(String id) {
        return bookmarkDao.bookmarkCoin(new BookmarkEntity(id, System.currentTimeMillis()))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable unBookmarkCoin(String id) {
        return bookmarkDao.unBookmarkCoin(id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Boolean> isCoinBookmarked(String id) {
        return bookmarkDao.isCoinBookmarked(id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Coin>> getBookmarkedCoins() {
        return bookmarkDao.getBookmarkedCoins()
                .subscribeOn(Schedulers.io())
                .map(this::mapFromEntity);
    }

    @Override
    public Single<List<ValueAndTimestamp<String>>> getBookmarkedCoinIds() {
        return bookmarkDao.getBookmarkedCoinIds()
                .subscribeOn(Schedulers.io())
                .map(x -> x.stream().map(BookmarkEntityMapper::mapFromEntity).collect(Collectors.toList()));
    }

    @Override
    public Single<List<ValueAndTimestamp<String>>> getCoinSearchHistory() {
        return searchHistoryDao.getCoinSearchHistory()
                .subscribeOn(Schedulers.io())
                .map(x -> x.stream().map(CoinSearchEntityMapper::mapFromEntity).collect(Collectors.toList()));
    }

    @Override
    public Completable insertCoinSearchQuery(String query) {
        return searchHistoryDao.insertCoinSearchQuery(new CoinSearchEntity(query, System.currentTimeMillis()))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable insertCoinSearchQuery(String... query) {
        return searchHistoryDao.insertCoinSearchQuery(
                Arrays.stream(query).map(
                        x -> new CoinSearchEntity(x, System.currentTimeMillis()))
                        .toArray(CoinSearchEntity[]::new))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteCoinSearchQuery(String query) {
        return searchHistoryDao.deleteCoinSearchQuery(query)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteCoinSearchHistory() {
        return searchHistoryDao.deleteCoinSearchHistory()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable saveCoins(Coin... coins) {
        return coinDao.addCoin(mapToEntity(coins));
    }

    private List<Coin> mapFromEntity(@NonNull List<CoinEntity> entities) {
        return entities.stream().map(CoinEntityMapper::mapFromEntity).collect(Collectors.toList());
    }

    private CoinEntity[] mapToEntity(@NonNull Coin... coins) {
        return Arrays.stream(coins).map(CoinEntityMapper::mapToEntity).toArray(CoinEntity[]::new);
    }

}

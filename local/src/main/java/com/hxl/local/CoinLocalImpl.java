package com.hxl.local;

import androidx.annotation.NonNull;

import com.hxl.data.repository.coin.CoinLocal;
import com.hxl.domain.model.Coin;
import com.hxl.local.database.CoinDao;
import com.hxl.local.database.BookmarkDao;
import com.hxl.local.model.BookmarkEntity;
import com.hxl.local.model.CoinEntity;
import com.hxl.local.model.CoinEntityMapper;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CoinLocalImpl implements CoinLocal {

    private final CoinDao coinDao;

    private final BookmarkDao bookmarkDao;

    public CoinLocalImpl(CoinDao coinDao, BookmarkDao bookmarkDao) {
        this.coinDao = coinDao;
        this.bookmarkDao = bookmarkDao;
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
    public Single<List<Coin>> getCoins(String ids) {
        return coinDao.getCoins(ids)
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
        return bookmarkDao.bookmarkCoin(new BookmarkEntity(id, System.currentTimeMillis()));
    }

    @Override
    public Completable unBookmarkCoin(String id) {
        return bookmarkDao.unBookmarkCoin(id);
    }

    @Override
    public Single<List<Coin>> getBookmarkedCoins() {
        return bookmarkDao.getBookmarkedCoins()
                .subscribeOn(Schedulers.io())
                .map(this::bookmarksToString)
                .flatMap(this::getCoins);
    }

    @Override
    public Completable saveCoins(List<Coin> coins) {
        return coinDao.saveCoins(mapToEntity(coins));
    }

    private List<Coin> mapFromEntity(@NonNull List<CoinEntity> entities) {
        return entities.stream().map(CoinEntityMapper::mapFromEntity).collect(Collectors.toList());
    }

    private List<CoinEntity> mapToEntity(@NonNull List<Coin> coins) {
        return coins.stream().map(CoinEntityMapper::mapToEntity).collect(Collectors.toList());
    }

    private String bookmarksToString(List<BookmarkEntity> bookmarks) {
        StringBuilder bld = new StringBuilder();
        for (BookmarkEntity i: bookmarks) {
            bld.append(i.id).append(",");
        }
        bld.deleteCharAt(bld.length()-1);
        return bld.toString();
    }
}

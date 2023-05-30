package com.hxl.data;

import com.hxl.data.repository.coin.CoinLocal;
import com.hxl.data.repository.coin.CoinRemote;
import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.domain.model.Trade;
import com.hxl.domain.repository.CoinRepository;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class CoinRepositoryImpl implements CoinRepository {

    private final CoinRemote remoteSource;
    private final CoinLocal localSource;

    public CoinRepositoryImpl(CoinRemote remoteSource, CoinLocal localSource) {
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }
    @Override
    public Single<List<Coin>> getCoins() {
        return remoteSource.getCoins()
                .flatMap(x -> saveCoins(x).toSingleDefault(x))
                .onErrorResumeWith(localSource.getCoins())
                .map(x -> {
                    if (x.isEmpty())
                        throw new UnknownHostException("No cached data, connect to the internet");
                    return x;
                });
    }

    @Override
    public Single<List<Coin>> getCoins(int limit, int offset) {
        return remoteSource.getCoins(limit, offset)
                    .flatMap(x -> saveCoins(x).toSingleDefault(x))
                    .onErrorResumeWith(localSource.getCoins(limit, offset))
                    .map(x -> {
                        if (x.isEmpty())
                            throw new UnknownHostException("No cached data, connect to the internet");
                        return x;
                    });
    }

    @Override
    public Single<List<Coin>> getCoins(List<String> ids) {
        if (ids.isEmpty())
            return Single.just(new ArrayList<>());
        return remoteSource.getCoins(ids)
                .flatMap(x -> saveCoins(x).toSingleDefault(x))
                .onErrorResumeWith(localSource.getCoins(ids));
    }

    @Override
    public Single<List<Coin>> searchCoins(String key) {
        return remoteSource.searchCoins(key)
                .flatMap(x -> saveCoins(x).toSingleDefault(x))
                .onErrorResumeWith(localSource.searchCoins(key));
    }

    @Override
    public Single<Coin> getCoin(String id) {
        return remoteSource.getCoin(id)
                .flatMap(x -> saveCoin(x).toSingleDefault(x))
                .onErrorResumeWith(localSource.getCoin(id));
    }

    private Completable saveCoins(List<Coin> coins) {
        return localSource.saveCoins(coins.toArray(new Coin[0]));
    }

    private Completable saveCoin(Coin coin) {
        return localSource.saveCoins(coin);
    }

    @Override
    public Completable bookmarkCoin(String id) {
        return localSource.bookmarkCoin(id);
    }

    @Override
    public Completable unBookmarkCoin(String id) {
        return localSource.unBookmarkCoin(id);
    }

    @Override
    public Single<Boolean> isCoinBookmarked(String id) {
        return localSource.isCoinBookmarked(id);
    }

    @Override
    public Single<List<Coin>> getBookmarkedCoins() {
            return localSource.getBookmarkedCoinIds()
                    .flatMap(b -> {
                        if (b.isEmpty())
                            return Single.just(new ArrayList<>());

                        List<String> ids = b.stream().map(x -> x.value).collect(Collectors.toList());
                        return remoteSource.getCoins(ids)
                                .flatMap(x -> saveCoins(x).toSingleDefault(x))
                                .map(x -> {
                                    for (Coin c : x) {
                                        c.timestamp = b.get(ids.indexOf(c.id)).timestamp;
                                    }
                                    return x;
                                })
                                .onErrorResumeWith(localSource.getBookmarkedCoins());
                    });
    }

    @Override
    public Single<List<Coin>> searchBookmarkedCoins(String query) {
        return getBookmarkedCoins()
                .map(x -> x.stream().filter(c -> (
                        (c.id.toLowerCase().startsWith(query))
                        || (c.symbol.toLowerCase().startsWith(query))
                        || (c.name.toLowerCase().startsWith(query))
                )).collect(Collectors.toList()));
    }

    @Override
    public Single<List<Coin>> getCoinsBySearchHistory() {
        return localSource.getCoinSearchHistory()
                .flatMap(h -> {
                    List<String> queries = h.stream().map(x -> x.value).collect(Collectors.toList());
                    return getCoins(queries).map(c ->
                            c.stream().sorted(Comparator.comparingLong(coin -> {
                                int index = queries.indexOf(coin.id);
                                return index >= 0 ? -h.get(index).timestamp : Long.MIN_VALUE;
                            })).collect(Collectors.toList()));
                });
    }

    @Override
    public Completable insertCoinSearchQuery(String query) {
        return localSource.insertCoinSearchQuery(query);
    }

    @Override
    public Completable insertCoinSearchQuery(List<String> query) {
        return localSource.insertCoinSearchQuery(query.toArray(new String[0]));
    }

    @Override
    public Completable deleteCoinSearchQuery(String query) {
        return localSource.deleteCoinSearchQuery(query);
    }

    @Override
    public Completable eraseCoinSearchHistory() {
        return localSource.eraseCoinSearchHistory();
    }

    @Override
    public Completable eraseBookmarks() {
        return localSource.eraseBookmarks();
    }

    @Override
    public Completable eraseCoinCache() {
        return localSource.eraseCoinCache();
    }

    @Override
    public Single<List<CoinPriceHistory>> getCoinPriceHistory(String id, CoinPriceHistory.Interval interval) {
        return remoteSource.getCoinPriceHistory(id, interval);
    }

    @Override
    public Single<List<Trade>> getTradesByCoin(String id) {
        return remoteSource.getTradesByCoin(id);
    }

    @Override
    public Single<List<Trade>> getTradesByCoin(String id, int limit, int offset) {
        return remoteSource.getTradesByCoin(id, limit, offset);
    }
}

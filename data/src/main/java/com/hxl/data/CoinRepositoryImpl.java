package com.hxl.data;

import com.hxl.data.repository.coin.CoinLocal;
import com.hxl.data.repository.coin.CoinRemote;
import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.domain.model.SearchQuery;
import com.hxl.domain.repository.CoinRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        if (isOnline()) {
            return remoteSource.getCoins(2000, 0)
                    .flatMap(x -> saveCoins(x).toSingleDefault(x));
        }
        return localSource.getCoins();
    }

    @Override
    public Single<List<Coin>> getCoins(int limit, int offset) {
        if (isOnline()) {
            return remoteSource.getCoins(limit, offset)
                    .flatMap(x -> saveCoins(x).toSingleDefault(x));
        }
        return localSource.getCoins(limit, offset);
    }

    @Override
    public Single<List<Coin>> getCoins(List<String> ids) {
        if (!ids.isEmpty()) {
            if (isOnline()) {
                return remoteSource.getCoins(ids)
                        .flatMap(x -> saveCoins(x).toSingleDefault(x));
            }
            return localSource.getCoins(ids);
        }
        return Single.just(new ArrayList<>());
    }

    @Override
    public Single<List<Coin>> searchCoins(String key) {
        if (isOnline()) {
            return remoteSource.searchCoins(key)
                    .flatMap(x -> saveCoins(x).toSingleDefault(x));
        }
        return localSource.searchCoins(key);
    }

    @Override
    public Single<Coin> getCoin(String id) {
        if (isOnline()) {
            return remoteSource.getCoin(id)
                    .flatMap(x -> saveCoin(x).toSingleDefault(x));
        }
        return localSource.getCoin(id);
    }

    @Override
    public Completable saveCoins(List<Coin> coins) {
        return localSource.saveCoins(coins.toArray(new Coin[0]));
    }

    @Override
    public Completable saveCoin(Coin coin) {
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
        if (isOnline()) {
            return localSource.getBookmarkedCoinIds()
                    .flatMap(b -> {
                        if (b.isEmpty()) {
                            return Single.just(new ArrayList<Coin>());
                        }
                        return remoteSource.getCoins(b);
                    })
                    .flatMap(x -> saveCoins(x).toSingleDefault(x));
        }
        return localSource.getBookmarkedCoins();
    }

    @Override
    public Single<List<SearchQuery>> getCoinSearchHistory() {
        return localSource.getCoinSearchHistory();
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
    public Completable deleteCoinSearchHistory() {
        return localSource.deleteCoinSearchHistory();
    }

    @Override
    public Single<List<CoinPriceHistory>> getCoinPriceHistory(String id, CoinPriceHistory.Interval interval) {
        return remoteSource.getCoinPriceHistory(id, interval);
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException | InterruptedException ignored) { }

        return false;
    }
}

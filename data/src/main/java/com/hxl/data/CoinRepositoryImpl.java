package com.hxl.data;

import com.hxl.domain.model.Coin;
import com.hxl.domain.repository.CoinRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class CoinRepositoryImpl implements CoinRepository {
    @Override
    public Single<List<Coin>> getCoins() {
        return null;
    }

    @Override
    public Single<List<Coin>> getCoins(int limit, int offset) {
        return null;
    }

    @Override
    public Single<List<Coin>> getCoins(String ids) {
        return null;
    }

    @Override
    public Single<Coin> getCoin(String id) {
        return null;
    }

    @Override
    public void saveCoins(List<Coin> coins) {

    }

    @Override
    public void bookMarkCoin(String id) {

    }

    @Override
    public void unBookMarkCoin(String id) {

    }

    @Override
    public Single<List<Coin>> getBookMarkedCoins() {
        return null;
    }

}

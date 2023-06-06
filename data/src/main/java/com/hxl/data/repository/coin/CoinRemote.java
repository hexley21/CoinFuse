package com.hxl.data.repository.coin;

import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.domain.model.Trade;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface CoinRemote {

    Single<List<Coin>> getCoins();
    Single<List<Coin>> getCoins(int limit, int offset);
    Single<List<Coin>> getCoins(List<String> ids);
    Single<List<Coin>> searchCoins(String key);
    Single<Coin> getCoin(String id);

    Single<List<CoinPriceHistory>> getCoinPriceHistory(String id, CoinPriceHistory.Interval interval);
    Single<List<Trade>> getTradesByCoin(String id);
    Single<List<Trade>> getTradesByCoin(String id, int limit, int offset);
}

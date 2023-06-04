package com.hxl.domain.interactors.coins;

import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.domain.repository.CoinRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetCoinPriceHistory {

    public final CoinRepository coinRepository;

    @Inject
    public GetCoinPriceHistory(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    public Single<List<CoinPriceHistory>> invoke(String id, CoinPriceHistory.Interval interval) {
        return coinRepository.getCoinPriceHistory(id, interval);
    }
}

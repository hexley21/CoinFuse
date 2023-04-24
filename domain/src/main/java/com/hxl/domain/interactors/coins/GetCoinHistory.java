package com.hxl.domain.interactors.coins;

import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.domain.repository.CoinRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetCoinHistory {

    public CoinRepository coinRepository;

    @Inject
    public GetCoinHistory(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    public Single<List<CoinPriceHistory>> invoke(String id, CoinPriceHistory.Interval interval) {
        return coinRepository.getCoinHistory(id, interval);
    }
}

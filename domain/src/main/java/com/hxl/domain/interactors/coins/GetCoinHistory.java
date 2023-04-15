package com.hxl.domain.interactors.coins;

import com.hxl.domain.model.History;
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

    public Single<List<History>> invoke(String id, History.Interval interval) {
        return coinRepository.getCoinHistory(id, interval);
    }
}

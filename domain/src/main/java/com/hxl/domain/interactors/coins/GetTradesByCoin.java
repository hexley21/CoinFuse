package com.hxl.domain.interactors.coins;

import com.hxl.domain.model.Trade;
import com.hxl.domain.repository.CoinRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetTradesByCoin {
    private final CoinRepository repository;

    @Inject
    public GetTradesByCoin(CoinRepository repository) {
        this.repository = repository;
    }

    public Single<List<Trade>> invoke(String id) {
        return repository.getTradesByCoin(id);
    }

    public Single<List<Trade>> invoke(String id, int limit, int offset) {
        return repository.getTradesByCoin(id, limit, offset);
    }
}

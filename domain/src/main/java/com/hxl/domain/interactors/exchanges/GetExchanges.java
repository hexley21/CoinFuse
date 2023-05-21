package com.hxl.domain.interactors.exchanges;

import com.hxl.domain.model.Exchange;
import com.hxl.domain.repository.ExchangeRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetExchanges {

    private final ExchangeRepository repository;

    @Inject
    public GetExchanges(ExchangeRepository repository) {
        this.repository = repository;
    }

    public Single<List<Exchange>> invoke() {
        return repository.getExchanges();
    }

    public Single<List<Exchange>> invoke(int limit, int offset) {
        return repository.getExchanges(limit, offset);
    }

}

package com.hxl.domain.interactors.exchanges;

import com.hxl.domain.model.Exchange;
import com.hxl.domain.repository.ExchangeRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetExchange {

    private final ExchangeRepository repository;

    @Inject
    public GetExchange(ExchangeRepository repository) {
        this.repository = repository;
    }

    public Single<Exchange> invoke(String baseId) {
        return repository.getExchange(baseId);
    }

}

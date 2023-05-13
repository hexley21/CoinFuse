package com.hxl.domain.interactors.exchanges;

import com.hxl.domain.model.Exchange;
import com.hxl.domain.repository.ExchangeRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;

public class InsertExchange {

    private final ExchangeRepository repository;

    @Inject
    public InsertExchange(ExchangeRepository repository) {
        this.repository = repository;
    }

    public Completable invoke(List<Exchange> exchanges) {
        return repository.insertExchange(exchanges);
    }

    public Completable invoke(Exchange exchange) {
        return repository.insertExchange(exchange);
    }

}

package com.hxl.domain.interactors.exchanges;

import com.hxl.domain.repository.ExchangeRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;

public class EraseExchange {
    private final ExchangeRepository repository;

    @Inject
    public EraseExchange(ExchangeRepository repository) {
        this.repository = repository;
    }

    public Completable invoke() {
        return repository.eraseExchanges();
    }

}

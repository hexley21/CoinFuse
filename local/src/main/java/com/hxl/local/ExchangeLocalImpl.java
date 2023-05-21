package com.hxl.local;

import com.hxl.data.repository.exchange.ExchangeLocal;
import com.hxl.domain.model.Exchange;
import com.hxl.local.exchange.dao.ExchangeDao;
import com.hxl.local.exchange.mapper.ExchangeEntityMapper;
import com.hxl.local.exchange.model.ExchangeEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ExchangeLocalImpl implements ExchangeLocal {

    private final ExchangeDao exchangeDao;

    public ExchangeLocalImpl(ExchangeDao exchangeDao) {
        this.exchangeDao = exchangeDao;
    }

    @Override
    public Single<List<Exchange>> getExchanges() {
        return exchangeDao.getExchanges()
                .subscribeOn(Schedulers.io())
                .map(x -> x.stream()
                        .map(ExchangeEntityMapper::mapFromEntity)
                        .collect(Collectors.toList()));
    }

    @Override
    public Single<List<Exchange>> getExchanges(int limit, int offset) {
        return exchangeDao.getExchanges(limit, offset)
                .subscribeOn(Schedulers.io())
                .map(x -> x.stream()
                        .map(ExchangeEntityMapper::mapFromEntity)
                        .collect(Collectors.toList()));
    }

    @Override
    public Single<Exchange> getExchange(String exchangeId) {
        return exchangeDao.getExchange(exchangeId)
                .subscribeOn(Schedulers.io())
                .map(ExchangeEntityMapper::mapFromEntity);
    }

    @Override
    public Completable insertExchange(Exchange... exchanges) {
        return exchangeDao.insertExchanges(Arrays.stream(exchanges)
                .map(ExchangeEntityMapper::mapToEntity).toArray(ExchangeEntity[]::new))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable eraseExchanges() {
        return exchangeDao.eraseExchanges();
    }
}

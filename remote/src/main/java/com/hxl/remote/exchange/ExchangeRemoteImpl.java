package com.hxl.remote.exchange;

import com.hxl.data.repository.exchange.ExchangeRemote;
import com.hxl.domain.model.Exchange;
import com.hxl.domain.model.Trade;
import com.hxl.remote.exchange.api.ExchangeService;
import com.hxl.remote.exchange.mapper.ExchangeDTOMapper;
import com.hxl.remote.exchange.mapper.TradeDTOMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class ExchangeRemoteImpl implements ExchangeRemote {
    private final ExchangeService exchangeService;

    public ExchangeRemoteImpl(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @Override
    public Single<List<Exchange>> getExchanges() {
        return exchangeService.getExchanges()
                .map(x -> x.data.stream()
                        .map(ExchangeDTOMapper::mapFromDTO)
                        .collect(Collectors.toList()));
    }

    @Override
    public Single<List<Exchange>> getExchanges(int limit, int offset) {
        return exchangeService.getExchanges(limit, offset)
                .map(x -> x.data.stream()
                        .map(ExchangeDTOMapper::mapFromDTO)
                        .collect(Collectors.toList()));
    }

    @Override
    public Single<Exchange> getExchange(String exchangeId) {
        return exchangeService.getExchange(exchangeId)
                .map(x -> ExchangeDTOMapper.mapFromDTO(x.data));
    }

    @Override
    public Single<List<Trade>> getTrades(Map<String, String> queryMap) {
        return exchangeService.getTrades(queryMap)
                .map(x -> x.data.stream()
                        .map(TradeDTOMapper::mapFromDto)
                        .collect(Collectors.toList()));
    }
}

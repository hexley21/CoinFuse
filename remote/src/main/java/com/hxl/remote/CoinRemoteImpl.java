package com.hxl.remote;

import com.hxl.data.repository.coin.CoinRemote;
import com.hxl.domain.model.Coin;
import com.hxl.remote.api.CoinService;
import com.hxl.remote.model.CoinDTO;
import com.hxl.remote.model.CoinMapper;
import com.hxl.remote.model.Response;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CoinRemoteImpl implements CoinRemote {
    private final CoinService coinService;
    private final CoinMapper mapper;

    public CoinRemoteImpl(CoinService coinService, CoinMapper mapper) {
        this.coinService = coinService;
        this.mapper = mapper;
    }

    @Override
    public Single<List<Coin>> getCoins() {
        return coinService.getCoins()
                .subscribeOn(Schedulers.io())
                .map(this::mapFromDto);
    }

    @Override
    public Single<List<Coin>> getCoins(int limit, int offset) {
        return coinService.getCoins(limit, offset)
                .subscribeOn(Schedulers.io())
                .map(this::mapFromDto);
    }

    @Override
    public Single<List<Coin>> getCoins(List<String> ids) {
        return coinService.getCoins(String.join(",", ids))
                .subscribeOn(Schedulers.io())
                .map(this::mapFromDto);
    }

    @Override
    public Single<List<Coin>> searchCoins(String key) {
        return coinService.searchCoins(key)
                .subscribeOn(Schedulers.io())
                .map(this::mapFromDto);
    }

    @Override
    public Single<Coin> getCoin(String id) {
        return coinService.getCoin(id)
                .subscribeOn(Schedulers.io())
                .map(r -> mapper.mapFromDTO(r.data, r.timestamp));
    }

    private List<Coin> mapFromDto(Response<List<CoinDTO>> response) {
        return response.data.stream()
                .map(r -> mapper.mapFromDTO(r, r.timestamp)).collect(Collectors.toList());
    }
}

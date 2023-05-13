package com.hxl.remote.coin;

import com.hxl.data.repository.coin.CoinRemote;
import com.hxl.domain.model.Coin;
import com.hxl.domain.model.CoinPriceHistory;
import com.hxl.remote.coin.api.CoinService;
import com.hxl.remote.coin.mapper.CoinPriceHistoryDTOMapper;
import com.hxl.remote.coin.model.CoinDTO;
import com.hxl.remote.coin.mapper.CoinDTOMapper;
import com.hxl.remote.coin.model.HistoryDTO;
import com.hxl.remote.Response;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CoinRemoteImpl implements CoinRemote {
    private final CoinService coinService;
    private final CoinDTOMapper mapper;

    public CoinRemoteImpl(CoinService coinService, CoinDTOMapper mapper) {
        this.coinService = coinService;
        this.mapper = mapper;
    }

    @Override
    public Single<List<Coin>> getCoins() {
        return coinService.getCoins()
                .subscribeOn(Schedulers.io())
                .map(this::mapCoinFromDto);
    }

    @Override
    public Single<List<Coin>> getCoins(int limit, int offset) {
        return coinService.getCoins(limit, offset)
                .subscribeOn(Schedulers.io())
                .map(this::mapCoinFromDto);
    }

    @Override
    public Single<List<Coin>> getCoins(List<String> ids) {
        return coinService.getCoins(String.join(",", ids))
                .subscribeOn(Schedulers.io())
                .map(this::mapCoinFromDto);
    }

    @Override
    public Single<List<Coin>> searchCoins(String key) {
        return coinService.searchCoins(key)
                .subscribeOn(Schedulers.io())
                .map(this::mapCoinFromDto);
    }

    @Override
    public Single<Coin> getCoin(String id) {
        return coinService.getCoin(id)
                .subscribeOn(Schedulers.io())
                .map(r -> mapper.mapFromDTO(r.data, r.timestamp));
    }

    @Override
    public Single<List<CoinPriceHistory>> getCoinPriceHistory(String id, CoinPriceHistory.Interval interval) {
        return coinService.getCoinHistory(id, interval.param)
                .subscribeOn(Schedulers.io())
                .map(this::mapHistoryFromDto);
    }

    private List<Coin> mapCoinFromDto(Response<List<CoinDTO>> response) {
        return response.data.stream()
                .map(r -> mapper.mapFromDTO(r, response.timestamp)).collect(Collectors.toList());
    }

    private List<CoinPriceHistory> mapHistoryFromDto(Response<List<HistoryDTO>> response) {
        return response.data.stream()
                .map(CoinPriceHistoryDTOMapper::mapFromDTO).collect(Collectors.toList());
    }
}

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

public class CoinRemoteImpl implements CoinRemote {
    private final CoinService coinService;

    public CoinRemoteImpl(CoinService coinService) {
        this.coinService = coinService;
    }

    @Override
    public Single<List<Coin>> getCoins() {
        return coinService.getCoins().map(this::mapFromDto);
    }

    @Override
    public Single<List<Coin>> getCoins(int limit, int offset) {
        return coinService.getCoins().map(this::mapFromDto);
    }

    @Override
    public Single<List<Coin>> getCoins(String ids) {
        return coinService.getCoins().map(this::mapFromDto);
    }

    @Override
    public Single<Coin> getCoin(String id) {
        return coinService.getCoin(id).map(response -> CoinMapper.mapFromDTO(response.data));
    }

    private List<Coin> mapFromDto(Response<List<CoinDTO>> response) {
        return response.data.stream().map(CoinMapper::mapFromDTO).collect(Collectors.toList());
    }
}

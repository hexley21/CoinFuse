package com.hxl.remote;

import com.hxl.data.repository.coin.CoinRemote;
import com.hxl.domain.model.Coin;
import com.hxl.remote.api.CoinService;
import com.hxl.remote.model.CoinDTO;
import com.hxl.remote.model.CoinMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CoinRemoteImpl implements CoinRemote {
    private final CoinService coinService;

    public CoinRemoteImpl(CoinService coinService) {
        this.coinService = coinService;
    }

    @Override
    public List<Coin> getCoins() {
        return mapFromDto(coinService.getCoins().data);
    }

    @Override
    public List<Coin> getCoins(int limit, int offset) {
        return mapFromDto(coinService.getCoins().data);
    }

    @Override
    public List<Coin> getCoins(String ids) {
        return mapFromDto(coinService.getCoins().data);
    }

    @Override
    public Coin getCoin(String id) {
        return CoinMapper.mapFromDTO(coinService.getCoin(id).data);
    }

    private List<Coin> mapFromDto(List<CoinDTO> dto) {
        return dto.stream().map(CoinMapper::mapFromDTO).collect(Collectors.toList());
    }
}

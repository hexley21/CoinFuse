package com.hxl.data.repository.coin;

import com.hxl.domain.model.Coin;

import java.util.List;

public interface CoinRemote {

    List<Coin> getCoins();
    List<Coin> getCoins(int limit, int offset);
    List<Coin> getCoins(String ids);
    Coin getCoin(String id);
}

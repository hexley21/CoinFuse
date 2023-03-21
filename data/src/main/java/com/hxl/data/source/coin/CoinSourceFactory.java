package com.hxl.data.source.coin;

import com.hxl.data.repository.coin.CoinLocal;
import com.hxl.data.repository.coin.CoinRemote;
import com.hxl.data.repository.coin.CoinSource;

public class CoinSourceFactory {

    private final CoinRemote remoteSource;
    private final CoinLocal localSource;

    public CoinSourceFactory(CoinRemote remoteSource, CoinLocal localSource) {
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }

    public CoinSource getCoinSource(boolean isOnline) {
        if (isOnline){
            return new CoinRemoteSource(remoteSource);
        }
        return new CoinLocalSource(localSource);
    }

    public CoinRemote getRemoteSource() {
        return remoteSource;
    }

    public CoinLocal getLocalSource() {
        return localSource;
    }
}

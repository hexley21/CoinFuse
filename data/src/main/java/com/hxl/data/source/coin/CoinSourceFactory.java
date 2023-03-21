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

    public CoinSource getCoinSource(boolean isOffline) {
        if (isOffline){
            return new CoinLocalSource(localSource);
        }
        return new CoinRemoteSource(remoteSource);
    }

    public CoinRemote getRemoteSource() {
        return remoteSource;
    }

    public CoinLocal getLocalSource() {
        return localSource;
    }
}

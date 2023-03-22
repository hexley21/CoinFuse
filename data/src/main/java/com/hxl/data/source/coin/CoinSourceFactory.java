package com.hxl.data.source.coin;

import com.hxl.data.repository.coin.CoinLocal;
import com.hxl.data.repository.coin.CoinRemote;
import com.hxl.data.repository.coin.CoinSource;

import java.io.IOException;

public class CoinSourceFactory {

    private final CoinRemote remoteSource;
    private final CoinLocal localSource;

    public CoinSourceFactory(CoinRemote remoteSource, CoinLocal localSource) {
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }

    public CoinSource getCoinSource() {
        if (isOnline()){
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

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException | InterruptedException ignored) { }

        return false;
    }
}

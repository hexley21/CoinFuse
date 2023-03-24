package com.hxl.remote.fake;

import com.hxl.remote.model.CoinDTO;

import java.util.Random;
import java.util.UUID;

public class FakeData {

    Random random = new Random();

    public CoinDTO getFakeCoin() {
        return new CoinDTO(
                randomName(),   //  id
                randomInt(),    //  rank
                randomName(),   //  symbol
                randomName(),   //  name
                randomDouble(), //  supply
                randomDouble(), //  maxSupply
                randomDouble(), //  marketCapUsd
                randomDouble(), //  volumeUsd24Hr
                randomDouble(), //  priceUsd
                randomFloat(),  //  changePercent24Hr
                randomDouble(), //  vwap24Hr
                randomName(),   //  explorer
                System.currentTimeMillis()
        );
    }

    private String randomName() {
        return UUID.randomUUID().toString();
    }
    private String randomInt() {
        return String.valueOf(random.nextInt());
    }
    private String randomDouble() {
        return String.valueOf(random.nextDouble());
    }
    private String randomFloat() {
        return String.valueOf(random.nextFloat());
    }
}

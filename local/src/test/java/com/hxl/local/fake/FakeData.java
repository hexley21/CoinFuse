package com.hxl.local.fake;

import com.hxl.domain.model.Coin;
import com.hxl.local.model.CoinEntity;

import java.util.Random;
import java.util.UUID;

public class FakeData {

    Random random = new Random();

    public CoinEntity getFakeEntity() {
        return new CoinEntity(
                randomName(),   // id
                randomInt(),    // rank
                randomName(),   // symbol
                randomName(),   // name
                randomDouble(), // supply
                randomDouble(), // maxSupply
                randomDouble(), // marketCapUsd
                randomDouble(), // volumeUsd24Hr
                randomDouble(), // priceUsd
                randomFloat(),  // changePercent24Hr
                randomDouble(), // vwap24Hr
                randomName(),   // explorer
                randomLong(),   // timestamp
                randomName()    // img
        );
    }

    public Coin getFakeCoin() {
        return new Coin(
                randomName(),   // id
                randomInt(),    // rank
                randomName(),   // symbol
                randomName(),   // name
                randomDouble(), // supply
                randomDouble(), // maxSupply
                randomDouble(), // marketCapUsd
                randomDouble(), // volumeUsd24Hr
                randomDouble(), // priceUsd
                randomFloat(),  // changePercent24Hr
                randomDouble(), // vwap24Hr
                randomName(),   // explorer
                randomLong(),   // timestamp
                randomName()    // img
        );
    }

    private String randomName() {
        return UUID.randomUUID().toString();
    }
    private int randomInt() {
        return random.nextInt();
    }
    private long randomLong() {
        return random.nextLong();
    }
    private double randomDouble() {
        return random.nextDouble();
    }
    private float randomFloat() {
        return random.nextFloat();
    }
}

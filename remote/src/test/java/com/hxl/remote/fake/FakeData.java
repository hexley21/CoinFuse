package com.hxl.remote.fake;

import com.hxl.remote.model.CoinDTO;
import com.hxl.remote.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class FakeData {

    Random random = new Random();
    public Response<List<CoinDTO>> getResponse(int size) {
        return new Response<>(getFakeData(size), random.nextLong());
    }

    private List<CoinDTO> getFakeData(int size) {
        List<CoinDTO> fakeData = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            fakeData.add(getFakeCoin(i));
        }
        return fakeData;
    }

    public CoinDTO getFakeCoin(int rank) {
        return new CoinDTO(
                randomName(),   //  id
                String.valueOf(rank),    //  rank
                randomName(),   //  symbol
                randomName(),   //  name
                randomDouble(), //  supply
                randomDouble(), //  maxSupply
                randomDouble(), //  marketCapUsd
                randomDouble(), //  volumeUsd24Hr
                randomDouble(), //  priceUsd
                randomFloat(),  //  changePercent24Hr
                randomDouble(), //  vwap24Hr
                randomName()    //  explorer
        );
    }

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
                randomName()    //  explorer
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

package com.hxl.remote.fake;

import com.hxl.remote.model.CoinDTO;
import com.hxl.remote.model.Response;

import java.util.ArrayList;
import java.util.List;

import static com.hxl.remote.fake.TestConstants.*;

public class FakeRemoteDataFactory {

    public static Response<List<CoinDTO>> getResponse(int size) {
        return new Response<>(getFakeData(size), TIMESTAMP);
    }

    public static Response<CoinDTO> getResponse(String id) {
        return new Response<>(getCoinDTO(id), TIMESTAMP);
    }

    private static List<CoinDTO> getFakeData(int size) {
        List<CoinDTO> fakeData = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            fakeData.add(getCoinDTO());
        }
        return fakeData;
    }
    private static CoinDTO getCoinDTO() {
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

    private static CoinDTO getCoinDTO(String id) {
        return new CoinDTO(
                id,             //  id
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

}

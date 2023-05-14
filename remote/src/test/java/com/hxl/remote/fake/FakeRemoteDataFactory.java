package com.hxl.remote.fake;

import static com.hxl.remote.fake.RemoteTestConstants.TIMESTAMP;
import static com.hxl.remote.fake.RemoteTestConstants.randomBoolean;
import static com.hxl.remote.fake.RemoteTestConstants.randomDouble;
import static com.hxl.remote.fake.RemoteTestConstants.randomFloat;
import static com.hxl.remote.fake.RemoteTestConstants.randomInt;
import static com.hxl.remote.fake.RemoteTestConstants.randomLong;
import static com.hxl.remote.fake.RemoteTestConstants.randomName;

import com.hxl.remote.Response;
import com.hxl.remote.coin.model.CoinDTO;
import com.hxl.remote.coin.model.HistoryDTO;
import com.hxl.remote.exchange.model.ExchangeDTO;

import java.util.ArrayList;
import java.util.List;

public class FakeRemoteDataFactory {

    public static Response<List<CoinDTO>> getResponse(int size) {
        return new Response<>(getFakeData(size), TIMESTAMP);
    }

    public static Response<List<CoinDTO>> getResponse(String[] ids) {
        return new Response<>(getFakeData(ids), TIMESTAMP);
    }

    public static Response<CoinDTO> getResponse(String id) {
        return new Response<>(getCoinDTO(id), TIMESTAMP);
    }

    public static Response<List<HistoryDTO>> getHistoryResponse(int size) {
        List<HistoryDTO> fakeHistory = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fakeHistory.add(
                    new HistoryDTO(
                            randomDouble(),
                            TIMESTAMP,
                            randomName()
                    )
            );
        }
        return new Response<>(fakeHistory, TIMESTAMP);
    }

    private static List<CoinDTO> getFakeData(String[] ids) {
        List<CoinDTO> fakeData = new ArrayList<>();
        for (String id : ids) {
            fakeData.add(getCoinDTO(id));
        }
        return fakeData;
    }

    private static List<CoinDTO> getFakeData(int size) {
        List<CoinDTO> fakeData = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fakeData.add(getCoinDTO());
        }
        return fakeData;
    }

    public static Response<List<ExchangeDTO>> getFakeExchangeResponse(int size) {
        return new Response<>(getFakeExchangeDTOs(size), TIMESTAMP);
    }

    public static Response<ExchangeDTO> getFakeExchangeResponse(String exchangeId) {
        return new Response<>(getExchangeDTO(exchangeId), TIMESTAMP);
    }

    private static List<ExchangeDTO> getFakeExchangeDTOs(int size) {
        List<ExchangeDTO> fakeData = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fakeData.add(getExchangeDTO());
        }

        return fakeData;
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

    private static CoinDTO getCoinDTO() {
        return getCoinDTO(randomName());
    }

    public static ExchangeDTO getExchangeDTO(String exchangeId) {
        return new ExchangeDTO(
                exchangeId,
                randomName(),
                randomInt(),
                randomDouble(),
                randomDouble(),
                randomInt(),
                randomBoolean(),
                randomName(),
                randomLong()
        );
    }

    public static ExchangeDTO getExchangeDTO() {
        return getExchangeDTO(randomName());
    }
}

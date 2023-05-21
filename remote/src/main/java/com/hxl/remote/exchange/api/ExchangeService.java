package com.hxl.remote.exchange.api;

import com.hxl.remote.Response;
import com.hxl.remote.exchange.model.ExchangeDTO;
import com.hxl.remote.exchange.model.TradeDTO;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ExchangeService {

    @GET("exchanges/")
    Single<Response<List<ExchangeDTO>>> getExchanges();
    @GET("exchanges/")
    Single<Response<List<ExchangeDTO>>> getExchanges(@Query("limit") int limit, @Query("offset") int offset);
    @GET("exchanges/{id}")
    Single<Response<ExchangeDTO>> getExchange(@Path("id") String id);
    @GET("markets/")
    Single<Response<List<TradeDTO>>> getTrades(@QueryMap Map<String, String> queryMap);

}

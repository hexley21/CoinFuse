package com.hxl.remote.exchange.api;

import com.hxl.remote.Response;
import com.hxl.remote.exchange.model.ExchangeDTO;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ExchangeService {

    @GET("exchanges/")
    Single<Response<List<ExchangeDTO>>> getExchanges();
    @GET("exchanges/")
    Single<Response<List<ExchangeDTO>>> getExchanges(@Query("limit") int limit, @Query("offset") int offset);
    @GET("assets/{id}")
    Single<Response<ExchangeDTO>> getExchange(@Path("baseId") String id);

}

package com.hxl.remote.coin.api;

import com.hxl.remote.Response;
import com.hxl.remote.coin.model.CoinDTO;
import com.hxl.remote.coin.model.HistoryDTO;
import com.hxl.remote.exchange.model.TradeDTO;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CoinService {

    @GET("assets/")
    Single<Response<List<CoinDTO>>> getCoins();
    @GET("assets/")
    Single<Response<List<CoinDTO>>> getCoins(@Query("limit") int limit, @Query("offset") int offset);
    @GET("assets/")
    Single<Response<List<CoinDTO>>> getCoins(@Query("ids") String ids);
    @GET("assets/")
    Single<Response<List<CoinDTO>>> searchCoins(@Query("search") String search);
    @GET("assets/{id}")
    Single<Response<CoinDTO>> getCoin(@Path("id") String id);
    @GET("assets/{id}/history")
    Single<Response<List<HistoryDTO>>> getCoinHistory(@Path("id") String id, @Query("interval") String interval);

    @GET("assets/{id}/markets")
    Single<Response<List<TradeDTO>>> getTradesByCoin(@Path("id") String id);
    @GET("assets/{id}/markets")
    Single<Response<List<TradeDTO>>> getTradesByCoin(@Path("id") String id, @Query("limit") int limit, @Query("offset") int offset);
}

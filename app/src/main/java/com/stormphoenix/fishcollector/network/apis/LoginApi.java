package com.stormphoenix.fishcollector.network.apis;

import com.stormphoenix.fishcollector.network.HttpResult;
import com.stormphoenix.fishcollector.network.model.DispatchTable;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-2-17.
 * StormPhoenix is a intelligent Android developer.
 */

public interface LoginApi {
    @GET("user/login")
    Observable<HttpResult<List<DispatchTable>>> login(@Query("username") String username, @Query("password") String password);
}

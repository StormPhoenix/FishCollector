package com.stormphoenix.fishcollector.network.apis;

import com.stormphoenix.fishcollector.network.HttpResult;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Developer on 17-1-19.
 * Wang Cheng is a intelligent Android developer.
 */

public interface SubmitSingleModelApi {
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("commit")
    Observable<HttpResult<Void>> submit(
            @Query("modelType") String modelType,
            @Body RequestBody modelBody);
}

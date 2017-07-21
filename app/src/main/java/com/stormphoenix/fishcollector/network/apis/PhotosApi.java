package com.stormphoenix.fishcollector.network.apis;

import com.stormphoenix.fishcollector.network.HttpResult;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-6-14.
 * StormPhoenix is a intelligent Android developer.
 */

public interface PhotosApi {
    @POST("/user/download_photo")
    Observable<Response<ResponseBody>> downloadPhoto(@Query("username") String username,
                                                     @Query("password") String password,
                                                     @Query("photo_name") String photoName);
}

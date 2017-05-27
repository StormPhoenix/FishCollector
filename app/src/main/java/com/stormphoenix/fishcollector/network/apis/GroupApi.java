package com.stormphoenix.fishcollector.network.apis;

import com.stormphoenix.fishcollector.network.HttpResult;
import com.stormphoenix.fishcollector.network.model.Group;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-5-5.
 * StormPhoenix is a intelligent Android developer.
 */

public interface GroupApi {
    @GET("group/create")
    Observable<HttpResult<Group>> createNewGroup(@Query("username") String username, @Query("password") String password, @Query("group_name") String name);
}

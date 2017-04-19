package com.stormphoenix.fishcollector.network.apis;

import com.stormphoenix.fishcollector.mvp.model.beans.Account;
import com.stormphoenix.fishcollector.network.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-3-17.
 * StormPhoenix is a intelligent Android developer.
 */

public interface MembersApi {
    @GET("user/members")
    Observable<HttpResult<List<Account>>> loadMembersInfo(@Query("adminName") String adminName,
                                                          @Query("password") String password);
}

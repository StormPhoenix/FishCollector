package com.stormphoenix.fishcollector.network.apis;

import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.network.HttpResult;
import com.stormphoenix.fishcollector.network.model.Group;
import com.stormphoenix.fishcollector.network.model.GroupRecord;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-2-17.
 * StormPhoenix is a intelligent Android developer.
 */

public interface UserApi {
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("model/submit/{modelType}")
    Observable<HttpResult<Void>> sumbitModel(
            @Path("modelType") String modelType,
            @Body RequestBody modelBody);

    @GET("model/download")
    Observable<HttpResult<List<MonitoringSite>>> downloadData(@Query("username") String username,
                                                              @Query("password") String password);

    // 用户登录，返回关于该用户的组信息
    @GET("user/login")
    Observable<HttpResult<GroupRecord>> login(@Query("username") String username, @Query("password") String password);

    // 用户创建分组
    @GET("user/create_group")
    Observable<HttpResult<GroupRecord>> createNewGroup(@Query("username") String username, @Query("password") String password, @Query("group_name") String name);

    @GET("user/join_group")
    Observable<HttpResult<GroupRecord>> joinGroup(@Query("username") String username, @Query("password") String password, @Query("group_id") String groupId);
}

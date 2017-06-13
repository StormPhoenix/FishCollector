package com.stormphoenix.fishcollector.network.apis;

import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.network.HttpResult;
import com.stormphoenix.fishcollector.network.model.GroupRecord;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-2-17.
 * StormPhoenix is a intelligent Android developer.
 */

public interface UserApi {
    @GET("/user/download_photos_info")
    Observable<HttpResult<List<String>>> downloadPhotosInfo(@Query("username") String username,
                                                            @Query("password") String password,
                                                            @Query("model_id") String modelId,
                                                            @Query("modle_type") String modelType);

    // 上传组长的分配表
    @POST("user/upload_task_table")
    Observable<HttpResult<Void>> uploadTaskTable(@Query("username") String username,
                                                 @Query("password") String password,
                                                 @Body RequestBody tableBody);

    // 用户登录，返回关于该用户的组信息
    @GET("user/pull_all_models")
    Observable<HttpResult<List<MonitoringSite>>> downloadAllModel(@Query("username") String username, @Query("password") String password);

    // 用户提交数据
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("user/upload_model")
    Observable<HttpResult<Void>> uploadModel(@Query("username") String username,
                                             @Query("password") String password,
                                             @Query("model_type") String modelType,
                                             @Body RequestBody modelBody);

    // 用户提交图片数据
    @POST("user/upload_photo")
    Observable<HttpResult<Void>> uploadPhoto(@Query("model_type") String modelType,
                                             @Query("username") String username,
                                             @Query("password") String password,
                                             @Query("model_id") String modelId,
                                             @Body RequestBody body);

    // 用户登录，返回关于该用户的组信息
    @GET("user/login")
    Observable<HttpResult<GroupRecord>> login(@Query("username") String username, @Query("password") String password);

    // 用户创建分组
    @GET("user/create_group")
    Observable<HttpResult<GroupRecord>> createNewGroup(@Query("username") String username, @Query("password") String password, @Query("group_name") String name);

    // 用户加入组
    @GET("user/join_group")
    Observable<HttpResult<GroupRecord>> joinGroup(@Query("username") String username, @Query("password") String password, @Query("group_id") String groupId);

    // 请求下载组内所有信息
    @GET("user/request_tree")
    Observable<HttpResult<List<MonitoringSite>>> requestTree(@Query("username") String username,
                                                             @Query("password") String password);
}

package com.stormphoenix.fishcollector.network.apis;

import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.network.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by StormPhoenix on 17-2-18.
 * StormPhoenix is a intelligent Android developer.
 */

public interface DownloadApi {
    @GET("download")
    Observable<HttpResult<List<MonitoringSite>>> downloadData();
}

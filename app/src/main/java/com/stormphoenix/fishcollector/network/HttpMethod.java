package com.stormphoenix.fishcollector.network;

import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.RequestCallback;
import com.stormphoenix.fishcollector.network.apis.SubmitSingleModelApi;
import com.stormphoenix.fishcollector.shared.JsonParser;
import com.stormphoenix.fishcollector.shared.NetManager;
import com.stormphoenix.fishcollector.shared.rxutils.RxJavaCustomTransformer;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Developer on 17-1-19.
 * Wang Cheng is a intelligent Android developer.
 */

public class HttpMethod {

    private static HttpMethod instance = null;
    private SubmitSingleModelApi submitSingleModelApi = null;

    private HttpMethod() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetManager.getBaseUrl())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        submitSingleModelApi = retrofit.create(SubmitSingleModelApi.class);
    }

    public static HttpMethod getInstance() {
        if (instance == null) {
            synchronized (HttpMethod.class) {
                if (instance == null) {
                    instance = new HttpMethod();
                }
            }
        }
        return instance;
    }


    public Subscription submitSingleModel(String modelType, BaseModel model, final RequestCallback<HttpResult<Void>> callback) {
        callback.beforeRequest();

        RequestBody modelBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonParser.getInstance().toJson(model));
        return submitSingleModelApi.submit(modelType, modelBody)
                .compose(RxJavaCustomTransformer.<HttpResult<Void>>defaultSchedulers())
                .subscribe(new Subscriber<HttpResult<Void>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.toString());
                    }

                    @Override
                    public void onNext(HttpResult<Void> result) {
                        callback.success(result);
                    }
                });
    }
}

package com.stormphoenix.fishcollector.network;

import android.util.Log;

import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.RequestCallback;
import com.stormphoenix.fishcollector.network.apis.UserApi;
import com.stormphoenix.fishcollector.network.model.GroupRecord;
import com.stormphoenix.fishcollector.shared.ConfigUtils;
import com.stormphoenix.fishcollector.shared.JsonParser;
import com.stormphoenix.fishcollector.shared.NetManager;
import com.stormphoenix.fishcollector.shared.rxutils.RxJavaCustomTransformer;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
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

    private static final String TAG = "HttpMethod";
    private static HttpMethod instance = null;
    private UserApi userApi = null;

    private OkHttpClient client = null;

    private HttpMethod() {
        client = new OkHttpClient.Builder()
                .readTimeout(5000, java.util.concurrent.TimeUnit.MILLISECONDS)
                .connectTimeout(5000, java.util.concurrent.TimeUnit.MILLISECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetManager.getBaseUrl())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(JsonParser.getInstance().getGson()))
                .build();

        userApi = retrofit.create(UserApi.class);
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

    public Subscription downloadAllModels(String username, String password, final RequestCallback<HttpResult<List<MonitoringSite>>> callback) {
        callback.beforeRequest();
        return userApi.downloadAllModel(username, password)
                .compose(RxJavaCustomTransformer.<HttpResult<List<MonitoringSite>>>defaultSchedulers())
                .subscribe(new Subscriber<HttpResult<List<MonitoringSite>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult<List<MonitoringSite>> listHttpResult) {
                        Log.e(TAG, "onNext: " + JsonParser.getInstance().toJson(listHttpResult));
                        callback.success(listHttpResult);
                    }
                });
    }

    // 创建一个分组
    public Subscription createNewGroup(String groupName, final RequestCallback<HttpResult<GroupRecord>> callback) {
        callback.beforeRequest();
        return userApi.createNewGroup(ConfigUtils.getInstance().getUsername(), ConfigUtils.getInstance().getPassword(), groupName)
                .compose(RxJavaCustomTransformer.<HttpResult<GroupRecord>>defaultSchedulers())
                .subscribe(new Subscriber<HttpResult<GroupRecord>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult<GroupRecord> httpResult) {
                        Log.e(TAG, "onNext: " + JsonParser.getInstance().toJson(httpResult));
                        callback.success(httpResult);
                    }
                });
    }

    public Subscription requestTree(final RequestCallback<HttpResult<List<MonitoringSite>>> callback) {
        callback.beforeRequest();
        return userApi.requestTree(ConfigUtils.getInstance().getUsername(), ConfigUtils.getInstance().getPassword())
                .compose(RxJavaCustomTransformer.<HttpResult<List<MonitoringSite>>>defaultSchedulers())
                .subscribe(new Subscriber<HttpResult<List<MonitoringSite>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult<List<MonitoringSite>> listHttpResult) {
                        Log.e(TAG, "onNext: " + JsonParser.getInstance().toJson(listHttpResult));
                        callback.success(listHttpResult);
                    }
                });
    }

    public Subscription login(String username, String password, final RequestCallback<HttpResult<GroupRecord>> callback) {
        callback.beforeRequest();
        return userApi.login(username, password)
                .compose(RxJavaCustomTransformer.<HttpResult<GroupRecord>>defaultSchedulers())
                .subscribe(new Subscriber<HttpResult<GroupRecord>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.toString());
                    }

                    @Override
                    public void onNext(HttpResult<GroupRecord> groupRecord) {
                        callback.success(groupRecord);
                    }
                });
    }

    public Subscription submitModelWithPhoto(String modelType, BaseModel model, final RequestCallback<HttpResult<Void>> callback) {
        return submitModel(modelType, model, callback);
//        callback.beforeRequest();
//        String[] paths = null;
//        try {
//            Method getPicMethod = model.getClass().getMethod("getPhoto", (Class[]) null);
//            String path = (String) getPicMethod.invoke(model, null);
//            paths = PicturePathUtils.processPicturePath(path);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            Log.e(TAG, "submitModelWithPhoto: " + e.toString());
//        }
//        if (paths == null) {
//            return null;
//        }
//
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        for (String path : paths) {
//            File file = new File(path);
//            builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
//        }
//        builder.addFormDataPart("json", JsonParser.getInstance().toJson(model));
//
//        return submitSingleModelApi.submitWithPhoto(modelType, builder.build())
//                .compose(RxJavaCustomTransformer.<HttpResult<Void>>defaultSchedulers())
//                .subscribe(new Subscriber<HttpResult<Void>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        callback.onError(e.toString());
//                    }
//
//                    @Override
//                    public void onNext(HttpResult<Void> result) {
//                        callback.success(result);
//                    }
//                });
    }

    public Subscription submitModel(String modelType, BaseModel model, final RequestCallback<HttpResult<Void>> callback) {
        callback.beforeRequest();

        RequestBody modelBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonParser.getInstance().toJson(model));
        return userApi.submitModel(modelType,
                ConfigUtils.getInstance().getUsername(),
                ConfigUtils.getInstance().getPassword(),
                modelBody)
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

    public Subscription joinGroup(String groupId, final RequestCallback<HttpResult<GroupRecord>> callback) {
        callback.beforeRequest();
        return userApi.joinGroup(ConfigUtils.getInstance().getUsername(), ConfigUtils.getInstance().getPassword(), groupId)
                .compose(RxJavaCustomTransformer.<HttpResult<GroupRecord>>defaultSchedulers())
                .subscribe(new Subscriber<HttpResult<GroupRecord>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult<GroupRecord> httpResult) {
                        Log.e(TAG, "onNext: " + JsonParser.getInstance().toJson(httpResult));
                        callback.success(httpResult);
                    }
                });
    }
}

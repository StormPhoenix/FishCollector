package com.stormphoenix.fishcollector.network;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.stormphoenix.fishcollector.FishApplication;
import com.stormphoenix.fishcollector.db.DbManager;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.RequestCallback;
import com.stormphoenix.fishcollector.mvp.ui.dialog.MultiProgressDialogGenerator;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseImageListFragment;
import com.stormphoenix.fishcollector.network.apis.PhotosApi;
import com.stormphoenix.fishcollector.network.apis.UserApi;
import com.stormphoenix.fishcollector.network.model.GroupRecord;
import com.stormphoenix.fishcollector.network.model.TaskTable;
import com.stormphoenix.fishcollector.network.progress.ProgressHelper;
import com.stormphoenix.fishcollector.network.progress.UIProgressRequestListener;
import com.stormphoenix.fishcollector.network.progress.UIProgressResponseListener;
import com.stormphoenix.fishcollector.shared.ConfigUtils;
import com.stormphoenix.fishcollector.shared.JsonParser;
import com.stormphoenix.fishcollector.shared.NetManager;
import com.stormphoenix.fishcollector.shared.ReflectUtils;
import com.stormphoenix.fishcollector.shared.rxutils.RxJavaCustomTransformer;

import org.greenrobot.essentials.io.IoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
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

    public void downloadPhotos(BaseModel model, String[] paths, final MultiProgressDialogGenerator.ProgressBarsWrapper wrapper, BaseImageListFragment fragment) {
        for (int index = 0; index < paths.length; index++) {
            final int finalIndex = index;
            UIProgressResponseListener progressListener = new UIProgressResponseListener() {
                @Override
                public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
                    int value = (int) (((float) bytesRead) / contentLength * 100);
                    wrapper.setProgress(finalIndex, value, done);
                }
            };
            // 可能有多张图片，每一张图片对应一次请求
            dowloadPhoto(model, paths[index], progressListener, fragment);
        }
    }

    /**
     * 提交图片
     */
    public void uploadPhotos(String modelType, String modelId, String[] paths, final MultiProgressDialogGenerator.ProgressBarsWrapper wrapper) {
        for (int index = 0; index < paths.length; index++) {
            final int finalIndex = index;
            UIProgressRequestListener progressListener = new UIProgressRequestListener() {
                @Override
                public void onUIRequestProgress(long bytesWrite, long contentLength, boolean done) {
                    Log.e(TAG, "onUIRequestProgress: is done " + done);
                    //ui层回调
                    int value = (int) (((float) bytesWrite) / contentLength * 100);
                    wrapper.setProgress(finalIndex, value, done);
                }
            };
            // 可能有多张图片，每一张图片对应一次请求
            uploadPhoto(modelType, modelId, paths[index], progressListener);
        }
    }

    private void dowloadPhoto(final BaseModel model, String photoName, UIProgressResponseListener listener, final BaseImageListFragment fragment) {
        // 这里 new 出了一个 OkHttpClient， 目的是为了创建一个可以使用 UIProgressResponseListener 的家伙
        OkHttpClient client = ProgressHelper.createClientWithProgressResponseListener(listener);
        Retrofit retrofit = new Retrofit.Builder()
//                .client(client)
                .baseUrl(NetManager.getBaseUrl())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(JsonParser.getInstance().getGson()))
                .build();

        PhotosApi photosApi = retrofit.create(PhotosApi.class);
        photosApi.downloadPhoto(ConfigUtils.getInstance().getUsername(),
                ConfigUtils.getInstance().getPassword(),
                photoName).compose(RxJavaCustomTransformer.<Response<ResponseBody>>defaultSchedulers())
                .subscribe(new Subscriber<Response<ResponseBody>>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onNext(Response<ResponseBody> response) {
                        // 把图片下载完了后就要存到某一个地方，
                        // 这个存的路径呀，还要保存到modelBean里面
//                        Log.e(TAG, "onNext: " + response.headers().get("imageName"));
                        String imageName = response.headers().get("imageName");
                        if (TextUtils.isEmpty(imageName)) {
                            return;
                        }

                        File imageFile = new File(Environment.getExternalStorageDirectory() + File.separator + "fish", imageName);
                        if (!imageFile.getParentFile().exists()) {
                            imageFile.getParentFile().mkdir();
                        }

                        try {
                            IoUtils.copyAllBytes(response.body().byteStream(), new FileOutputStream(imageFile));
                            // 向数据库中添加数据
                            ReflectUtils.appendModelPhotoPath(model, imageFile.getAbsolutePath());
                            DbManager dbManager = new DbManager(FishApplication.getInstance());
                            dbManager.save(model);
                            fragment.updatePicturesData();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.e(TAG, "success");
                    }
                });
    }

    private void uploadPhoto(String modelType, String modelId, String imagePath, UIProgressRequestListener progressListener) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        File file = new File(imagePath);
        MultipartBody body = new MultipartBody.Builder().addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file)).build();
        userApi.uploadPhoto(modelType,
                ConfigUtils.getInstance().getUsername(),
                ConfigUtils.getInstance().getPassword(),
                modelId,
                ProgressHelper.addProgressRequestListener(body, progressListener))
                .compose(RxJavaCustomTransformer.<HttpResult<Void>>defaultSchedulers())
                .subscribe(new Subscriber<HttpResult<Void>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        // do something
                        Log.e(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(HttpResult<Void> result) {
                    }
                });
    }

    public void downloadSingleModel(String username, String password, String modelType, String modelId, final RequestCallback<HttpResult<String>> callback) {
        callback.beforeRequest();
        userApi.downloadSingleModel(username, password,modelType,modelId)
                .compose(RxJavaCustomTransformer.<HttpResult<String>>defaultSchedulers())
                .subscribe(new Subscriber<HttpResult<String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.toString());
                    }

                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        callback.success(stringHttpResult);
                    }
                });
    }

    public Subscription downloadPhotosInfo(String username, String password, String modelId, String modelType, final RequestCallback<HttpResult<List<String>>> callback) {
        callback.beforeRequest();
        return userApi.downloadPhotosInfo(username, password, modelId, modelType)
                .compose(RxJavaCustomTransformer.<HttpResult<List<String>>>defaultSchedulers())
                .subscribe(new Subscriber<HttpResult<List<String>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.toString());
                    }

                    @Override
                    public void onNext(HttpResult<List<String>> result) {
                        callback.success(result);
                    }
                });
    }

    public Subscription uploadModel(String modelType, BaseModel model, final RequestCallback<HttpResult<Void>> callback) {
        callback.beforeRequest();

        RequestBody modelBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonParser.getInstance().toJson(model));
        return userApi.uploadModel(ConfigUtils.getInstance().getUsername(),
                ConfigUtils.getInstance().getPassword(),
                modelType,
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

    public Subscription uploadTaskTable(String username, String password, TaskTable taskTable, final RequestCallback<HttpResult<Void>> callback) {
        callback.beforeRequest();
        RequestBody modelBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonParser.getInstance().toJson(taskTable));
        return userApi.uploadTaskTable(username,
                password,
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

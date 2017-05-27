package com.stormphoenix.fishcollector.db;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.stormphoenix.fishcollector.FishApplication;
import com.stormphoenix.fishcollector.Locals;
import com.stormphoenix.fishcollector.network.model.DispatchTable;
import com.stormphoenix.fishcollector.network.model.Group;
import com.stormphoenix.fishcollector.shared.ConfigUtils;
import com.stormphoenix.fishcollector.shared.JsonParser;
import com.stormphoenix.fishcollector.shared.rxutils.RxJavaCustomTransformer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by StormPhoenix on 17-5-4.
 * StormPhoenix is a intelligent Android developer.
 */

public class FSManager {
    private static final String TAG = FSManager.class.getName();

    private final static String GROUP_FILE_TYPE = "groups.json";
    private final static String DISPATCH_FILE_TYPE = "dispatches.json";
    private final static String ACCOUNT_FILE_TYPE = "account.json";

    private static FSManager INSTANCE = null;

    public static FSManager getInstance() {
        if (INSTANCE == null) {
            synchronized (FSManager.class) {
                if (INSTANCE == null) {
                    synchronized (FSManager.class) {
                        INSTANCE = new FSManager(FishApplication.getInstance());
                    }
                }
            }
        }
        return INSTANCE;
    }

    private static List<Group> groupList = null;

    private static List<DispatchTable> dispatchTableList = null;

    public void saveGroupAsync(final Group group, final FsCallback<Void> callback) {
        queryAllGroupsAsync(new FsCallback<List<Group>>() {
            @Override
            public void call(final List<Group> groups) {
                groups.add(group);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        saveGr
//                                ..
                        saveGroupsSync(groups);
                    }
                }).start();
            }

            @Override
            public void onError(String errorMsg) {
                Log.e(TAG, "onError: " + errorMsg);
                callback.onError(errorMsg);
            }
        });
    }

    public static interface FsCallback<T> {
        void call(T t);

        void onError(String errorMsg);
    }

    private Context mContext;

    private FSManager(Context context) {
        mContext = context;
    }

    public void queryAllGroupsAsync(final FsCallback<List<Group>> callback) {
        Observable.create(new Observable.OnSubscribe<List<Group>>() {
            @Override
            public void call(Subscriber<? super List<Group>> subscriber) {
                List<Group> groups = readGroups();
                subscriber.onNext(groups);
            }
        }).compose(RxJavaCustomTransformer.<List<Group>>defaultSchedulers())
                .subscribe(new Action1<List<Group>>() {
                    @Override
                    public void call(List<Group> groups) {
                        callback.call(groups);
                    }
                });
    }

    public List<Group> queryAllGroupsSync() {
        return readGroups();
    }

    public void queryJoinGroupAsync(final FsCallback<List<Group>> callback) {
        Observable.create(new Observable.OnSubscribe<List<Group>>() {
            @Override
            public void call(Subscriber<? super List<Group>> subscriber) {
                List<Group> groups = readGroups();
                List<Group> result = new ArrayList<>();
                for (Group group : groups) {
                    if (!group.header.name.equals(ConfigUtils.getInstance().getUsername())) {
                        result.add(group);
                    }
                }
                subscriber.onNext(result);
            }
        }).compose(RxJavaCustomTransformer.<List<Group>>defaultSchedulers())
                .subscribe(new Subscriber<List<Group>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.toString());
                    }

                    @Override
                    public void onNext(List<Group> groups) {
                        callback.call(groups);
                    }
                });
    }

    public void queryManagerGroupAsync(final FsCallback<List<Group>> callback) {
        Observable.create(new Observable.OnSubscribe<List<Group>>() {
            @Override
            public void call(Subscriber<? super List<Group>> subscriber) {
                List<Group> groups = readGroups();
                List<Group> result = new ArrayList<>();
                for (Group group : groups) {
                    if (group.header.name.equals(ConfigUtils.getInstance().getUsername())) {
                        result.add(group);
                    }
                }
                subscriber.onNext(result);
            }
        }).compose(RxJavaCustomTransformer.<List<Group>>defaultSchedulers())
                .subscribe(new Subscriber<List<Group>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.toString());
                    }

                    @Override
                    public void onNext(List<Group> groups) {
                        callback.call(groups);
                    }
                });
    }

    public void queryDispatchTablesAsync(final FsCallback<List<DispatchTable>> callback) {
        Observable.create(new Observable.OnSubscribe<List<DispatchTable>>() {
            @Override
            public void call(Subscriber<? super List<DispatchTable>> subscriber) {
                if (dispatchTableList != null) {
                    subscriber.onNext(dispatchTableList);
                } else {
                    List<DispatchTable> dispatchTables = readDispatchTable();
                    dispatchTableList = dispatchTables;
                    subscriber.onNext(dispatchTables);
                }
            }
        }).compose(RxJavaCustomTransformer.<List<DispatchTable>>defaultSchedulers())
                .subscribe(new Subscriber<List<DispatchTable>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.toString());
                    }

                    @Override
                    public void onNext(List<DispatchTable> dispatchTables) {
                        callback.call(dispatchTables);
                    }
                });
    }

    public void saveGroupsSync(List<Group> groups) {
        File file = new File(mContext.getCacheDir(), GROUP_FILE_TYPE);
        Locals.userGroups = groups;
        if (Locals.userGroups == null || Locals.userGroups.size() == 0) {
            Locals.currentGroup = -1;
        } else {
            Locals.currentGroup = 0;
        }
        writeToFile(JsonParser.getInstance().toJson(groups), file);
    }

    public void saveGroupsAsync(final List<Group> groups, final FsCallback<Void> callback) {
        Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                File file = new File(mContext.getCacheDir(), GROUP_FILE_TYPE);
                Locals.userGroups = groups;
                if (Locals.userGroups == null || Locals.userGroups.size() == 0) {
                    Locals.currentGroup = -1;
                } else {
                    Locals.currentGroup = 0;
                }
                writeToFile(JsonParser.getInstance().toJson(groups), file);
            }
        }).compose(RxJavaCustomTransformer.<Void>defaultSchedulers())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.toString());
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        callback.call(aVoid);
                    }
                });
    }

    public void saveDispatchTablesSync(List<DispatchTable> dispatchTables) {
        File file = new File(mContext.getCacheDir(), DISPATCH_FILE_TYPE);
        Locals.dispatchTables = dispatchTables;
        if (Locals.dispatchTables == null || Locals.dispatchTables.size() == 0) {
            Locals.currentDispatchTable = -1;
        } else {
            Locals.currentDispatchTable = 0;
        }
        writeToFile(JsonParser.getInstance().toJson(dispatchTables), file);
    }

    private List<DispatchTable> readDispatchTable() {
        if (dispatchTableList != null) {
            return dispatchTableList;
        } else {
            File file = new File(mContext.getCacheDir(), DISPATCH_FILE_TYPE);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            String jsonContent = readFromFile(file);
            if (jsonContent == null) {
                new ArrayList<>();
            }
            Type type = new TypeToken<List<DispatchTable>>() {
            }.getType();
            dispatchTableList = (List<DispatchTable>) JsonParser.getInstance().fromJson(jsonContent, type);
        }
        return dispatchTableList;
    }

    private List<Group> readGroups() {
        if (groupList != null) {
            return groupList;
        } else {
            File file = new File(mContext.getCacheDir(), GROUP_FILE_TYPE);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            String jsonContent = readFromFile(file);
            if (jsonContent == null || TextUtils.isEmpty(jsonContent)) {
                return new ArrayList<>();
            }
            Type type = new TypeToken<List<Group>>() {
            }.getType();
            groupList = (List<Group>) JsonParser.getInstance().fromJson(jsonContent, type);
        }
        return groupList;
    }

    public void saveAll(final List<DispatchTable> dispatchTables) {
        if (dispatchTables == null || dispatchTables.size() == 0) {
            Log.e(TAG, "saveAll: tables == null");
            return;
        }
        final List<Group> groups = new ArrayList<>();

        for (DispatchTable table : dispatchTables) {
            assert table.group != null;
            groups.add(table.group);
        }
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                saveGroupsSync(groups);
                saveDispatchTablesSync(dispatchTables);
            }
        }).subscribe();
    }

    private void writeToFile(String jsongContent, File file) {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdir();
                file.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(jsongContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromFile(File file) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            String temp;
            while ((temp = reader.readLine()) != null) {
                builder.append(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return builder.toString();
        }
    }
}

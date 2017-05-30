package com.stormphoenix.fishcollector.db;

import com.google.gson.Gson;
import com.stormphoenix.fishcollector.FishApplication;
import com.stormphoenix.fishcollector.network.model.GroupRecord;
import com.stormphoenix.fishcollector.shared.JsonParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by StormPhoenix on 17-5-4.
 * StormPhoenix is a intelligent Android developer.
 */

public class FSManager {
    private static final String GROUP_TASK_RECORD = "group_task_record.json";
    private static final String ACCOUNT_RECORD = "account_record.json";

    // 保证 record 的同步
    public volatile GroupRecord record = null;

    private static FSManager instance = null;

    public static FSManager getInstance() {
        if (instance == null) {
            synchronized (FSManager.class) {
                if (instance == null) {
                    synchronized (FSManager.class) {
                        instance = new FSManager();
                    }
                }
            }
        }
        return instance;
    }

    private FSManager() {
    }

    public synchronized GroupRecord getRecordContent() {
        if (record != null) {
            return record;
        }
        File storeFile = new File(FishApplication.getInstance().getFilesDir(), GROUP_TASK_RECORD);
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(storeFile)));
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                builder.append(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            record = (GroupRecord) JsonParser.getInstance().fromJson(builder.toString(), GroupRecord.class);
            return record;
        }
    }

    /**
     * 保存新的组数据，同时更新 record 变量
     *
     * @param groupRecord
     */
    public synchronized void saveRecordContent(GroupRecord groupRecord) {
        try {
            File storeFile = new File(FishApplication.getInstance().getFilesDir(), GROUP_TASK_RECORD);
            if (!storeFile.exists()) {
                storeFile.getParentFile().mkdir();
                storeFile.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(storeFile)));
            writer.write(JsonParser.getInstance().toJson(groupRecord));
            writer.flush();
            // 更新数据
            record = groupRecord;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

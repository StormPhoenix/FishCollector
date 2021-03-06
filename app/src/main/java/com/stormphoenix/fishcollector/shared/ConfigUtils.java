package com.stormphoenix.fishcollector.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.stormphoenix.fishcollector.FishApplication;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by StormPhoenix on 17-2-17.
 * StormPhoenix is a intelligent Android developer.
 */

public class ConfigUtils {
    private static ConfigUtils INSTANCE = null;

    private SharedPreferences userInfoSp = null;
    private SharedPreferences taskDispatch = null;

    private ConfigUtils(Context context) {
        userInfoSp = context.getSharedPreferences("user_info", MODE_PRIVATE);
        taskDispatch = context.getSharedPreferences("task_dispatch", MODE_PRIVATE);
    }

    public static ConfigUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (ConfigUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ConfigUtils(FishApplication.getInstance());
                }
            }
        }
        return INSTANCE;
    }

    public boolean isUserLogin() {
        return userInfoSp.getBoolean("is_login", false);
    }

    public void setUserLogin() {
        userInfoSp.edit()
                .putBoolean("is_login", true)
                .commit();
    }

    public void setUserLogout() {
        userInfoSp.edit()
                .putBoolean("is_login", false)
                .commit();
    }

    public String getUserGroupId() {
        return userInfoSp.getString("group_id", null);
    }

    public void setUserGroupId(String groupId) {
        userInfoSp.edit()
                .putString("group_id", groupId)
                .commit();
    }

    public void setUserInfo(String username, String password) {
        userInfoSp.edit()
                .putString("username", username)
                .putString("password", password)
                .commit();
    }

    public String getUsername() {
        return userInfoSp.getString("username", null);
    }

    public String getPassword() {
        return userInfoSp.getString("password", null);
    }
}

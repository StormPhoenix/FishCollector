package com.stormphoenix.fishcollector.db;

import android.content.Context;

import com.stormphoenix.fishcollector.mvp.model.beans.DaoMaster;
import com.stormphoenix.fishcollector.mvp.model.beans.DaoSession;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSiteDao;

import java.util.List;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class DbManager {
    private DaoMaster.DevOpenHelper devOpenHelper = null;

    public DbManager(Context context) {
        devOpenHelper = new DaoMaster.DevOpenHelper(context, "fish-db");
    }

    public List<MonitoringSite> queryAll() {
        DaoSession daoSession = new DaoMaster(devOpenHelper.getReadableDatabase()).newSession();
        MonitoringSiteDao dao = daoSession.getMonitoringSiteDao();
        return dao.queryBuilder().build().list();
    }
}

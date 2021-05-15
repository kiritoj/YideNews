package com.Blinger.YiDeNews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.Blinger.YiDeNews.dao.DaoMaster;
import com.Blinger.YiDeNews.dao.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * 作者：310Lab
 * 时间：2020/4/1 10:13
 * 邮箱：1760567382@qq.com
 * 功能：初始化数据库
 */

public class DbManager
{
    private static DbManager mManager;
    private DaoSession mSession;

    private DbManager()
    {

    }

    public static DbManager getManager()
    {
        if (mManager == null)
        {
            synchronized (DbManager.class)
            {
                if (mManager == null)
                {
                    mManager = new DbManager();
                }
            }
        }
        return mManager;
    }


    public DaoSession initialization(Context context)
    {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, context.getPackageName() + ".db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        mSession = new DaoMaster(db).newSession();
        mSession.clear();
        return mSession;
    }

}

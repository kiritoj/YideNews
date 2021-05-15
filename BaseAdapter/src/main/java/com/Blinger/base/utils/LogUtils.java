package com.Blinger.base.utils;

import android.text.TextUtils;
import android.util.Log;



/**
 * 作者：310Lab
 * 时间：2020/4/1 10:07
 * 邮箱：1760567382@qq.com
 * 功能：
 */

public class LogUtils
{
    private static boolean isDebug = true;
    private static String TAG = "info";

    public static void v(String msg)
    {
        if (isDebug)
        {
            LogUtils.v(msg);
        }
    }

    public static void d(String msg)
    {
        if (isDebug)
        {
            LogUtils.d(msg);
        }
    }

    public static void i(String msg)
    {
        if (isDebug)
        {
            LogUtils.i(msg);
        }
    }

    public static void w(String msg)
    {
        if (isDebug)
        {
            LogUtils.w(msg);
        }
    }

    public static void e(String msg)
    {
        if (isDebug)
        {
            LogUtils.e(msg);
        }
    }

    public static void json(String msg)
    {
        if (isDebug)
        {
            LogUtils.json(msg);
        }
    }

    public static void v(String tag, String msg)
    {
        if (isDebug)
        {
            Log.e(tag, msg);
        }
    }

    public static void d(String tag, String msg)
    {
        if (isDebug)
        {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg)
    {
        if (isDebug)
        {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg)
    {
        if (isDebug)
        {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg)
    {
        if (isDebug && !TextUtils.isEmpty(msg)) {
            {
                Log.e(tag, msg);
            }
        }


    }
}

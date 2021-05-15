package com.Blinger.base.base;

/**
 * 作者：310 Group
 * 时间：2020/4/1 21:59
 * 邮箱：1760567382@qq.com
 * 功能：
 */

public interface BaseView
{
    /**
     * send Object to View
     * @param obj
     */
    void showData(Object obj);

    /**
     * send error msg to View
     * @param msg
     */
    void showError(String msg);
}

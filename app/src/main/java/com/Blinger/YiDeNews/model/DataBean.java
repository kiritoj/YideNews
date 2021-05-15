package com.Blinger.YiDeNews.model;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：310Lab
 * 时间：2020/4/1 23:48
 * 邮箱：1760567382@qq.com
 * 功能：二级实体类
 */
public class DataBean<T>
{
    @SerializedName("stat")
    private int code;
    @SerializedName("data")
    private T t;

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public T getT()
    {
        return t;
    }

    public void setT(T t)
    {
        this.t = t;
    }
}

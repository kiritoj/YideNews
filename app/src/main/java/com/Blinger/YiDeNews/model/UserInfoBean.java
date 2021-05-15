package com.Blinger.YiDeNews.model;

/**
 * Created by 社会主义好
 */

/**
 * modified by 逃课
 * 功能：用户提交不想看的新闻后返回的状态
 */
public class UserInfoBean {
    private int status;
    private String info;
    private Object object;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "status:" + status + ",info:" + info + ",onject:" + object;
    }
}

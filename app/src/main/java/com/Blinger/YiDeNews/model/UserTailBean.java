package com.Blinger.YiDeNews.model;

/**
 * Created by 社会主义好
 */

public class UserTailBean {
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
        return "info:" + info + " ,status:" + status + ",object" + object;
    }
}

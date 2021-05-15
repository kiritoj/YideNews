package com.Blinger.base;

/**
 * Created by tk
 * 功能：用于EventBus布尔类型的消息通知
 */
public class BooleanEvent {
    private boolean aBoolean;
    private String tag;
    public BooleanEvent(boolean aBoolean,String tag){
        this.aBoolean = aBoolean;
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public boolean getaBoolean(){
        return aBoolean;
    }
}

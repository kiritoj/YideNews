package com.Blinger.base.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

/**
 * Created by tk
 * 夜间模式：降低屏幕透明度
 */
public class NightModeUtil {
    public static void reduceTransparency(Float mAloha, Activity activity){
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();

        lp.alpha = mAloha; //设置透明度
        activity.getWindow().setAttributes(lp);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    public static void resetTransparency(Activity activity){
        WindowManager.LayoutParams lp1 = activity.getWindow().getAttributes();
        lp1.alpha = 1f;
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp1);
    }
}

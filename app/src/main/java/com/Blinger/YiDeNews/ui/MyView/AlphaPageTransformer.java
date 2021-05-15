package com.Blinger.YiDeNews.ui.MyView;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;



public class AlphaPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_ALPHA = 0.5f; //左右两侧的透明度
    private static final float MIN_SCALA = 0.8f; //左右两侧的缩放

    @Override
    public void transformPage(@NonNull View page, float position) {
        float alpha = 0f;
        float scalaY = 0f;
        if (position < -1  || position > 1) {
            //[-Infinity，-1）,透明度统一设置0.5
            alpha = MIN_ALPHA;
            scalaY = MIN_SCALA;
        } else if (position <= 1) {
            //[-1,1]
            //在huffing过程中，每个page的
            if (position < 0) {
                // [-1,0),动态改变透明度
                // 1 + position 的范围是（0，1）
                // *（1 - MIN_ALPHA）的范围是 （0，1 - MIN_ALPHA）
                // + MIN_ALPHA 的范围是(MIN_ALPHA,1)
                alpha = (1 + position) * (1 - MIN_ALPHA) + MIN_ALPHA;
                scalaY = (1 + position) * (1 - MIN_SCALA) + MIN_SCALA;
            }else{
                //（0，1）
                alpha = (1 - position) * (1 - MIN_ALPHA) + MIN_ALPHA;
                scalaY = (1 - position) * (1 - MIN_SCALA) + MIN_SCALA;
            }
        }
        page.setAlpha(alpha);
        page.setScaleY(scalaY);
    }
}

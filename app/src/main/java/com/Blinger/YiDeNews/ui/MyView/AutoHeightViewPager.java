package com.Blinger.YiDeNews.ui.MyView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;



public class AutoHeightViewPager extends ViewPager {
    public AutoHeightViewPager(@NonNull Context context) {
        super(context);
    }

    public AutoHeightViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        //下面遍历所有child的高度
        for (int i = 0; i < getChildCount(); i++) {
            ImageView child = (ImageView) getChildAt(i);
            if (child.getDrawable() != null){
                height = child.getDrawable().getIntrinsicHeight();
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                        MeasureSpec.EXACTLY);

            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

package com.Blinger.base.ui;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

/**
 * 作者：310Lab
 * 时间：2020/4/1 22:46
 * 邮箱：1760567382@qq.com
 * 功能：
 */

public class SupportPopupWindow extends PopupWindow
{

    public SupportPopupWindow(View contentView, int width, int height)
    {
        super(contentView, width, height);
    }

    @Override
    public void showAsDropDown(View anchor)
    {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N)
        {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff)
    {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N)
        {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }
}

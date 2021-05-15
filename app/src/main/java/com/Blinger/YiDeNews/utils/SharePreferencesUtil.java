package com.Blinger.YiDeNews.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by tk on
 */
public class SharePreferencesUtil {
    /**
     * 将用户头像转为Bitmap保存
     * @param context
     * @param key
     * @param bitmap
     * @return
     */
    public static boolean putBitmap(Context context, String key, Bitmap bitmap) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("init", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (bitmap == null || bitmap.isRecycled()) {
            return false;
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            String imageBase64 = new String(Base64.encode(baos.toByteArray(),
                    Base64.DEFAULT));

            editor.putString(key, imageBase64);
            return editor.commit();
        }
    }

    /**
     * 获取用户头像
     * @param context
     * @param key
     * @return
     */
    public static Bitmap getBitmap(Context context, String key
    ) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("init", MODE_PRIVATE);
        String imageBase64 = sharedPreferences.getString(key, "");
        if (TextUtils.isEmpty(imageBase64)) {
            return null;
        }

        byte[] base64Bytes = Base64.decode(imageBase64.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        Bitmap ret = BitmapFactory.decodeStream(bais);
        if (ret != null) {
            return ret;
        } else {
            return null;
        }
    }
}

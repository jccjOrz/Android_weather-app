package com.jc.weather.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 消息提示工具类
 *
 * @author jc
 */
public class ToastUtils {

    /**
     * 长消息
     *
     * @param context 上下文参数
     * @param jc     内容
     */
    public static void showLongToast(Context context, CharSequence jc) {
        Toast.makeText(context.getApplicationContext(), jc, Toast.LENGTH_LONG).show();
    }

    /**
     * 短消息
     *
     * @param context 上下文参数
     * @param jc     内容
     */
    public static void showShortToast(Context context, CharSequence jc) {
        Toast.makeText(context.getApplicationContext(), jc, Toast.LENGTH_SHORT).show();
    }
}

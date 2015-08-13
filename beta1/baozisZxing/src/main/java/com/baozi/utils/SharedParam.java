package com.baozi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 2015/7/8.
 */
public class SharedParam {
    public static void setParam(Context context, String name, String value) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("Shop",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static String getParam(Context context, String name, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Shop",
                Activity.MODE_PRIVATE);
        return sharedPreferences.getString(name, value);
    }
}

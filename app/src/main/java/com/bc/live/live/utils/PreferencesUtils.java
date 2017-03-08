package com.bc.live.live.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/2/24.
 */
public class PreferencesUtils {

    private static final String PREFERENCES_NAME = "shop_common";

    //存储
    public static boolean putString(Context context,String key,String value){
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_NAME
                ,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key,value);
        return editor.commit();
    }
    //读取
    public static String getString(Context context,String key){
            return getString(context,key,null);
    }
    public static String getString(Context context,String key,String defaultValue){
            SharedPreferences settings = context.getSharedPreferences(PREFERENCES_NAME
                    ,Context.MODE_PRIVATE);
        return settings.getString(key,defaultValue);
    }
}

package com.bc.live.live.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/2/24.
 */
public class GsonUtils {
    //这样创建兼容性更好
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static Gson getGson(){
        return gson;
    }

    //转为对象
    public static <T> T fromJson(String json,Class<T> cls){
        return gson.fromJson(json,cls);
    }
    //转为集合
    public static <T> T fromJson(String json,Type type){
        return gson.fromJson(json,type);
    }

    public static String toJson(Object object){
        return gson.toJson(object);
    }
}

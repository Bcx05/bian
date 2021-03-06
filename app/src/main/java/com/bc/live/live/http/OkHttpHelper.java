package com.bc.live.live.http;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/16.
 */
public class OkHttpHelper {

    private static OkHttpClient okHttpClient;
    private Gson mGson;
    private static OkHttpHelper mInstance;
    private Handler mHandler;

    //静态代码块在类被加载的时候就执行 并且只执行一次
    static {
        mInstance = new OkHttpHelper();
    }

    private OkHttpHelper(){
        okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).build();
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpHelper getInstance(){
        return mInstance;
    }

    public void doRequest(final Request request, final BaseCallBack baseCallBack){
        baseCallBack.onRequestBefore(request);
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    baseCallBack.onFailure(request,e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String resultStr = response.body().string();
                    // response.body().string()此方法只可调用一次
                    if(response.isSuccessful()){
                        if(baseCallBack.mType == String.class){
//                            baseCallBack.onSuccess(response,resultStr);
                            callbackSuccess(baseCallBack,response,resultStr);
                        }else {
                            try {
                                Object object = mGson.fromJson(resultStr,baseCallBack.mType);
                                //baseCallBack.onSuccess(response,object);
                                callbackSuccess(baseCallBack,response,object);
                            } catch (JsonSyntaxException e) {//json语法异常
                                baseCallBack.onError(response,response.code(),e);
                            } catch (Exception e) {
                                baseCallBack.onError(response,response.code(),e);
                            }
                        }

                    }else {
                        baseCallBack.onError(response,response.code(),null);
                    }
                    //不论成功失败都会走到
                    baseCallBack.onResponse(response);

                }
            });
    }

    public void get(String url,BaseCallBack callBack){
        Request request = buildRequest(url,null, HttpMethodType.GET);
        doRequest(request,callBack);
    }
    public void post(String url, Map<String,Object> params ,BaseCallBack callBack){
        Request request = buildRequest(url,params, HttpMethodType.POST);
        doRequest(request,callBack);
    }
    public Request buildRequest(String url, Map<String,Object> params,HttpMethodType methodType){

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if(methodType == HttpMethodType.GET){
            builder.get();
        }else if(methodType == HttpMethodType.POST){
            RequestBody body = buildForData(params);
            builder.post(body);
        }
        return builder.build();
    }

    private RequestBody buildForData(Map<String ,Object> params){
        FormBody.Builder builder = new FormBody.Builder();
            if(params!=null){
                for (Map.Entry<String,Object> entry : params.entrySet()){
                    builder.add(entry.getKey(),entry.getValue()==null? "" :
                    entry.getValue().toString());
                }
            }
        return builder.build();
    }

    //用于会掉
    private void callbackSuccess(final BaseCallBack callback, final Response response, final Object object){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response,object);
            }
        });
    }

    enum HttpMethodType{
        GET,
        POST
    }


}

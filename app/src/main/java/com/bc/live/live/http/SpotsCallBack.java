package com.bc.live.live.http;

import android.content.Context;

import java.io.IOException;

import dmax.dialog.SpotsDialog;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/17.
 */
public abstract class SpotsCallBack<T> extends BaseCallBack<T> {

    private Context mContext;
    private SpotsDialog mDialog;

    public SpotsCallBack(Context context) {
        this.mContext = context;
        mDialog = new SpotsDialog(mContext,"拼命加载中...");
    }

    //显示对话框
    public void showDialog(){
        mDialog.show();
    }
    //关闭对话框
    public void dismissDialog(){
        if(mDialog!=null){
            mDialog.dismiss();
        }
    }

    //设置文字信息
    public void setMessage(String message){
        mDialog.setMessage(message);
    }

    //失败就关闭对话框
    @Override
    public void onFailure(Request request, IOException e) {
        dismissDialog();
    }

    @Override
    public void onResponse(Response response) {
        dismissDialog();
    }


    @Override
    public void onRequestBefore(Request request) {
        showDialog();
    }

}

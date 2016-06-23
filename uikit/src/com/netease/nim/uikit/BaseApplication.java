package com.netease.nim.uikit;

import android.app.Application;

import com.zongbutech.httplib.http.API.NtfinaceApi;
import com.zongbutech.httplib.http.Utils.OkHttpClientServer;
import com.zongbutech.httplib.http.service.RetrofitService;
import com.zongbutech.httplib.http.service.RetrofitYangService;

import okhttp3.OkHttpClient;

/**
 * Created by lixian on 2016/6/23.
 */
public class BaseApplication extends Application {


    public OkHttpClient mOkHttpClient;
    public NtfinaceApi mNtfinaceApi;
    public NtfinaceApi mNtfinaceApiYang;

    public void onCreate() {
        super.onCreate();

        mOkHttpClient = OkHttpClientServer.getOkHttpClient(getApplicationContext());
        mNtfinaceApi = RetrofitService.getInstance(mOkHttpClient, NtfinaceApi.BaseApi).createService(NtfinaceApi.class);
        mNtfinaceApiYang = RetrofitYangService.getInstance(mOkHttpClient, NtfinaceApi.BaseApiYang).createService(NtfinaceApi.class);



    }
}

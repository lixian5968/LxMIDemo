package com.zongbutech.httplib.http.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Description : OkHttp网络连接封装工具类
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/17
 */
public class OkHttpUtils {


    private static final String TAG = "OkHttpUtils";

    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    private OkHttpUtils(Context ct) {
        mOkHttpClient = OkHttpClientServer.getOkHttpClient(ct);
        //1.要刷新UI，handler要用到主线程的looper。
        // 那么在主线程 Handler handler = new Handler();，
        // 如果在其他线程，也要满足这个功能的话，要Handler handler = new Handler(Looper.getMainLooper());
        mDelivery = new Handler(Looper.getMainLooper());
    }


    //synchronized，当它用来修饰一个方法或者一个代码块的时候，能够保证在同一时刻最多只有一个线程执行该段代码。
    private synchronized static OkHttpUtils getmInstance(Context ct) {
        if (mInstance == null) {
            mInstance = new OkHttpUtils(ct);
        }
        return mInstance;
    }

    private void getRequest(String url, final ResultCallback callback) {
        final Request request = new Request.Builder().url(url).build();
        Log.e("ruitong,getRequest,back", url);
        deliveryResult(callback, request);
    }

    private String getRequest(String url) {
        final Request request = new Request.Builder().url(url).build();
        Log.e("ruitong,getRequest", url);
        return deliveryResult(request);
    }

    private void DeleteRequest(String url, final ResultCallback callback) {
        final Request request = new Request.Builder().url(url).delete().build();
        Log.e("ruitong,DeleteRequest", url);
        deliveryResult(callback, request);
    }

    private String DeleteRequest(String url) {
        final Request request = new Request.Builder().url(url).delete().build();
        Log.e("ruitong,DeleteRequest", url);
        return deliveryResult(request);
    }

    private void postRequest(String url, final ResultCallback callback, List<Param> params) {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    private String postRequest(String url, List<Param> params) {
        Request request = buildPostRequest(url, params);
        return deliveryResult(request);
    }

    private void putRequest(String url, final ResultCallback callback, List<Param> params) {
        Request request = buildPutRequest(url, params);
        deliveryResult(callback, request);
    }

    private String putRequest(String url, List<Param> params) {
        Request request = buildPutRequest(url, params);
        return deliveryResult(request);
    }

    private String deliveryResult(Request request) {
        try {
            return mOkHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            return "";
        }
    }


    private void deliveryResult(final ResultCallback callback, Request request) {

        mOkHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailCallback(callback, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    int code = response.code();
                    if (code == 200 || code == 204) {
                        String str = response.body().string();
                        if (callback.mType == String.class) {
                            sendSuccessCallBack(callback, str);
                        } else {
                            Object object = JsonUtils.deserialize(str, callback.mType);
                            sendSuccessCallBack(callback, object);
                        }
                    } else {
                        sendFailCallback(callback, new RuntimeException(response.body().string()));
                    }


                } catch (final Exception e) {
                    Log.e(TAG, "convert json failure" + e.getMessage());
                    sendFailCallback(callback, e);
                }

            }
        });
    }

    private void sendFailCallback(final ResultCallback callback, final Exception e) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFailure(e);
                }
            }
        });
    }

    private void sendSuccessCallBack(final ResultCallback callback, final Object obj) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onSuccess(obj);
                }
            }
        });
    }

    private Request buildPostRequest(String url, List<Param> params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }


    private Request buildPutRequest(String url, List<Param> params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        Log.e("ruitong,buildPutRequest", url);
        return new Request.Builder().url(url).put(requestBody).build();
    }
    /**********************对外接口************************/

    /**
     * get请求
     *
     * @param url      请求url
     * @param callback 请求回调
     */
    public static void get(Context ct, String url, ResultCallback callback) {
        getmInstance(ct).getRequest(url, callback);
    }

    public static String get(Context ct, String url) {
        return getmInstance(ct).getRequest(url);
    }

    public static void Delete(Context ct, String url, ResultCallback callback) {
        getmInstance(ct).DeleteRequest(url, callback);
    }

    public static String Delete(Context ct, String url) {
        return getmInstance(ct).DeleteRequest(url);
    }


    /**
     * post请求
     *
     * @param url      请求url
     * @param callback 请求回调
     * @param params   请求参数
     */
    public static void post(Context ct, String url, final ResultCallback callback, List<Param> params) {
        getmInstance(ct).postRequest(url, callback, params);
    }

    public static String post(Context ct, String url, List<Param> params) {
        return getmInstance(ct).postRequest(url, params);
    }

    /**
     * post请求
     *
     * @param url      请求url
     * @param callback 请求回调
     * @param params   请求参数
     */
    public static void put(Context ct, String url, final ResultCallback callback, List<Param> params) {
        getmInstance(ct).putRequest(url, callback, params);
    }

    public static String put(Context ct, String url, List<Param> params) {
        return getmInstance(ct).putRequest(url, params);
    }

    /**
     * http请求回调类,回调方法在UI线程中执行
     *
     * @param <T>
     */
    public static abstract class ResultCallback<T> {

        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        //Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
        ////getGenericSuperclass()获得带有泛型的父类
        //Type type=clazz.getGenericSuperclass();

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        /**
         * 请求成功回调
         *
         * @param response
         */
        public abstract void onSuccess(T response);

        /**
         * 请求失败回调
         *
         * @param e
         */
        public abstract void onFailure(Exception e);
    }

    /**
     * post请求参数类
     */
    public static class Param implements Serializable {

        public String key;
        public String value;

        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

    }


}

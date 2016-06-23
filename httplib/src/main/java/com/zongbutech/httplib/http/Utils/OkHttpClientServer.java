package com.zongbutech.httplib.http.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

import com.zongbutech.httplib.cookie.ClearableCookieJar;
import com.zongbutech.httplib.cookie.PersistentCookieJar;
import com.zongbutech.httplib.cookie.cache.SetCookieCache;
import com.zongbutech.httplib.cookie.persistence.SharedPrefsCookiePersistor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by lixian on 2016/6/1.
 */
public class OkHttpClientServer {
    public static OkHttpClient mOkHttpClient =null;
    public static OkHttpClient getOkHttpClient(Context ct) {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(20 * 1000, TimeUnit.MILLISECONDS).readTimeout(20 * 1000, TimeUnit.MILLISECONDS);
            ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(ct));
            mOkHttpClient = builder.cookieJar(cookieJar).build();
        }
        return mOkHttpClient;
    }



    public static boolean isOpenNetwork(Context ct) {
        ConnectivityManager connManager = (ConnectivityManager) ct.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }
}

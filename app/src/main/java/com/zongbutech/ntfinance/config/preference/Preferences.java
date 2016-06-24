package com.zongbutech.ntfinance.config.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.zongbutech.ntfinance.DemoCache;


/**
 * Created by hzxuwen on 2015/4/13.
 */
public class Preferences {
    private static final String KEY_USER_ACCOUNT = "account";
    private static final String KEY_USER_TOKEN = "token";
    private static final String KEY_APPKEY = "appkey";

    public static void saveUserAccount(String account) {
        saveString(KEY_USER_ACCOUNT, account);
    }

    public static String getUserAccount() {
        return getString(KEY_USER_ACCOUNT);
    }
    public static void saveUserToken(String token) {
        saveString(KEY_USER_TOKEN, token);
    }
    public static void saveAppKey(String appkey) {
        saveString(KEY_APPKEY, appkey);
    }

    public static String getUserToken() {
        return getString(KEY_USER_TOKEN);
    }
    public static String getAppKey() {
        return getString(KEY_APPKEY);
    }

    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    static SharedPreferences getSharedPreferences() {
        return DemoCache.getContext().getSharedPreferences("Demo", Context.MODE_PRIVATE);
    }
}

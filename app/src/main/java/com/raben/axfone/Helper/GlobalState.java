package com.raben.axfone.Helper;

/**
 * Created by Raben on 10/10/2017.
 */

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;

public class GlobalState extends Application {

    SharedPreferences isLogin;
    SharedPreferences.Editor isLoginEditor;

    public static GlobalState singleton;

    public static final String PREFS_LOGIN_CHECK = "is login in";

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreate() {
        super.onCreate();

        isLogin = this.getSharedPreferences(PREFS_LOGIN_CHECK, 0);
        isLoginEditor = isLogin.edit();

        singleton = this;
    }

    /**
     * @return MySIngleton instance
     */
    public GlobalState getInstance() {
        return singleton;
    }

    public String getPrefsIsLogin() {
        return isLogin.getString(PREFS_LOGIN_CHECK, "");
    }

    public void setPrefsIsLogin(String value, int resultCode) {
        if (resultCode == 1) {
            isLoginEditor.putString(PREFS_LOGIN_CHECK, value).commit();
        } else {
            isLoginEditor.remove(PREFS_LOGIN_CHECK).commit();
        }
    }
}

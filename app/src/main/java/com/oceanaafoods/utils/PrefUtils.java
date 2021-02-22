package com.oceanaafoods.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.oceanaafoods.interfaces.StringConstants;

public class PrefUtils {
    private static PrefUtils prefUtils;
    private Context context;

    private PrefUtils() {
    }

    private class PrefKeys {
        static final String BIDBOL_SHARED_PREF = StringConstants.APPNAME;
        static final String ISLOGIN = "isLogin";
        static final String USERID = "userId";
    }

    public static PrefUtils getInstance() {
        if (prefUtils == null) {
            synchronized (PrefUtils.class) {
                prefUtils = new PrefUtils();
            }
        }
        return prefUtils;
    }

    public void setPrefContext(Context context) {
        this.context = context;
    }

    private SharedPreferences getPreferences() {
        return context.getSharedPreferences(PrefKeys.BIDBOL_SHARED_PREF, 0);
    }

    public void saveUserId(int userId) {
        getPreferences().edit().putInt(PrefKeys.USERID, userId).apply();
    }

    public void saveIsLogin(boolean isLogin) {
        getPreferences().edit().putBoolean(PrefKeys.ISLOGIN, isLogin).apply();
    }

    public boolean isLogin() {
        return getPreferences().getBoolean(PrefKeys.ISLOGIN, false);
    }

    public void clearSharedPref() {
        getPreferences().edit().clear().commit();
    }

    public int getUserId() {
        return getPreferences().getInt(PrefKeys.USERID, 0);
    }
}

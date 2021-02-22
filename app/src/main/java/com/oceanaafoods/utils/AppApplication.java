package com.oceanaafoods.utils;

import android.app.Application;

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PrefUtils.getInstance().setPrefContext(getApplicationContext());
    }
}

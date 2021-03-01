package com.oceanaafoods.utils;

import android.app.Application;
import com.onesignal.OneSignal;

public class AppApplication extends Application {
    private static final String ONESIGNAL_APP_ID = "64264993-b645-4ac9-a566-2ddeb01c2305";

    @Override
    public void onCreate() {
        super.onCreate();
        PrefUtils.getInstance().setPrefContext(getApplicationContext());
        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}

package com.oceanaafoods.activities;


import android.os.Bundle;
import android.os.Handler;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.oceanaafoods.R;
import com.oceanaafoods.baseui.BaseAppCompactActivity;
import com.oceanaafoods.utils.ConnectivityDetector;
import com.oceanaafoods.utils.PrefUtils;

import butterknife.BindView;

public class SplashActivity extends BaseAppCompactActivity {
    private int delayTimeInSec = 3000;
    @BindView(R.id.coordinator)
    ConstraintLayout coordinator;

    @Override
    public int getActivityLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ConnectivityDetector.isNetworkAvailable(SplashActivity.this)) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    goToActivity(MapsActivity.class);
                    finish();
                }
            }, delayTimeInSec);
        } else {
            Snackbar.make(coordinator, getResources().getString(R.string.no_internet), Snackbar.LENGTH_LONG).show();
        }
    }
}

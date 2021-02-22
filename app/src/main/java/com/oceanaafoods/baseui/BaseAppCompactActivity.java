package com.oceanaafoods.baseui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.oceanaafoods.R;
import com.oceanaafoods.interfaces.IActivityBaseView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseAppCompactActivity extends AppCompatActivity implements IActivityBaseView {

    private ProgressDialog progressDialog;
    //    public Toolbar toolbar;
    private Unbinder unbinder;

    public abstract int getActivityLayout();

    public String getToolbarTitle() {
        return null;
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);
        ViewStub viewStub = (ViewStub) findViewById(R.id.layout_activity);
        viewStub.setLayoutResource(getActivityLayout());
        viewStub.inflate();
        unbinder = ButterKnife.bind(this);


        if (showUpNavigation()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    protected boolean isToolBarEnabled() {
        return false;
    }

    protected boolean showUpNavigation() {
        return false;
    }


    @Override
    public void showProgressDialog(String msg) {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);

        }
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public boolean isProgressVisible() {
        if (progressDialog.isShowing())
            return true;

        return false;
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    private String[] getPermissionsToAsk(String... permissions) {

        List<String> needToAskPermissions = new ArrayList<>();

        for (String permission : permissions) {
            if (!(ActivityCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_GRANTED))
                needToAskPermissions.add(permission);
        }

        return needToAskPermissions.toArray(new String[]{});
    }

    protected boolean requestPermission(int requestCode, String... permissions) {

        String[] permissionsToAsk = getPermissionsToAsk(permissions);

        if (permissionsToAsk.length == 0) {
            return true;
        }

        ActivityCompat.requestPermissions(this,
                permissionsToAsk,
                requestCode);

        return false;
    }

    protected boolean hasGrantPermissions(int[] grantResults) {
        if (grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    protected void goToActivity(Class clazz) {
        if (clazz != null) {
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
        }
    }

    protected void goToActivityClearTop(Class clazz) {
        if (clazz != null) {
            Intent intent = new Intent(this, clazz);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
    protected void goToActivityWithClearTask(Class clazz) {
        if (clazz != null) {
            Intent intent = new Intent(this, clazz);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    protected void goToActivity(Class clazz, Bundle args) {

        if (clazz != null) {
            Intent intent = new Intent(this, clazz);
            intent.putExtras(args);
            startActivity(intent);
        }
    }


    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public int applyColor(@ColorRes int colorId) {
        return ContextCompat.getColor(this, colorId);
    }

    @Override
    public void showAlertDialog(String title, String alertMsg, final OnPositiveClick onPositiveClick) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle(title);
        // Setting Dialog Message
        alertDialog.setMessage(alertMsg);

        // Setting Positive "ok" Button
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onPositiveClick.onClick();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    public void showAlertDialog(String title, String alertMsg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle(title);
        // Setting Dialog Message
        alertDialog.setMessage(alertMsg);

        // Setting Positive "ok" Button
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    protected void onStop() {
        dismissProgress();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

}

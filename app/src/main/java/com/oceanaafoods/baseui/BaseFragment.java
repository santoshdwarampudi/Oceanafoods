package com.oceanaafoods.baseui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;


import com.oceanaafoods.R;
import com.oceanaafoods.interfaces.IActivityBaseView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {

    public abstract int getFragmentLayoutId();
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getFragmentLayoutId();
        View view = inflater.inflate(R.layout.frag_base, container, false);
        ViewStub viewStub = (ViewStub) view.findViewById(R.id.layout_fragment);
        viewStub.setLayoutResource(layoutId);
        viewStub.inflate();
        unbinder=ButterKnife.bind(this, view);
        return view;
    }

    protected ProgressDialog progressDialog;

    public void showProgressDialog(String msg) {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
        }

        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void showToast(String msg) {
        if (isUsable()) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isProgressVisible() {
        if (progressDialog.isShowing())
            return true;

        return false;
    }


    public boolean isUsable() {
        return getActivity() != null;
    }

    protected void goToActivity(Class clazz) {
        if (clazz != null) {
            Intent intent = new Intent(getActivity(), clazz);
            startActivity(intent);
        }
    }

    protected void goToActivity(Class clazz, Bundle args) {
        if (clazz != null) {
            Intent intent = new Intent(getActivity(), clazz);
            intent.putExtras(args);
            startActivity(intent);
        }
    }

    private String[] getPermissionsToAsk(String... permissions) {

        List<String> needToAskPermissions = new ArrayList<>();

        for (String permission : permissions) {
            if (!(ActivityCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED))
                needToAskPermissions.add(permission);
        }

        return needToAskPermissions.toArray(new String[]{});
    }

    protected boolean requestPermissions(int requestCode, String... permissions) {

        String[] permissionsToAsk = getPermissionsToAsk(permissions);

        if (permissionsToAsk.length == 0) {
            return true;
        }

        requestPermissions(permissionsToAsk,
                requestCode);

        return false;
    }


    public void showAlertDialog(String title, String alertMsg, final IActivityBaseView.OnPositiveClick onPositiveClick) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

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
    public void showAlertDialog(String title, String alertMsg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

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
    public void onDestroyView() {
        super.onDestroyView();
        progressDialog = null;
        unbinder.unbind();
    }
}

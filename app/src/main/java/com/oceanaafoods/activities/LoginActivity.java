package com.oceanaafoods.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.oceanaafoods.R;
import com.oceanaafoods.baseui.BaseAppCompactActivity;
import com.oceanaafoods.interfaces.ILoginView;
import com.oceanaafoods.interfaces.StringConstants;
import com.oceanaafoods.models.requests.LoginRequest;
import com.oceanaafoods.models.responses.LoginResponse;
import com.oceanaafoods.presenters.LoginPresenter;
import com.oceanaafoods.utils.APIClient;
import com.oceanaafoods.utils.PrefUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseAppCompactActivity implements ILoginView {

    private LoginPresenter loginPresenter;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.tv_heading)
    TextView tv_heading;
    @BindView(R.id.iv_menu)
    ImageView iv_menu;

    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        checkValidations();
    }

    @OnClick(R.id.tv_register)
    public void onRegisterClick() {
        goToActivity(RegisterActivity.class);
    }

    @OnClick(R.id.iv_menu)
    public void onBackClick() {
        onBackPressed();
    }

    public LoginActivity() {
        loginPresenter = new LoginPresenter(this, APIClient.getAPIService());
    }

    private void checkValidations() {
        if (et_username.getText().toString() == null || et_username.getText().toString().isEmpty()) {
            showToast("Please enter username");
        } else if (et_pwd.getText().toString() == null || et_pwd.getText().toString().isEmpty()) {
            showToast("Please enter password");
        } else {
            callLoginApi();
        }
    }

    private void callLoginApi() {
        if (loginPresenter == null)
            loginPresenter = new LoginPresenter(this, APIClient.getAPIService());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(et_pwd.getText().toString());
        loginRequest.setPhoneremail(et_username.getText().toString());
        loginRequest.setLastLogin(StringConstants.ANDROID);
        loginPresenter.loginUser(loginRequest);
    }

    @Override
    public int getActivityLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_heading.setText(getResources().getString(R.string.login));
        iv_menu.setVisibility(View.GONE);
    }

    @Override
    public void onLoginSuccess(LoginResponse loginResponse) {
        if (loginResponse != null) {
            if (loginResponse.getSuccess() == 1) {
                showToast(loginResponse.getMessage());
                PrefUtils.getInstance().saveIsLogin(true);
                PrefUtils.getInstance().saveUserId(Integer.parseInt(loginResponse.getUserid()));
                goToActivityClearTop(HomeActivity.class);
            }
        }
    }

    @Override
    public void onLoginFailed() {
        showToast("Failed to login,please try later");
    }
}

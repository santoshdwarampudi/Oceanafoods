package com.oceanaafoods.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.oceanaafoods.R;
import com.oceanaafoods.baseui.BaseAppCompactActivity;
import com.oceanaafoods.interfaces.IRegisterView;
import com.oceanaafoods.interfaces.StringConstants;
import com.oceanaafoods.models.requests.RegisterRequest;
import com.oceanaafoods.models.responses.RegisterResponse;
import com.oceanaafoods.presenters.RegisterPresenter;
import com.oceanaafoods.utils.APIClient;
import com.oceanaafoods.utils.PrefUtils;
import com.oceanaafoods.utils.ValidationUtil;

import butterknife.BindView;
import butterknife.OnClick;


public class RegisterActivity extends BaseAppCompactActivity implements IRegisterView {

    private RegisterPresenter registerPresenter;
    @BindView(R.id.et_fname)
    EditText et_fname;
    @BindView(R.id.et_lname)
    EditText et_lname;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_mobileNumber)
    EditText et_mobileNumber;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.tv_heading)
    TextView tv_heading;

    @OnClick(R.id.iv_menu)
    public void onBackClick() {
        finish();
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick() {
        validCredentials();
    }


    @Override
    public int getActivityLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_heading.setText("Register");
        registerPresenter = new RegisterPresenter(this, APIClient.getAPIService());
    }

    private void validCredentials() {
        if (ValidationUtil.isEditTextEmpty(et_fname)) {
            showToast("First Name is required");
        } else if (ValidationUtil.isEditTextEmpty(et_lname)) {
            showToast("Last Name is required");
        } else if (ValidationUtil.isEditTextEmpty(et_email)) {
            showToast("Email is required");
        } else if (!ValidationUtil.isValidEmail(et_email.getText().toString())) {
            showToast("Valid Email is required");
        } else if (ValidationUtil.isEditTextEmpty(et_mobileNumber)) {
            showToast("Mobile Number is required");
        } else if (!ValidationUtil.isValidMobile(et_mobileNumber.getText().toString())) {
            showToast("Valid Mobile Number is required");
        } else if (ValidationUtil.isEditTextEmpty(et_pwd)) {
            showToast("Password is required");
        } else {
            callLoginApiService();
        }
    }

    private void callLoginApiService() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(et_email.getText().toString());
        registerRequest.setFirstname(et_fname.getText().toString());
        registerRequest.setLastname(et_lname.getText().toString());
        registerRequest.setPassword(et_pwd.getText().toString());
        registerRequest.setPhone(et_mobileNumber.getText().toString());
        registerRequest.setSource(StringConstants.ANDROID);
        registerPresenter.registerUser(registerRequest);
    }

    @Override
    public void registerSuccess(RegisterResponse registerResponse) {
        if (registerResponse != null) {
            if (registerResponse.getSuccess() == 1) {
                showToast(registerResponse.getMessage());
                PrefUtils.getInstance().saveIsLogin(true);
                PrefUtils.getInstance().saveUserId(registerResponse.getUserid());
                goToActivityClearTop(HomeActivity.class);
            } else {
                showToast(registerResponse.getMessage());
            }
        } else {
            showToast("Failed to register,please try again");
        }
    }

    @Override
    public void registerFailure() {
        showToast("Failed to register,please try again");
    }
}

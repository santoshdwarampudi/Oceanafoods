package com.oceanaafoods.presenters;

import com.oceanaafoods.interfaces.ApiInterface;
import com.oceanaafoods.interfaces.ILoginView;
import com.oceanaafoods.models.requests.LoginRequest;
import com.oceanaafoods.models.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class LoginPresenter {
    private ILoginView iLoginView;
    private ApiInterface apiInterface;

    public LoginPresenter(ILoginView iLoginView, ApiInterface apiInterface) {
        this.iLoginView = iLoginView;
        this.apiInterface = apiInterface;
    }

    public void loginUser(@Body LoginRequest loginRequest) {
        iLoginView.showProgressDialog("Login user....");
        Call<LoginResponse> loginResponseCall = apiInterface.loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                iLoginView.dismissProgress();
                iLoginView.onLoginSuccess(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                iLoginView.dismissProgress();
                iLoginView.onLoginFailed();
            }
        });
    }
}

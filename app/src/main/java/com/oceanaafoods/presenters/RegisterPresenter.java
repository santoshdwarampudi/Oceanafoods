package com.oceanaafoods.presenters;

import com.oceanaafoods.interfaces.ApiInterface;
import com.oceanaafoods.interfaces.IRegisterView;
import com.oceanaafoods.models.requests.RegisterRequest;
import com.oceanaafoods.models.responses.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter {
    private IRegisterView iRegisterView;
    private ApiInterface apiInterface;

    public RegisterPresenter(IRegisterView iRegisterView, ApiInterface apiInterface) {
        this.iRegisterView = iRegisterView;
        this.apiInterface = apiInterface;
    }

    public void registerUser(RegisterRequest registerRequest) {
        iRegisterView.showProgressDialog("Please wait..");
        Call<RegisterResponse> registerResponseCall = apiInterface.registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                iRegisterView.dismissProgress();
                iRegisterView.registerSuccess(response.body());
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                iRegisterView.dismissProgress();
                iRegisterView.registerFailure();
            }
        });
    }
}

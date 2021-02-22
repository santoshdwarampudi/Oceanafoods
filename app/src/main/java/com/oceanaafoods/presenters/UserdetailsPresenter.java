package com.oceanaafoods.presenters;

import com.oceanaafoods.interfaces.ApiInterface;
import com.oceanaafoods.interfaces.IUserDetailView;
import com.oceanaafoods.models.responses.UserDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserdetailsPresenter {
    private ApiInterface apiInterface;
    private IUserDetailView iUserDetailView;


    public UserdetailsPresenter(ApiInterface apiInterface, IUserDetailView iUserDetailView) {
        this.apiInterface = apiInterface;
        this.iUserDetailView = iUserDetailView;
    }

    public void getUserDetails(String userId, boolean showProgress) {
        if (showProgress)
            iUserDetailView.showProgressDialog("Getting UserDetails..");
        Call<UserDetailsResponse> userDetailsResponseCall = apiInterface.getUserDetails(userId);
        userDetailsResponseCall.enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {
                if (showProgress)
                    iUserDetailView.dismissProgress();
                iUserDetailView.onUserDetailsSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                if (showProgress)
                    iUserDetailView.dismissProgress();
                iUserDetailView.onUserDetailsFailed();
            }
        });
    }
}

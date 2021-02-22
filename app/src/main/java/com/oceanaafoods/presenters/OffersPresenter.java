package com.oceanaafoods.presenters;

import com.oceanaafoods.interfaces.ApiInterface;
import com.oceanaafoods.interfaces.IOffersView;
import com.oceanaafoods.models.responses.OffersResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffersPresenter {
    private ApiInterface apiInterface;
    private IOffersView iOffersView;

    public OffersPresenter(ApiInterface apiInterface, IOffersView iOffersView) {
        this.apiInterface = apiInterface;
        this.iOffersView = iOffersView;
    }

    public void getOffers(final boolean showprogress) {
        if (showprogress)
            iOffersView.showProgressDialog("Getting Offers..");
        Call<OffersResponse> offersResponseCall = apiInterface.getOffers();
        offersResponseCall.enqueue(new Callback<OffersResponse>() {
            @Override
            public void onResponse(Call<OffersResponse> call, Response<OffersResponse> response) {
                if (showprogress)
                    iOffersView.dismissProgress();
                iOffersView.getOffersResponseSuccess(response.body());
            }

            @Override
            public void onFailure(Call<OffersResponse> call, Throwable t) {
                if (showprogress)
                    iOffersView.dismissProgress();
                iOffersView.getOffersResponseFailed();
            }
        });
    }
}

package com.oceanaafoods.presenters;

import com.oceanaafoods.interfaces.ApiInterface;
import com.oceanaafoods.interfaces.IScratchCardView;
import com.oceanaafoods.models.responses.ScratchCardResponse;
import com.oceanaafoods.models.responses.UpdateScratchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScratchcardPresenter {
    private ApiInterface apiInterface;
    private IScratchCardView iScratchCardView;

    public ScratchcardPresenter(ApiInterface apiInterface, IScratchCardView iScratchCardView) {
        this.apiInterface = apiInterface;
        this.iScratchCardView = iScratchCardView;
    }

    public void getScratchCards(String userId) {
        iScratchCardView.showProgressDialog("Getting ScracthCards...");
        Call<ScratchCardResponse> scratchCardResponseCall = apiInterface.getScratchCards(userId);
        scratchCardResponseCall.enqueue(new Callback<ScratchCardResponse>() {
            @Override
            public void onResponse(Call<ScratchCardResponse> call, Response<ScratchCardResponse> response) {
                iScratchCardView.onScratchCardSuccess(response.body());
                iScratchCardView.dismissProgress();
            }

            @Override
            public void onFailure(Call<ScratchCardResponse> call, Throwable t) {
                iScratchCardView.onScratchCardFailed();
                iScratchCardView.dismissProgress();
            }
        });
    }

    public void updateScratchCard(String scratchId) {

        Call<UpdateScratchResponse> updateScratchResponseCall = apiInterface.updateScratchCardResponse(scratchId);
        updateScratchResponseCall.enqueue(new Callback<UpdateScratchResponse>() {
            @Override
            public void onResponse(Call<UpdateScratchResponse> call, Response<UpdateScratchResponse> response) {
                iScratchCardView.onScratchUpdateSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UpdateScratchResponse> call, Throwable t) {
                iScratchCardView.onScratchUpdateFailed();
            }
        });
    }
}

package com.oceanaafoods.presenters;

import com.oceanaafoods.interfaces.ApiInterface;
import com.oceanaafoods.interfaces.IMenuProductView;
import com.oceanaafoods.models.responses.MenuProductsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuProductPresenter {
    private IMenuProductView iMenuView;
    private ApiInterface apiInterface;

    public MenuProductPresenter(IMenuProductView iMenuView, ApiInterface apiInterface) {
        this.iMenuView = iMenuView;
        this.apiInterface = apiInterface;
    }


    public void getMenuProducts(final boolean showprogress, String menuId) {
        if (showprogress)
            iMenuView.showProgressDialog("Getting Products");
        Call<MenuProductsResponse> menuProductsResponseCall = apiInterface.getMenuProductResponse(menuId);
        menuProductsResponseCall.enqueue(new Callback<MenuProductsResponse>() {
            @Override
            public void onResponse(Call<MenuProductsResponse> call, Response<MenuProductsResponse> response) {
                if (showprogress)
                    iMenuView.dismissProgress();
                iMenuView.getMenuProductsSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MenuProductsResponse> call, Throwable t) {
                if (showprogress)
                    iMenuView.dismissProgress();
                iMenuView.getMenuProductsFailed();
            }
        });
    }
}

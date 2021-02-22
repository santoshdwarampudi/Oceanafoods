package com.oceanaafoods.presenters;

import com.oceanaafoods.interfaces.ApiInterface;
import com.oceanaafoods.interfaces.IMenuView;
import com.oceanaafoods.models.responses.MenuItemsResponse;
import com.oceanaafoods.models.responses.MenuTypesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuPresenter {
    private IMenuView iMenuView;
    private ApiInterface apiInterface;

    public MenuPresenter(IMenuView iMenuView, ApiInterface apiInterface) {
        this.iMenuView = iMenuView;
        this.apiInterface = apiInterface;
    }

    public void getMenuTypes(final boolean showProgress) {
        if (showProgress)
            iMenuView.showProgressDialog("Getting Menu");
        Call<MenuTypesResponse> menuTypesResponseCall = apiInterface.getMenuTypes();
        menuTypesResponseCall.enqueue(new Callback<MenuTypesResponse>() {
            @Override
            public void onResponse(Call<MenuTypesResponse> call, Response<MenuTypesResponse> response) {
                if (showProgress)
                    iMenuView.dismissProgress();
                iMenuView.getMenuTypesSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MenuTypesResponse> call, Throwable t) {
                if (showProgress)
                    iMenuView.dismissProgress();
                iMenuView.getMenuTypesFailed();

            }
        });
    }

    public void getMenuItems(final boolean showProgress, String menuItemId) {
        if (showProgress)
            iMenuView.showProgressDialog("Getting Menu Items");
        Call<MenuItemsResponse> menuItemsResponseCall = apiInterface.getMenuItemResponse(menuItemId);
        menuItemsResponseCall.enqueue(new Callback<MenuItemsResponse>() {
            @Override
            public void onResponse(Call<MenuItemsResponse> call, Response<MenuItemsResponse> response) {
                if (showProgress)
                    iMenuView.dismissProgress();
                iMenuView.getMenuItemsSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MenuItemsResponse> call, Throwable t) {
                if (showProgress)
                    iMenuView.dismissProgress();
                iMenuView.getMenuItemsFailed();

            }
        });
    }
}

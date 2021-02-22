package com.oceanaafoods.interfaces;

import com.oceanaafoods.models.requests.LoginRequest;
import com.oceanaafoods.models.requests.RegisterRequest;
import com.oceanaafoods.models.responses.LoginResponse;
import com.oceanaafoods.models.responses.MenuItemsResponse;
import com.oceanaafoods.models.responses.MenuProductsResponse;
import com.oceanaafoods.models.responses.MenuTypesResponse;
import com.oceanaafoods.models.responses.OffersResponse;
import com.oceanaafoods.models.responses.RegisterResponse;
import com.oceanaafoods.models.responses.ScratchCardResponse;
import com.oceanaafoods.models.responses.UpdateScratchResponse;
import com.oceanaafoods.models.responses.UserDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET(APIConstants.MENUTYPE)
    Call<MenuTypesResponse> getMenuTypes();

    @GET(APIConstants.MENUITEMS)
    Call<MenuItemsResponse> getMenuItemResponse(@Path("menuId") String menuId);

    @GET(APIConstants.MENUPRODUCTS)
    Call<MenuProductsResponse> getMenuProductResponse(@Path("menuId") String menuId);

    @GET(APIConstants.OFFERS)
    Call<OffersResponse> getOffers();

    @POST(APIConstants.REGISTRATION)
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST(APIConstants.LOGIN)
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET(APIConstants.USERDETAILS)
    Call<UserDetailsResponse> getUserDetails(@Path("userId") String userId);

    @GET(APIConstants.SCRATCHCARDS)
    Call<ScratchCardResponse> getScratchCards(@Path("userId") String userId);

    @GET(APIConstants.UPDATESCRATCHSTATUS)
    Call<UpdateScratchResponse> updateScratchCardResponse(@Path("scratchCardId") String scratchCardId);

}

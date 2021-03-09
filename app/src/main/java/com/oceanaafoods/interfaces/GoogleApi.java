package com.oceanaafoods.interfaces;

import com.oceanaafoods.models.responses.google.PlaceResponse;
import com.oceanaafoods.models.responses.google.SearchLocationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleApi {
    String BASE_URL_MAPS = "https://maps.googleapis.com";

    @GET("/maps/api/place/autocomplete/json?&inputtype=textquery&region=in")
    Call<SearchLocationResponse> searchLocation(@Query("key") String key, @Query("input") String input);


    @GET("/maps/api/place/details/json")
    Call<PlaceResponse> placeByPlaceId(@Query("key") String key, @Query("placeid") String placeid);

}

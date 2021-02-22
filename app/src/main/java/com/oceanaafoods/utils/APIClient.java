package com.oceanaafoods.utils;

import androidx.annotation.NonNull;

import com.oceanaafoods.BuildConfig;
import com.oceanaafoods.interfaces.APIConstants;
import com.oceanaafoods.interfaces.ApiInterface;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static ApiInterface apiInterface;

    private static Retrofit retrofit = null;

    public static ApiInterface getAPIService() {
        if (apiInterface == null)
            apiInterface = getClient().create(ApiInterface.class);
        return apiInterface;
    }

    public static ApiInterface getAPIServiceForMultiPart() {
        if (apiInterface == null)
            apiInterface = getClientForMultiPart().create(ApiInterface.class);
        return apiInterface;
    }

    private static Retrofit getClientForMultiPart() {

        return new Retrofit.Builder()
                .baseUrl(APIConstants.BASE_URL)
                .client(getOkHttpClientObjectFormultipart())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static Retrofit getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(APIConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClientObject())
                .build();
        return retrofit;
    }

    private static OkHttpClient getOkHttpClientObject() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                Request request = builder.build();
                return chain.proceed(request);
            }
        });
        httpClient.addInterceptor(interceptor);// <-- this is the important line!
        return httpClient.build();
    }

    private static OkHttpClient getOkHttpClientObjectFormultipart() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                String boundary = "multipart/form-data; boundary=----" + System.currentTimeMillis() + "----";
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Content-Type", boundary);
                Request request = builder.build();
                return chain.proceed(request);
            }
        });
        if (BuildConfig.DEBUG) {
            // add logging as last interceptor
            httpClient.addInterceptor(logging);// <-- this is the important line!
        }
        return httpClient.build();
    }
}

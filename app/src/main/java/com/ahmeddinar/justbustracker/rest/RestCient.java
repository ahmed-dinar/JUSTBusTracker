package com.ahmeddinar.justbustracker.rest;

import com.ahmeddinar.justbustracker.rest.service.ApiService;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Ahmed Dinar on 6/29/2016.
 */
public class RestCient {

    public static final String API_BASE_URL  = "http://sheambd.com/bus/api/apitest/api/";
    private ApiService apiService;

    public RestCient(){

        OkHttpClient httpClient = new OkHttpClient();


        httpClient.interceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Api-Key", "omgverysecretapikeythisiswhysolamestop")
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json");

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });


        GsonConverterFactory gson = GsonConverterFactory.create();

        // Retrofit setup
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(httpClient)
                .addConverterFactory(gson)
                .build();


        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService(){
        return apiService;
    }

}
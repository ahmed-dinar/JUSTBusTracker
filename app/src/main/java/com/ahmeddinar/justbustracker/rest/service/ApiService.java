package com.ahmeddinar.justbustracker.rest.service;

import com.ahmeddinar.justbustracker.rest.model.BusLocation;

import java.util.List;

import retrofit.Call;

import retrofit.http.GET;

/**
 * Created by Ahmed Dinar on 6/29/2016.
 */
public interface ApiService {

    @GET("coordinate/all")
    Call<List<BusLocation>> get();

}

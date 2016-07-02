package com.ahmeddinar.justbustracker.rest.service;

import com.ahmeddinar.justbustracker.rest.model.BusLocation;

import java.util.List;

import rx.Observable;


import retrofit.http.GET;

/**
 * Created by Ahmed Dinar on 6/29/2016.
 */
public interface ApiService {

    @GET("coordinate/all")
    Observable<List<BusLocation>> get();

}

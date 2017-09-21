package com.booking.android.hotel.httpclient;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by Davit Galstyan on 7/18/17.
 */
public interface HotelsApi {

    @GET("/lookup.json?query=delh&lang=en&lookFor=both&limit=10")
    void getAllHotels (Callback<String> messagesCallBack);


    @GET("/search/getResult.json")
    void getHotel (@QueryMap Map<String, String> params, Callback<String> messagesCallBack);

    @GET("/search/start.json")
    void getHotelsByLocation (@QueryMap Map<String, String> params, Callback<String> messagesCallBack);


}

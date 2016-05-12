package com.example.hemil.rentapp.API;


import org.json.JSONObject;

import java.util.List;

import POJO.Property;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by hemil on 5/11/2016.
 */
public interface RestApiClass {

    @POST("/listing")
    JSONObject postProperty(@Body Property property);

    @Headers("Content-Type: application/json")
    @GET("/listings")
    List<Property> getAllListings();
}
package com.example.hemil.rentapp.API;


import org.json.JSONObject;

import java.util.List;

import POJO.Favourites;
import POJO.Property;
import POJO.SavedSearch;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Query;

/**
 * Created by hemil on 5/11/2016.
 */
public interface RestApiClass {

    @POST("/listing")
    JSONObject postProperty(@Body Property property);

    @Headers("Content-Type: application/json")
    @GET("/listings")
    List<Property> getAllListings();

    @Headers("Content-Type: application/json")
    @GET("/listing")
    List<Property> getLandlordProperty(@Query("userId") long userid);

    @PUT("/listing")
    JSONObject updateProperty(@Body Property property);

    @POST("/search")
    List<Property> getSearchResults(@Body SavedSearch savedSearch);

    @POST("/savedsearch")
    JSONObject saveSearchForUser(@Body SavedSearch savedSearch);

    @Headers("Content-Type: application/json")
    @DELETE("/listing")
    JSONObject deleteProperty(@Query("propertyId") long propertyId);

    @POST("/favourites")
    JSONObject addBookmark(@Body Favourites favourites);

    @Headers("Content-Type: application/json")
    @GET("/favourites")
    List<Property> getAllFavorites(@Query("userId") long userId);
}

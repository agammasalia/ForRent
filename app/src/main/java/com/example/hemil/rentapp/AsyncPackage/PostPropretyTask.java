package com.example.hemil.rentapp.AsyncPackage;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hemil.rentapp.API.RestApiClass;
import com.squareup.okhttp.OkHttpClient;

import POJO.Property;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by hemil on 5/11/2016.
 */

public class PostPropretyTask extends AsyncTask<String,Void,Void> {

    RestAdapter restAdapter;
    @Override
    protected void onPreExecute(){


        final OkHttpClient okHttpClient = new OkHttpClient();

        String url = "http://ec2-54-153-29-131.us-west-1.compute.amazonaws.com:8080/listings";

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(url)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(okHttpClient))
                .build();
    }

    @Override
    protected Void doInBackground(String... params) {

        final RestApiClass restApiClass = restAdapter.create(RestApiClass.class);

//        Property property = new Property(10000,propertyType,propertyPrice,propertyDescription,propertyTitle,propertyOwnerEmail,
//                propertyOwnerPhone,propertyStreetAddress,propertyCity,propertyState,propertyZip,propertyNumberOfBaths,
//                propertyNumberOfRooms,propertySquareFootage,"Available");

//        Property property = new Property(params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7]
//                ,params[8],params[9],params[10],params[11],params[12],params[13],params[14]);

////        Log.d("Property",property.toString());
////
////        String response = restApiClass.postProperty(property);
//
//        Log.d("Response", response);


        return null;
    }
}

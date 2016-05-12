package com.example.hemil.rentapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hemil.rentapp.API.RestApiClass;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;
import retrofit.*;
import POJO.Property;
import retrofit.client.OkClient;

/**
 * Created by hemil on 5/8/2016.
 */
public class LandlordPostActivity extends MainActivity implements AdapterView.OnItemSelectedListener {

    String propertyType,propertyDescription,propertyTitle, propertyOwnerEmail,propertyOwnerPhone,
    propertyStreetAddress,propertyCity,propertyState,propertyZip;
    int propertyNumberOfBaths =0, propertyNumberOfRooms = 0;
    double propertyPrice, propertySquareFootage;
    EditText title,price,rooms,bath,area,street,city,state,zipcode,email,phone,desc;
    Spinner spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.listView_home).setVisibility(View.INVISIBLE);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.landlord_post, null, false);
        mDrawer.addView(contentView, 0);

        //Do the rest as you want for each activity
        spinner = (Spinner) findViewById(R.id.spinner_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_resources, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        title = (EditText)  findViewById(R.id.post_title);
        price = (EditText)  findViewById(R.id.post_price);
        rooms = (EditText)  findViewById(R.id.post_rooms);
        bath = (EditText)  findViewById(R.id.post_bath);
        area = (EditText)  findViewById(R.id.post_area);
        street = (EditText)  findViewById(R.id.post_street);
        city = (EditText)  findViewById(R.id.post_city);
        state = (EditText)  findViewById(R.id.post_state);
        zipcode = (EditText)  findViewById(R.id.post_zipcode);
        email = (EditText)  findViewById(R.id.post_email);
        phone = (EditText)  findViewById(R.id.post_phone);
        desc = (EditText)  findViewById(R.id.post_desc);
    }

    public void postPropertyMethod(View view) throws JSONException {

         propertyDescription = desc.getText().toString();
         propertyTitle = title.getText().toString();
         propertyOwnerEmail = email.getText().toString();
         propertyOwnerPhone = phone.getText().toString();
         propertyStreetAddress = street.getText().toString();
         propertyCity = city.getText().toString();
         propertyState = state.getText().toString();
         propertyZip = zipcode.getText().toString();
        propertyType = spinner.getSelectedItem().toString();

        if(!price.getText().toString().isEmpty()){
            Log.d("Price", price.getText().toString());
            propertyPrice = Double.valueOf(price.getText().toString());
        }else propertyPrice = 0;

        if(!bath.getText().toString().isEmpty()){
            Log.d("Bath",bath.getText().toString());
            propertyNumberOfBaths =  Integer.valueOf(bath.getText().toString());
        }else
            propertyNumberOfBaths = 0;

        if(!rooms.getText().toString().isEmpty()){
            Log.d("rooms",rooms.getText().toString());
            propertyNumberOfRooms =  Integer.valueOf(rooms.getText().toString());
        }else
            propertyNumberOfRooms = 0;


        if(!area.getText().toString().isEmpty()){
            Log.d("Area",area.getText().toString());
            propertySquareFootage = Double.valueOf(area.getText().toString());
        }else propertySquareFootage = 0;


        if(propertyPrice!=0 && propertyNumberOfBaths!=0 && propertySquareFootage!=0 &&
          !propertyDescription.isEmpty() &&  !propertyTitle.isEmpty() && !propertyOwnerEmail.isEmpty() &&
                !propertyOwnerPhone.isEmpty() && !propertyStreetAddress.isEmpty() && !propertyCity.isEmpty()
                && !propertyState.isEmpty() && !propertyZip.isEmpty() && propertyNumberOfRooms!=0 ){

            PostPropretyTask postPropretyTask = new PostPropretyTask();
            postPropretyTask.execute();
        }
        else{
            Toast.makeText(LandlordPostActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        propertyType = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    public class PostPropretyTask extends AsyncTask<String,Void,Void> {

        RestAdapter restAdapter;
        @Override
        protected void onPreExecute(){


            final OkHttpClient okHttpClient = new OkHttpClient();

            String url = "http://ec2-54-153-29-131.us-west-1.compute.amazonaws.com:8080";

            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(url)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(new OkClient(okHttpClient))
                    .build();
        }

        @Override
        protected Void doInBackground(String... params) {

            final RestApiClass restApiClass = restAdapter.create(RestApiClass.class);

        Property property = new Property(10000,propertyType,propertyPrice,propertyDescription,propertyTitle,propertyOwnerEmail,
                propertyOwnerPhone,propertyStreetAddress,propertyCity,propertyState,propertyZip,propertyNumberOfBaths,
                propertyNumberOfRooms,propertySquareFootage,"Available");

//        Property property = new Property(params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7]
//                ,params[8],params[9],params[10],params[11],params[12],params[13],params[14]);

            Log.d("Property",property.toString());

            JSONObject response = restApiClass.postProperty(property);

            try {
                Log.d("Response", response.get("message").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }
    }


}

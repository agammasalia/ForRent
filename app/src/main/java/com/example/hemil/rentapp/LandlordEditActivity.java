package com.example.hemil.rentapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hemil.rentapp.API.RestApiClass;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;

import POJO.Property;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by hemil on 5/9/2016.
 */
public class LandlordEditActivity extends MainActivity {

    Property property;
    EditText title,price,rooms,bath,area,street,city,state,zipcode,email,phone,desc;

    String propertyType,propertyDescription,propertyTitle, propertyOwnerEmail,propertyOwnerPhone,
            propertyStreetAddress,propertyCity,propertyState,propertyZip;
    int propertyNumberOfBaths =0, propertyNumberOfRooms = 0;
    double propertyPrice, propertySquareFootage;
    Spinner spinner;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.listView_home).setVisibility(View.INVISIBLE);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.landlord_edit, null, false);
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

        String str = getIntent().getStringExtra("Property");
        property = new Gson().fromJson(str, Property.class);

        title.setText(property.getPropertyTitle());
        desc.setText(property.getPropertyDescription());
        price.setText(""+property.getPropertyPrice());
        rooms.setText(""+property.getPropertyNumberOfRooms());
        bath.setText(""+property.getPropertyNumberOfBaths());
        area.setText(""+property.getPropertySquareFootage());
        street.setText(property.getPropertyStreetAddress());
        state.setText(property.getPropertyState());
        city.setText(property.getPropertyCity());
        zipcode.setText(property.getPropertyZip());
        email.setText(property.getPropertyOwnerEmail());
        phone.setText(property.getPropertyOwnerPhone());
    }

    public void updatePropertyMethod(View view){

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

            UpdatePropertyAsync updatePropertyAsync = new UpdatePropertyAsync();
            updatePropertyAsync.execute();
        }
        else{
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }

    }

    public void deletePropertyMethod(View view){
        new DeletePropertyAsync().execute();
    }

    private class DeletePropertyAsync extends AsyncTask<Void,Void,Void>{
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
        protected Void doInBackground(Void... params) {
            final RestApiClass restApiClass = restAdapter.create(RestApiClass.class);

            JSONObject jsonObject = restApiClass.deleteProperty(property.getPropertyId());
            Log.d("delete Response",jsonObject.toString());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//stuff that updates ui
                    Toast.makeText(getApplicationContext(), "Property Deleted!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),LandlordShowListActivity.class);
                    startActivity(intent);
                }
            });

            return null;
        }
    }

    private class UpdatePropertyAsync extends AsyncTask<Void,Void,Void>{

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
        protected Void doInBackground(Void... params) {
            final RestApiClass restApiClass = restAdapter.create(RestApiClass.class);

            Property property_new = new Property(property.getPropertyOwnerId(),propertyType,propertyPrice,propertyDescription,propertyTitle,propertyOwnerEmail,
                    propertyOwnerPhone,propertyStreetAddress,propertyCity,propertyState,propertyZip,propertyNumberOfBaths,
                    propertyNumberOfRooms,propertySquareFootage,"Available");

            property_new.setPropertyId(property.getPropertyId());
            property_new.setPropertyLocationId(property.getPropertyLocationId());
            JSONObject response = restApiClass.updateProperty(property_new);
            Log.d("Updated Property",property_new.toString());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//stuff that updates ui
                    Intent intent = new Intent(getApplicationContext(),LandlordShowListActivity.class);
                    startActivity(intent);
                }
            });
            return null;
        }



    }

}


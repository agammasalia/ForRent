package com.example.hemil.rentapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hemil on 5/8/2016.
 */
public class LandlordPostActivity extends MainActivity implements AdapterView.OnItemSelectedListener {

    String propertyType;
    EditText title,price,rooms,bath,area,street,city,state,zipcode,email,phone,desc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.landlord_post, null, false);
        mDrawer.addView(contentView, 0);

        //Do the rest as you want for each activity
        Spinner spinner = (Spinner) findViewById(R.id.spinner_type);
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

         String propertyDescription = desc.getText().toString();
         String propertyTitle = title.getText().toString();
         String propertyOwnerEmail = email.getText().toString();
         String propertyOwnerPhone = phone.getText().toString();
         String propertyStreetAddress = street.getText().toString();
         String propertyCity = city.getText().toString();
         String propertyState = state.getText().toString();
         String propertyZip = zipcode.getText().toString();

        double propertyPrice;
        if(!price.getText().toString().isEmpty()){
            Log.d("Price", price.getText().toString());
            propertyPrice = Double.valueOf(price.getText().toString());
        }else propertyPrice = 0;

        int propertyNumberOfBaths;
        if(!bath.getText().toString().isEmpty()){
            Log.d("Bath",bath.getText().toString());
            propertyNumberOfBaths =  Integer.valueOf(bath.getText().toString());
        }else
            propertyNumberOfBaths = 0;

        int propertyNumberOfRooms = 0;
        if(!rooms.getText().toString().isEmpty()){
            Log.d("rooms",rooms.getText().toString());
            propertyNumberOfRooms =  Integer.valueOf(rooms.getText().toString());
        }else
            propertyNumberOfRooms = 0;

        double propertySquareFootage;
        if(!area.getText().toString().isEmpty()){
            Log.d("Area",area.getText().toString());
            propertySquareFootage = Double.valueOf(area.getText().toString());
        }else propertySquareFootage = 0;


        if(propertyPrice!=0 && propertyNumberOfBaths!=0 && propertySquareFootage!=0 &&
          !propertyDescription.isEmpty() &&  !propertyTitle.isEmpty() && !propertyOwnerEmail.isEmpty() &&
                !propertyOwnerPhone.isEmpty() && !propertyStreetAddress.isEmpty() && !propertyCity.isEmpty()
                && !propertyState.isEmpty() && !propertyZip.isEmpty() && propertyNumberOfRooms!=0 ){

            JSONObject json = new JSONObject();
            json.put("propertyOwnerId","");
            json.put("propertyLocationId","");
            json.put("propertyType",propertyType);
            json.put("propertyPrice",propertyPrice);
            json.put("propertyDescription",propertyDescription);
            json.put("propertyTitle",propertyTitle);
            json.put("propertyOwnerEmail",propertyOwnerEmail);
            json.put("propertyOwnerPhone",propertyOwnerPhone);
            json.put("propertyStreetAddress",propertyStreetAddress);
            json.put("propertyCity",propertyCity);
            json.put("propertyState",propertyState);
            json.put("propertyZip",propertyZip);
            json.put("propertyNumberOfBaths",propertyNumberOfBaths);
            json.put("propertyNumberOfRooms",propertyNumberOfRooms);

            Log.d("JSON",json.toString());
            Toast.makeText(LandlordPostActivity.this, "Make it a post", Toast.LENGTH_SHORT).show();
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
}

package com.example.hemil.rentapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import POJO.Property;

/**
 * Created by hemil on 5/11/2016.
 */
public class PropertyDetailsActivity extends MainActivity {

    TextView title, desc, type, room, bath, area, price, street, city, state, zip, email, phone;
    Property property;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.listView_home).setVisibility(View.INVISIBLE);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.property_detail, null,false);
        mDrawer.addView(contentView, 0);

        title = (TextView) findViewById(R.id.detail_title);
        desc = (TextView) findViewById(R.id.detail_description);
        type = (TextView) findViewById(R.id.detail_type);
        room = (TextView) findViewById(R.id.detail_rooms);
        bath = (TextView) findViewById(R.id.detail_bath);
        area = (TextView) findViewById(R.id.detail_area);
        price = (TextView) findViewById(R.id.detail_price);
        street = (TextView) findViewById(R.id.detail_street);
        city = (TextView) findViewById(R.id.detail_city);
        state = (TextView) findViewById(R.id.detail_state);
        zip = (TextView) findViewById(R.id.detail_zipcode);
        email = (TextView) findViewById(R.id.detail_email);
        phone = (TextView) findViewById(R.id.detail_phone);

        String str = getIntent().getStringExtra("Property");
        property = new Gson().fromJson(str,Property.class);

        Log.d("str", str);
        Log.d("propety on intent", property.toString());



        showDetails();

    }

    public void showDetails(){
        title.setText("Title : " + property.getPropertyTitle());
        desc.setText("Description: "+property.getPropertyDescription());
        type.setText("Type: "+property.getPropertyType());
        room.setText("Rooms: "+property.getPropertyNumberOfRooms());
        bath.setText("Bathrooms: "+property.getPropertyNumberOfBaths());
        area.setText("Area: "+property.getPropertySquareFootage());
        price.setText("Price: "+property.getPropertyPrice());
        street.setText("Street: "+property.getPropertyPrice());
        city.setText("City: "+property.getPropertyCity());
        state.setText("State: "+property.getPropertyState());
        zip.setText("Zipcode: "+property.getPropertyZip());
        email.setText("Email: "+property.getPropertyOwnerEmail());
        phone.setText("Phone: "+property.getPropertyOwnerPhone());
    }



}

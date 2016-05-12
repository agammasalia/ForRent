package com.example.hemil.rentapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by hemil on 5/9/2016.
 */
public class LandlordEditActivity extends MainActivity {


    String propertyType;
    EditText title,price,rooms,bath,area,street,city,state,zipcode,email,phone,desc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.listView_home).setVisibility(View.INVISIBLE);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.landlord_edit, null, false);
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

    public void updatePropertyMethod(View view){

    }

    public void deletePropertyMethod(View view){

    }
}

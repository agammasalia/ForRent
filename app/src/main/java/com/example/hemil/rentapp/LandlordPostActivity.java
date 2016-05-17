package com.example.hemil.rentapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.hemil.rentapp.API.RestApiClass;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import retrofit.*;
import POJO.Property;
import retrofit.client.OkClient;

/**
 */
public class LandlordPostActivity extends MainActivity implements AdapterView.OnItemSelectedListener {

    String propertyType,propertyDescription,propertyTitle, propertyOwnerEmail,propertyOwnerPhone,
    propertyStreetAddress,propertyCity,propertyState,propertyZip,imgDecodableString;
    int propertyNumberOfBaths =0, propertyNumberOfRooms = 0;
    double propertyPrice, propertySquareFootage;
    EditText title,price,rooms,bath,area,street,city,state,zipcode,email,phone,desc;
    Spinner spinner;
    ImageView imageView1, imageView2;
    BufferedInputStream fileInputStream1, fileInputStream2;
    SharedPreferences sharedPreferences;
    long userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.listView_home).setVisibility(View.INVISIBLE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = sharedPreferences.getLong("userId", 0);

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
        imageView1 = (ImageView) findViewById(R.id.imageView1);
    }

    public void addImageOneMethod(View view){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Data",data.toString());
        try {
            // When an Image is picked
            if (requestCode == 1 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                Log.d("Image ", imgDecodableString);

                File file = new File(imgDecodableString);
                if(fileInputStream1 == null){
                    FileInputStream fileInputStream = new FileInputStream(file);
                    fileInputStream1 = new BufferedInputStream(fileInputStream);
                    Log.d("F1",fileInputStream1.toString());
                    imageView1.setImageURI(Uri.parse(imgDecodableString));
                }
                else
                {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    fileInputStream2 = new BufferedInputStream(fileInputStream);
                    Log.d("F2",fileInputStream2.toString());
                }

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

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

            Map config = new HashMap();
            config.put("cloud_name", "arpit9691");
            config.put("api_key", "962197718417456");
            config.put("api_secret", "zPcSKVmofDl0doof8bXjXI8F9Mo");
            Cloudinary cloudinary = new Cloudinary(config);

            try {
                Map uploadResult =  cloudinary.uploader().upload(imgDecodableString, ObjectUtils.emptyMap());
                Log.d("URL", uploadResult.get("url").toString());

                String str = null;
                if(uploadResult!=null){
                    str = uploadResult.get("url").toString();
                }
        Property property = new Property(userId,propertyType,propertyPrice,propertyDescription,propertyTitle,propertyOwnerEmail,
                propertyOwnerPhone,propertyStreetAddress,propertyCity,propertyState,propertyZip,propertyNumberOfBaths,
                propertyNumberOfRooms,propertySquareFootage,"Available",str);

//        Property property = new Property(params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7]
//                ,params[8],params[9],params[10],params[11],params[12],params[13],params[14]);

            Log.d("Property",property.toString());

            restApiClass.postProperty(property);
            } catch (IOException e) {
                e.printStackTrace();
            }


//            try {
//                    //        Log.d("Response", response.get("message").toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//stuff that updates ui
                    Intent intent = new Intent(getApplicationContext(),LandlordShowListActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            return null;
        }
    }


}

package com.example.hemil.rentapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hemil.rentapp.API.RestApiClass;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;

import POJO.Favourites;
import POJO.Property;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 */
public class PropertyDetailsActivity extends MainActivity {

    TextView title, desc, type, room, bath, area, price, street, city, state, zip, email, phone;
    Property property;
    Button button;
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
        button = (Button) findViewById(R.id.favorite);

        if(getIntent().hasExtra("Favorite")){
            if(getIntent().getStringExtra("Favorite").equals("true")){
                button.setText("Delete favorite");
            }
        }

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

    public void addBookmarkMethod(View view){

        Log.d("Favorite", getIntent().getStringExtra("Favorite"));

        if(getIntent().getStringExtra("Favorite").equals("true")){
            new DeleteFavoriteAsync().execute();
        }
        else{
            new AddBookMarkAsync().execute();
        }

    }

    private class AddBookMarkAsync extends AsyncTask<Void,Void,Void>{

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

            Favourites favourites = new Favourites(property.getPropertyOwnerId(),property.getPropertyId());
            JSONObject jsonObject = restApiClass.addBookmark(favourites);

            Log.d("Favorites",jsonObject.toString());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
            });
            return null;
        }
    }

    private class DeleteFavoriteAsync extends AsyncTask<Void,Void,Void>{

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
            Favourites favourites = new Favourites(property.getPropertyOwnerId(),property.getPropertyId());

            restApiClass.deleteFavorite(property.getPropertyOwnerId(), property.getPropertyId());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), RenterFavoriteActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            return null;
        }
    }
}

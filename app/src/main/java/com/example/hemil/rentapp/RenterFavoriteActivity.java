package com.example.hemil.rentapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hemil.rentapp.API.RestApiClass;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import POJO.Property;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by hemil on 5/10/2016.
 */
public class RenterFavoriteActivity extends MainActivity {

    List<Property> list;
    ListView listview_favorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.listView_home).setVisibility(View.INVISIBLE);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.renter_favorite, null, false);
        mDrawer.addView(contentView, 0);

        listview_favorite = (ListView) findViewById(R.id.listView_favorite);

        new GetFavoritesListAsync().execute();
    }

    protected void setListIn(List<Property> list){
        this.list = list;
    }

    protected void setFavoritesListView(){
        if(list.size()>0) {
            Log.d("Inside", "inside");
            PopulateListViewAdapter populateListViewAdapter = new PopulateListViewAdapter(this, list);
            listview_favorite.setAdapter(populateListViewAdapter);

            // ListView Item Click Listener
            listview_favorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent intent = new Intent(getApplicationContext(), PropertyDetailsActivity.class);
                    Gson gson = new Gson();
                    String str = gson.toJson(list.get(position)).toString();
                    intent.putExtra("Property", str);
                    startActivity(intent);
                }

            });
        }
        else{
            Log.d("No ", "Result");
            findViewById(R.id.listView_favorite).setVisibility(View.GONE);
            TextView textView = (TextView) findViewById(R.id.nofavorites);
            textView.setVisibility(View.VISIBLE);
        }
    }

    private class GetFavoritesListAsync extends AsyncTask<Void,Void,Void>{

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

            List<Property> list = restApiClass.getAllFavorites(1);

            setListIn(list);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

//stuff that updates ui
                    setFavoritesListView();
                }
            });


            return null;
        }
    }
}

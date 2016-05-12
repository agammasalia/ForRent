package com.example.hemil.rentapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hemil.rentapp.API.RestApiClass;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import POJO.Property;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by hemil on 5/9/2016.
 */
public class LandlordShowListActivity extends MainActivity {

    List<Property> response;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Inside","LandlordShow");
        super.onCreate(savedInstanceState);
        findViewById(R.id.listView_home).setVisibility(View.INVISIBLE);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.landlord_list_show, null, false);
        mDrawer.addView(contentView, 0);


    }

    private void recordList(List<Property> list){
        response = list;
    }

    private void showListView(){
        listView = (ListView) findViewById(R.id.listView);
        PopulateListViewAdapter populateListViewAdapter = new PopulateListViewAdapter(this,response);
        listView.setAdapter(populateListViewAdapter);

        new ShowLandlordProperties().execute();
        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(getApplicationContext(), PropertyDetailsActivity.class);
                intent.putExtra("Property",response.get(position).toString());
                startActivity(intent);
            }

        });
    }

    public class ShowLandlordProperties extends AsyncTask<Void,Void,Void>{

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
            response = restApiClass.getLandlordProperty(1);
            Log.d("Response_edit", response.toString());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//stuff that updates ui
                 recordList(response);
                    showListView();
                }
            });

            return null;
        }
    }


}

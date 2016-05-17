package com.example.hemil.rentapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hemil.rentapp.API.RestApiClass;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import org.w3c.dom.Text;

import java.util.List;

import POJO.Property;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 */
public class LandlordShowListActivity extends MainActivity {

    List<Property> response;
    ListView listView;
    SharedPreferences sharedPreferences;
    long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Inside","LandlordShow");
        super.onCreate(savedInstanceState);
        findViewById(R.id.listView_home).setVisibility(View.INVISIBLE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.landlord_list_show, null, false);
        mDrawer.addView(contentView, 0);

        new ShowLandlordProperties().execute();
    }

    private void recordList(List<Property> list){
        response = list;
    }

    private void showListView(){
        listView = (ListView) findViewById(R.id.listView_landlord_show);

        if(response.size()>0) {
            PopulateListViewAdapter populateListViewAdapter = new PopulateListViewAdapter(this, response);
            listView.setAdapter(populateListViewAdapter);

            //  new ShowLandlordProperties().execute();
            // ListView Item Click Listener
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent intent = new Intent(getApplicationContext(), LandlordEditActivity.class);
                    Gson gson = new Gson();
                    String str = gson.toJson(response.get(position)).toString();
                    intent.putExtra("Property", str);
                    startActivity(intent);
                }

            });
        }
        else{
            listView.setVisibility(View.INVISIBLE);
            TextView textView = (TextView) findViewById(R.id.noprops);
            textView.setVisibility(View.VISIBLE);
        }
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
            userId = sharedPreferences.getLong("userId", 0);
            response = restApiClass.getLandlordProperty(userId);
            Log.d("Response_edit", response.toString());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//stuff that updates ui
                    if (response.size() > 0)
                        recordList(response);
                        showListView();

                }
            });

            return null;
        }
    }


}

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hemil.rentapp.API.RestApiClass;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import POJO.Property;
import POJO.SavedSearch;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by hemil on 5/10/2016.
 */
public class RenterSearchActivity extends MainActivity {

    EditText keyword, city, zipcode, lprice, hprice;
    Spinner spinner;
    ListView search_listview;
    List<Property> listProperty;
    String str_key, str_city, str_zip, str_type;
    double str_lp = 0, str_hp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.listView_home).setVisibility(View.INVISIBLE);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.renter_search, null, false);
        mDrawer.addView(contentView, 0);

        keyword = (EditText) findViewById(R.id.edit_search_keyword);
        city = (EditText) findViewById(R.id.edit_search_city);
        zipcode = (EditText) findViewById(R.id.edit_search_zipcode);
        lprice = (EditText) findViewById(R.id.edit_search_low_price);
        hprice = (EditText) findViewById(R.id.edit_search_high_price);
        spinner = (Spinner) findViewById(R.id.search_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_resources, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        search_listview = (ListView) findViewById(R.id.search_listView);
    }

        private void storeList(List<Property> listProperty){
            this.listProperty = listProperty;
        }

    public void searchPropertyMethod(View view){

            new SearchPropertyAsync().execute();
    }

    public void saveSearchPropertyMethod(View view){

    }

    public void showSearchResultList(){
        if(listProperty.size()>0) {
            Log.d("Inside","inside");
            findViewById(R.id.search_form_layout).setVisibility(View.GONE);
            search_listview.setVisibility(View.VISIBLE);
            PopulateListViewAdapter populateListViewAdapter = new PopulateListViewAdapter(this, listProperty);
            search_listview.setAdapter(populateListViewAdapter);

            // ListView Item Click Listener
            search_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent intent = new Intent(getApplicationContext(), PropertyDetailsActivity.class);
                    Gson gson = new Gson();
                    String str = gson.toJson(listProperty.get(position)).toString();
                    intent.putExtra("Property", str);
                    startActivity(intent);
                }

            });
        }
        else{
            Log.d("No ", "Result");
            findViewById(R.id.search_form_layout).setVisibility(View.GONE);
            TextView textView = (TextView) findViewById(R.id.nosearchfound);
            textView.setVisibility(View.VISIBLE);
        }
    }

    private class SearchPropertyAsync extends AsyncTask<Void,Void,Void>{

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
            str_key = keyword.getText().toString();
            str_type = spinner.getSelectedItem().toString();
            str_city = city.getText().toString();
            str_zip = zipcode.getText().toString();

            if( !lprice.getText().toString().isEmpty())
                str_lp = Double.parseDouble(lprice.getText().toString());

            if(!hprice.getText().toString().isEmpty())
                str_hp = Double.parseDouble(hprice.getText().toString());

            SavedSearch savedSearch = new SavedSearch(str_hp,str_lp,str_key,str_city,str_zip,str_type);

            Log.d("Sent Search Object",savedSearch.toString());

            List<Property> response = restApiClass.getSearchResults(savedSearch);

            storeList(response);

            Log.d("Search Results",response.toString());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showSearchResultList();
                }
            });

            return null;
        }
    }
}


package com.example.hemil.rentapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;

import com.squareup.okhttp.OkHttpClient;

import com.example.hemil.rentapp.API.RestApiClass;

import java.io.IOException;
import java.util.List;

import POJO.Property;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout mDrawer;
    SharedPreferences sharedPref ;
    Menu menuOption;
    ListView listview_home;
    List<Property> response;
    private CallbackManager callbackManager;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;
    private int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    //= Context.getSharedPreferences("Login", Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        listview_home = (ListView) findViewById(R.id.listView_home);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        sharedPref = getSharedPreferences("Login", Context.MODE_PRIVATE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ShowAllPropretyTask showAllPropretyTask = new ShowAllPropretyTask();
        showAllPropretyTask.execute();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
//                    mInformationTextView.setText(getString(R.string.gcm_send_message));
                } else {
//                    mInformationTextView.setText(getString(R.string.token_error_message));
                }
            }
        };

        // Registering BroadcastReceiver
        registerReceiver();

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    private void populateList(){


        PopulateListViewAdapter adapter = new PopulateListViewAdapter(this,response);
        listview_home.setAdapter(adapter);
        listview_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                Intent intent = new Intent(getApplicationContext(), PropertyDetailsActivity.class);
                Gson gson = new Gson();
                String str = gson.toJson(response.get(position)).toString();
                Log.d("Property",str);
                intent.putExtra("Property",str);
                startActivity(intent);
            }

        });
    }

    public void setList(List<Property> list){
        response = list;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        menuOption = menu;
        boolean flag = sharedPref.getBoolean("isLogin", false);

        Log.d("OnCreateOption", "" + flag);
        if(flag)
        {
            menu.findItem(R.id.sign_out).setVisible(false);
            menu.findItem(R.id.sign_in).setVisible(true);
        }
        else{
            menu.findItem(R.id.sign_in).setVisible(true);
            menu.findItem(R.id.sign_out).setVisible(false);
        }
    //   boolean flag = getResources().getBoolean((R.string.login_test));
    //    Log.d("Flag",""+flag);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "Settings Selected!", Toast.LENGTH_SHORT).show();
               return true;
        }

        if( id == R.id.sign_in){
          //  SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("isLogin", true);
            editor.commit();

        Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);

     //       updateMenuOptions();
     //       invalidateOptionsMenu();
     //       Toast.makeText(MainActivity.this, "Sign-In Screen!", Toast.LENGTH_SHORT).show();
            return true;
        }

        if(id == R.id.sign_out){
            //SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("isLogin", false);
            editor.commit();
            updateMenuOptions();
       //     invalidateOptionsMenu();
          //  Toast.makeText(MainActivity.this, "Sign-Out!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Override this method to do what you want when the menu is recreated

    public void updateMenuOptions() {
//        menu.findItem(R.id.pencil).setVisible(false);

        MenuItem signin = menuOption.getItem(R.id.sign_in);
        MenuItem signout = menuOption.getItem(R.id.sign_out);

        boolean flag = sharedPref.getBoolean("isLogin",false);
        Log.d("OnResult", "" + flag);

        if(flag)
        {
            signin.setVisible(true);
            signout.setVisible(false);
        }
        else{
            signin.setVisible(true);
            signout.setVisible(false);
        }

//        return super.onPrepareOptionsMenu(menu);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
           // Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_serach) {
            Intent intent = new Intent(this,RenterSearchActivity.class);
            startActivity(intent);
        //    Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_favorites) {
            Intent intent = new Intent(this,RenterFavoriteActivity.class);
            startActivity(intent);
        //    Toast.makeText(MainActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_notifications) {
            Intent intent = new Intent(this,RenterNotificationActivity.class);
            startActivity(intent);
         //   Toast.makeText(MainActivity.this, "Notification", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_post) {
            Intent intent = new Intent(this,LandlordPostActivity.class);
            startActivity(intent);
        //    Toast.makeText(MainActivity.this, "Post Property", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_edit) {
            Log.d("Initiated","EditSHow");
            Intent intent = new Intent(this, LandlordShowListActivity.class);
            startActivity(intent);
      //      Toast.makeText(MainActivity.this, "Edit Property", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public class ShowAllPropretyTask extends AsyncTask<String, Void, Void> {

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
            response = restApiClass.getAllListings();
            Log.d("Response", response.toString());


            setList(response);
            Log.d("Populate", "list");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

//stuff that updates ui
                    populateList();
                }
            });

            return null;
        }

        protected void onPostExecute(){
            Log.d("Inside","PostExceute");
            populateList();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("No Support", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

}

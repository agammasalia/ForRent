package com.example.hemil.rentapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout mDrawer;
    SharedPreferences sharedPref ;
    Menu menuOption;
    //= Context.getSharedPreferences("Login", Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        sharedPref = getSharedPreferences("Login", Context.MODE_PRIVATE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        Log.d("OnprepareOptionMenu Result",""+flag);

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
            Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_serach) {
            Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_favorites) {
            Toast.makeText(MainActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_notifications) {
            Toast.makeText(MainActivity.this, "Notification", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_post) {
            Intent intent = new Intent(this,LandlordPostActivity.class);
            startActivity(intent);

            Toast.makeText(MainActivity.this, "Post Property", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_edit) {
            Toast.makeText(MainActivity.this, "Edit Property", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

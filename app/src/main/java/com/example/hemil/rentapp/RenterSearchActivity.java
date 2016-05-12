package com.example.hemil.rentapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by hemil on 5/10/2016.
 */
public class RenterSearchActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.listView_home).setVisibility(View.INVISIBLE);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.renter_search, null, false);
        mDrawer.addView(contentView, 0);
    }


    public void searchPropertyMethod(View view){

    }

    public void saveSearchPropertyMethod(View view){

    }
}


package com.example.hemil.rentapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import POJO.Property;

/**
 * Created by hemil on 5/11/2016.
 */
public class PopulateListViewAdapter extends BaseAdapter {

    Context context;
    List<Property> propertyList;
    LayoutInflater layoutInflater;
    public PopulateListViewAdapter(Context context, List<Property> propertyList){
        this.context = context;
        this.propertyList = propertyList;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        Log.d("Count",""+propertyList.size());
        return propertyList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView title = null, price =null, location = null, bba = null;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.property_list_item,null);

            title = (TextView) convertView.findViewById(R.id.property_item_title);
            price = (TextView) convertView.findViewById(R.id.property_item_price);
            location = (TextView) convertView.findViewById(R.id.property_item_location);
            bba = (TextView) convertView.findViewById(R.id.property_item_bath_bed_area);
        }

        title.setText(propertyList.get(position).getPropertyTitle());
        price.setText("Price:"+ propertyList.get(position).getPropertyPrice()+" USD");
        location.setText(propertyList.get(position).getPropertyCity()+", "+propertyList.get(position).getPropertyState()+
        ", "+propertyList.get(position).getPropertyZip());
        bba.setText("Rooms: "+propertyList.get(position).getPropertyNumberOfRooms()+"  Bathrooms: "+
        propertyList.get(position).getPropertyNumberOfBaths()+"  Area: "+propertyList.get(position).getPropertySquareFootage());

        return convertView;
    }



}

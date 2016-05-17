package com.example.hemil.rentapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import POJO.Property;

/**
 */
public class PopulateListViewAdapter extends BaseAdapter {

    Context context;
    List<Property> propertyList;
    LayoutInflater layoutInflater;
//   TextView title, price, location, bba;
//    ImageView imageView;
static class Holder{
    TextView title, price, location, bba;
    ImageView imageView;
}

    Holder holder;



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

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.property_list_item,null);
            holder = new Holder();

            holder.title = (TextView) convertView.findViewById(R.id.property_item_title);
            holder.price = (TextView) convertView.findViewById(R.id.property_item_price);
            holder.location = (TextView) convertView.findViewById(R.id.property_item_location);
            holder.bba = (TextView) convertView.findViewById(R.id.property_item_bath_bed_area);

            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }


        holder.title.setText(propertyList.get(position).getPropertyTitle());
        holder.price.setText("Price:"+ propertyList.get(position).getPropertyPrice()+" USD/month");
        holder.location.setText(propertyList.get(position).getPropertyCity()+", "+propertyList.get(position).getPropertyState()+
        ", "+propertyList.get(position).getPropertyZip());
        holder.bba.setText("Rooms: "+propertyList.get(position).getPropertyNumberOfRooms()+"  Bathrooms: "+
        propertyList.get(position).getPropertyNumberOfBaths()+"  Area: "+propertyList.get(position).getPropertySquareFootage());

        holder.imageView = (ImageView) convertView.findViewById(R.id.property_item_image);
        if(propertyList.get(position).getPropertyImage()!=null){

            Picasso.with(context).load(propertyList.get(position).getPropertyImage()).into( holder.imageView);
            holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.imageView.setAlpha((float) 0.85);
        }
        else
            holder.imageView.setImageResource(R.drawable.nopreview);
        return convertView;
    }

}

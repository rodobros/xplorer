package com.xplorer;

import android.content.Context;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.GridView;

import java.util.ArrayList;

/**
* Created by Rodobros on 2016-10-20.
*/
public class PlaceOfInterestImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<PlaceOfInterest> pictures;

    public PlaceOfInterestImageAdapter(Context c) {
        mContext = c;
        pictures = new ArrayList<PlaceOfInterest>();
    }

    public int getCount() {
        return pictures.size();
    }

    public Object getItem(int position) {
        return pictures.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(pictures.get(position).getDrawableID());
        return imageView;
    }

    public void addPlaceOfInterest(String name, Double longitude, Double latitude, int drawableID){
        PlaceOfInterest tmp = new PlaceOfInterest(name, longitude, latitude, drawableID);

        if(!pictures.contains(tmp)){
            pictures.add(tmp);
        }
    }

    public void addPlaceOfInterest(PlaceOfInterest value) {
        if(!pictures.contains(value)){
            pictures.add(value);
        }
    }

    public void addPlacesOfInterest(ArrayList<PlaceOfInterest> value) {
        for(int i = 0 ; i < value.size() ; ++i) {
            addPlaceOfInterest(value.get(i));
        }
    }

}

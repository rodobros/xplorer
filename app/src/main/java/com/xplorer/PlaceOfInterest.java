package com.xplorer;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by Rodobros on 2016-10-20.
 */
public class PlaceOfInterest {
    private Double longitude_;
    private Double latitude_;
    private String name_;
    private int drawableID_;

    PlaceOfInterest(String name, Double latitude, Double longitude, int drawableID){
        name_ = name;
        longitude_ = longitude;
        latitude_ = latitude;
        drawableID_ = drawableID;
    }

    Double getLongitude(){
        return longitude_;
    }

    Double getLatitude(){
        return latitude_;
    }

    void setLongitude(Double value){
        longitude_ = value;
    }

    void getLatitude(Double value){
        latitude_ = value;
    }

    String getName(){
        return name_;
    }

    int getDrawableID() {
        return drawableID_;
    }
}

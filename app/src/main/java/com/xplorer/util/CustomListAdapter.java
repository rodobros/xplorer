package com.xplorer.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.xplorer.R;
import com.xplorer.business.PlaceOfInterest;

import java.util.ArrayList;

//package androidinterview.com.customlistviewimagetext;import com.xplorer.R;

/**
 * Created by Johanna on 2016-11-16.
 */

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;
    private final String[] itemcoords;

    public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid, String[] itemcoords) {
        super(context, R.layout.mylist, itemname);

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.itemcoords=itemcoords;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        extratxt.setText("Coordinates: "+itemcoords[position]);
        return rowView;

    };
}
package com.xplorer;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class AllPlacesOfInterestActivity extends AppCompatActivity {

    PlaceOfInterestImageAdapter myPlaceOfInterestImageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_places_of_interest);
        ActionBar a = getActionBar();
        if(a != null) {
            a.setDisplayHomeAsUpEnabled(true);

        }

        myPlaceOfInterestImageAdapter = new PlaceOfInterestImageAdapter(this);
        myPlaceOfInterestImageAdapter.addPlacesOfInterest(PlacesOfInterestManager.getInstance().getListOfPlacesOfInterest());

        GridView gridview = (GridView) findViewById(R.id.imageGridView);
        gridview.setAdapter(myPlaceOfInterestImageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                onClickItem(position);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickItem(int position){
        PlaceOfInterest choosenLocation = ((PlaceOfInterest) myPlaceOfInterestImageAdapter.getItem(position));
        PlacesOfInterestManager.getInstance().setCurrentGoal(choosenLocation);
        Intent i = new Intent(this, PlaceOfInterestConfirmGoalActivity.class);
        startActivity(i);
    }
}

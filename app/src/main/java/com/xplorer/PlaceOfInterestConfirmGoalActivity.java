package com.xplorer;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class PlaceOfInterestConfirmGoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_of_interest_confirm_goal);

        Intent i = getIntent();
        ((ImageView)findViewById(R.id.imageChoiceConfirm)).setImageResource(PlacesOfInterestManager.getInstance().getCurrentGoal().getDrawableID());
        ActionBar a = getActionBar();
        if(a != null) {
            a.setDisplayHomeAsUpEnabled(true);

        }
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

    public void onBeginGameClick(View v) {
        Intent i=new Intent(this, GoalSeekingMapActivity.class);
        startActivity(i);
    }
}

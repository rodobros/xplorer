package com.xplorer;

import android.app.ActionBar;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xplorer.business.TypeOfInformation;
import com.xplorer.manager.PlacesOfInterestManager;
import com.xplorer.manager.SettingsManager;

public class SeeGoalWhilePlayingActivity extends AppCompatActivity {
    Double currentLong_;
    Double currentLat_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_goal_while_playing);

        ActionBar a = getActionBar();
        if(a != null) {
            a.setDisplayHomeAsUpEnabled(true);

        }

        TextView textViewTitlePlace = (TextView) findViewById(R.id.goal_while_playing_title_place);
        if (SettingsManager.getInstance().getTypeOfInformation() == TypeOfInformation.TitleWithImage) {
            textViewTitlePlace.setText(PlacesOfInterestManager.getInstance().getCurrentGoal().getName());
        }


        Intent i = getIntent();

        currentLong_ = i.getDoubleExtra("currentLongitude", 0);
        currentLat_ = i.getDoubleExtra("currentLatitude", 0);

        ((ImageView)findViewById(R.id.imageGoal)).setImageResource(PlacesOfInterestManager.getInstance().getCurrentGoal().getDrawableID());
    }


    public void onFoundItClick(View v){
        float[] results = new float[1];
        Log.d("latlongs before", "start:" + currentLat_+ ":" + currentLong_ + " end:" + PlacesOfInterestManager.getInstance().getCurrentGoal().getLatitude() + ":" + PlacesOfInterestManager.getInstance().getCurrentGoal().getLongitude());
        Location.distanceBetween(currentLat_, currentLong_,PlacesOfInterestManager.getInstance().getCurrentGoal().getLatitude(),PlacesOfInterestManager.getInstance().getCurrentGoal().getLongitude(), results);
        Log.d("distance calculated", "" + results[0]);

        boolean foundIt = results[0] < SettingsManager.getInstance().getValidateGoalMinDistance();
        if(foundIt){
            Toast.makeText(SeeGoalWhilePlayingActivity.this, "You found it! good job!", Toast.LENGTH_SHORT).show();
            PlacesOfInterestManager.getInstance().setPlaceOfInterestFound(PlacesOfInterestManager.getInstance().getCurrentGoal());
            Intent i=new Intent(this, MainMenuActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(SeeGoalWhilePlayingActivity.this, "Try again! You're bad !", Toast.LENGTH_SHORT).show();
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
}

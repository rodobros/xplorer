package com.xplorer;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {
    Button startButton;
    Button settingsButton;
    Button statisticsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        settingsButton = (Button)findViewById(R.id.buttonSettings);

        //to be sure that the timer is canceled
        if (MapGoalSeekingActivity.timer != null ) {
            MapGoalSeekingActivity.timer.cancel();

        }
    }

    public void onSettingsClick(View v) {
        Intent i=new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    public void onStatisticsClick(View v) {
        Intent i=new Intent(this, StatisticsActivity.class);
        startActivity(i);
    }

    public void onStartClick(View v) {
        Intent i=new Intent(this, AllPlacesOfInterestActivity.class);
        startActivity(i);
    }
}

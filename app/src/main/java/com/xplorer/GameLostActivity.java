package com.xplorer;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xplorer.manager.SettingsManager;

public class GameLostActivity extends AppCompatActivity {
    TextView textViewTypeOfLoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lost);

        ActionBar a = getActionBar();
        if(a != null) {
            a.setDisplayHomeAsUpEnabled(true);
        }

        textViewTypeOfLoose = (TextView) findViewById(R.id.textViewTypeOfLoose);

        Intent intent = getIntent();
        switch (intent.getStringExtra("Type of Lost")) {
            case "nbOfValidation":
                String strNbOfValidation = "You used your " + SettingsManager.getInstance().getMaxNumberOfValidation()+ " attempts";
                textViewTypeOfLoose.setText(strNbOfValidation);
                break;
            case "timerFinished":
                String strTimer = "Timer finished";
                textViewTypeOfLoose.setText(strTimer);
                break;
        }
        //to be sure that the timer is canceled
        if (MapGoalSeekingActivity.timer != null ) {
            MapGoalSeekingActivity.timer.cancel();

        }
    }

    public void onClickBackToMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

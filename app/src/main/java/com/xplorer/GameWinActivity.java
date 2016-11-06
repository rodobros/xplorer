package com.xplorer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xplorer.manager.PlacesOfInterestManager;

public class GameWinActivity extends AppCompatActivity {
    ImageView winImage;
    TextView winLocationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_win);

        winImage = (ImageView)findViewById(R.id.win_image);
        winImage.setImageResource(PlacesOfInterestManager.getInstance().getCurrentGoal().getDrawableID());

        winLocationName = (TextView)findViewById(R.id.text_win_location_name);
        winLocationName.setText(PlacesOfInterestManager.getInstance().getCurrentGoal().getName());

    }

    public void onClickBackMenu(View view) {
        Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}

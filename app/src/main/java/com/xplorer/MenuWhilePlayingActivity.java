package com.xplorer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuWhilePlayingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_while_playing);
    }

    public void onChooseAnotherImageClick(View v){
        Intent i=new Intent(this, MainMenuActivity.class);
        startActivity(i);
    }


    public void onResumeClick(View v){
        Intent i=new Intent(this, MapGoalSeekingActivity.class);
        startActivity(i);
    }
}

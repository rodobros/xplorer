package com.xplorer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import android.widget.GridView;
import android.widget.TextView;

import com.xplorer.manager.PlacesOfInterestManager;
import com.xplorer.util.CustomListAdapter;

public class StatisticsActivity extends Activity {


ListView list;
    String[] itemname;
    Integer[] imgid;
    String[] itemcoords;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ActionBar a = getActionBar();

        if(a != null) {
            a.setDisplayHomeAsUpEnabled(true);
        }

        itemname= PlacesOfInterestManager.getInstance().getStringArrayOfNames();
        imgid= PlacesOfInterestManager.getInstance().getIntArrayOfImages();
        itemcoords= PlacesOfInterestManager.getInstance().getStringArrayOfCoords();

        CustomListAdapter adapter=new CustomListAdapter(this, itemname, imgid, itemcoords);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);



        ////////When clicking on the list a toast pops up!!
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem= itemname[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });
    }


    //Check the "this" 2 rows above, might be wrong and should be "listA"

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

    public void onClickReset(View v) {
        PlacesOfInterestManager.getInstance().resetAllPlacesFound();
        itemname= PlacesOfInterestManager.getInstance().getStringArrayOfNames();
        imgid= PlacesOfInterestManager.getInstance().getIntArrayOfImages();
        itemcoords= PlacesOfInterestManager.getInstance().getStringArrayOfCoords();
        CustomListAdapter adapter=new CustomListAdapter(this, itemname, imgid, itemcoords);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        Toast.makeText(StatisticsActivity.this, "data have been reset", Toast.LENGTH_SHORT).show();
    }
}

package com.xplorer;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.xplorer.business.PlaceOfInterest;
import com.xplorer.manager.PlacesOfInterestManager;
import com.xplorer.manager.SettingsManager;
import com.xplorer.util.PlaceOfInterestImageAdapter;

public class AllPlacesOfInterestActivity extends AppCompatActivity
        implements  GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private TextView textViewDistance;

    private PlaceOfInterestImageAdapter myPlaceOfInterestImageAdapter;
    private GoogleApiClient mGoogleApiClient;
    private Location currentLocation;
    private final int REQUEST_PERMISSION_PHONE_STATE = 1; // constant for the permission callack
    private boolean gridViewInitialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_places_of_interest);
        ActionBar a = getActionBar();
        if (a != null) {
            a.setDisplayHomeAsUpEnabled(true);
        }

        //set the text view
        textViewDistance = (TextView) findViewById(R.id.all_places_within_radius);
        switch (SettingsManager.getInstance().getRadiusDistance()) {
            case 1000 :
                textViewDistance.setText("Select a place of interest (within 1 KM):");
                break;
            case 2500 :
                textViewDistance.setText("Select a place of interest (within 2.5 KM):");
                break;
            case 10000 :
                textViewDistance.setText("Select a place of interest (within 10 KM):");
                break;
            default:
                textViewDistance.setText("Select a place of Interest:");
        }


        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        // The grid view will be initialize when the location will be initialized

    }


    // activity on start
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    // activity on stop
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }




    ////////////////////// GETTING THE LOCATION /////////////////////////////

    // google play service onConnected method (google play service provides location tracking)
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d("debug","now connected" );
        showPhoneStatePermission(); // this will call the update location if the permission is granted
    }

    @Override
    public void onConnectionSuspended(int i) {
        // nothing for now
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // nothing for now
    }

    // code to grant location tracking permission :
    private void showPhoneStatePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                showExplanation("Permission Needed", "Rationale", android.Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_PERMISSION_PHONE_STATE);
            } else {
                requestPermission(android.Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_PERMISSION_PHONE_STATE);
            }
        } else {
            Location location = getCurrentLocation();
            initializeGridView(location);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String permissions[],
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_PHONE_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Location location = getCurrentLocation();
                    initializeGridView(location);
                } else {
                    Toast.makeText(AllPlacesOfInterestActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }

    // code to start and track locations updates:
    protected Location getCurrentLocation() {
        Location mLastLocation = null;
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if(mLastLocation == null){
                // if unable to get position, provide a mock one.
                mLastLocation = new Location("fake provider");
                mLastLocation.setLatitude(22.3321);
                mLastLocation.setLongitude(114.19);
            }
        } catch(SecurityException e) {
            Log.e("location", "permission denied");
        }
        return mLastLocation;
    }

    ////////////////////// END GETTING THE LOCATION /////////////////////////////

    /**
     * Method called when the location is initialized
     */
    private void initializeGridView(Location location) {
            myPlaceOfInterestImageAdapter = new PlaceOfInterestImageAdapter(this);
            myPlaceOfInterestImageAdapter.addPlacesOfInterest(
                    PlacesOfInterestManager.getInstance().getListOfClosePlacesOfInterest(
                            location.getLatitude(), location.getLongitude(),
                            SettingsManager.getInstance().getRadiusDistance()));

            GridView gridview = (GridView) findViewById(R.id.imageGridView);
            Log.d("GRID WIDTH:", ""+ gridview.getWidth());
            gridview.setColumnWidth(gridview.getWidth() / 3);
            myPlaceOfInterestImageAdapter.setImageWidth(gridview.getWidth() / 3);
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
        PlaceOfInterest chosenLocation = ((PlaceOfInterest) myPlaceOfInterestImageAdapter.getItem(position));
        PlacesOfInterestManager.getInstance().startNewGoal(chosenLocation);

        Intent i = new Intent(this, PlaceOfInterestConfirmGoalActivity.class);
        startActivity(i);
    }
}

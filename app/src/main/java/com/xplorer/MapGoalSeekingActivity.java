package com.xplorer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.xplorer.manager.PlacesOfInterestManager;
import com.xplorer.manager.SettingsManager;

public class MapGoalSeekingActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
                   GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap map_;
    private GoogleApiClient mGoogleApiClient;
    private Location currentLocation;
    private Location previousLocation;
    private final int REQUEST_PERMISSION_PHONE_STATE = 1; // constant for the permission callack
    private final float MIN_ZOOM_LEVEL = 14f;
    private final float MAX_ZOOM_LEVEL = 14f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_seeking_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        ((ImageView)findViewById(R.id.imageGoalThumbnail)).setImageResource(PlacesOfInterestManager.getInstance().getCurrentGoal().getDrawableID());

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




    // callback triggered when the map is ready :
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map_ = googleMap;
        map_.setMinZoomPreference(MIN_ZOOM_LEVEL);
        map_.setMaxZoomPreference(MAX_ZOOM_LEVEL);
        map_.getUiSettings().setScrollGesturesEnabled(false);
    }

    // click events handlers:
    public void onMenuClick(View v) {
        Intent i=new Intent(this, MenuWhilePlayingActivity.class);
        startActivity(i);
    }

    public void onImageGoalClick(View v) {
        Intent i=new Intent(this, SeeGoalWhilePlayingActivity.class);
        if(currentLocation != null) {
            i.putExtra("currentLongitude", currentLocation.getLongitude());
            i.putExtra("currentLatitude", currentLocation.getLatitude());
        }
        startActivity(i);
    }

    public void onHotOrColdClick(View v) {

        if(previousLocation== null) {
             Toast.makeText(MapGoalSeekingActivity.this, "You have to move to use the hot or cold function!", Toast.LENGTH_SHORT).show();
        }    else {

            float[] resultsNow = new float[1];
            float[] resultsPrev = new float[1];
            Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(), PlacesOfInterestManager.getInstance().getCurrentGoal().getLatitude(), PlacesOfInterestManager.getInstance().getCurrentGoal().getLongitude(), resultsNow);
            Location.distanceBetween(previousLocation.getLatitude(), previousLocation.getLongitude(), PlacesOfInterestManager.getInstance().getCurrentGoal().getLatitude(), PlacesOfInterestManager.getInstance().getCurrentGoal().getLongitude(), resultsPrev);

            boolean shorterDistance = resultsNow[0] < resultsPrev[0];
            if (shorterDistance) {
                Toast.makeText(MapGoalSeekingActivity.this, "It's getting warmer!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MapGoalSeekingActivity.this, "It's getting colder!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    // google play service onConnected method (google play service provides location tracking)
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d("debug","now connected");
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
            startLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_PHONE_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {
                    Toast.makeText(MapGoalSeekingActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
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
    protected void startLocationUpdates() {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, createLocationRequest(), this);
        } catch(SecurityException e) {
            Log.e("location", "permission denied");
        }
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    @Override
    public void onLocationChanged(Location location) {
        previousLocation = currentLocation;
        currentLocation = location;
        //updateUI();
        try {
            map_.setMyLocationEnabled(true);
            updateUI();
        } catch(SecurityException e) {

        }
    }

    private void updateUI() {
        // update map here
        if(map_ != null) {
            map_.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(this.currentLocation.getLatitude(), this.currentLocation.getLongitude()), MAX_ZOOM_LEVEL));
        }
    }
}


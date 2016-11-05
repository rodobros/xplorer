package com.xplorer.manager;

import android.location.Location;

import com.xplorer.business.PlaceOfInterest;
import com.xplorer.R;
import com.xplorer.util.ApplicationSharedPreference;

import java.util.ArrayList;

/**
 * Provide thanks to the singleton patter an unique instance of this manager
 *
 * Contains all information related to list of place of interest
 * Created by Rodobros on 2016-10-21.
 */
public class PlacesOfInterestManager {
    private static PlacesOfInterestManager instance;

    private ArrayList<PlaceOfInterest> listOfPlacesOfInterest = new ArrayList<PlaceOfInterest>();
    private PlaceOfInterest currentGoal = new PlaceOfInterest("default", 0.0,0.0,1);

    private int currentNumberOfValidation;
    private int currentNumberOfCheck;
    private int currentTimer;

    /////////////////// Constructor and get instance
    private PlacesOfInterestManager() {
        // to initialize all places of interest (currently hard coded)
        this.addAllPlacesOfInterest();
    }

    /**
     * Pattern singleton, instance of the PlaceOfInterestManager
     * @return instance of the PlaceOfInterestManager
     */
    public static PlacesOfInterestManager getInstance() {
        if(instance == null){
            instance = new PlacesOfInterestManager();
        }
        return instance;
    }


    /////////////////// getter list of interest

    /**
     * @return all places of interest
     */
    public  ArrayList<PlaceOfInterest> getListOfPlacesOfInterest() {
        return listOfPlacesOfInterest;
    }

    /**
     * @param latitude Double current latitude
     * @param longitude Double current longitude
     * @param radius int radius in meter
     * @return return all places of interest which within the radius.
     */
    public ArrayList<PlaceOfInterest> getListOfClosePlacesOfInterest(Double latitude, Double longitude, int radius) {
        ArrayList<PlaceOfInterest> listOfClosePlacesOfInterest = new ArrayList<PlaceOfInterest>();

        PlaceOfInterest currentPlace ;
        float[] results = new float[1];

        for(int i = 0 ; i < listOfPlacesOfInterest.size() ; ++i) {
            currentPlace = listOfPlacesOfInterest.get(i);
            Location.distanceBetween(latitude, longitude, currentPlace.getLatitude(), currentPlace.getLongitude(), results);
            if (results[0] < radius
                    && !ApplicationSharedPreference.getBoolData(currentPlace.getName() + "isValidated")) {
                listOfClosePlacesOfInterest.add(currentPlace);
            }
        }
        return listOfClosePlacesOfInterest;
    }


    /////////////////// other getters
    public PlaceOfInterest getCurrentGoal() {
        return currentGoal;
    }

    public int getCurrentNumberOfCheck() {
        return currentNumberOfCheck;
    }

    public int getCurrentTimer() {
        return currentTimer;
    }

    public int getCurrentNumberOfValidation() {
        return currentNumberOfValidation;
    }

    /////////////// Setters
    public void setCurrentGoal(PlaceOfInterest placeOfInterest) {
        currentGoal = placeOfInterest;
    }

    public void setCurrentNumberOfValidation(int currentNumberOfValidation) {
        this.currentNumberOfValidation = currentNumberOfValidation;
    }

    public void setCurrentNumberOfCheck(int currentNumberOfCheck) {
        this.currentNumberOfCheck = currentNumberOfCheck;
    }

    public void setCurrentTimer(int currentTimer) {
        this.currentTimer = currentTimer;
    }


    /////////////// list editor

    /**
     * @param placeOfInterest add this place of interest to the list of all places of interest
     *                        if this place is not already found.
     */
    private void addNewPlaceOfInterest(PlaceOfInterest placeOfInterest) {
        if(!ApplicationSharedPreference.getBoolData(placeOfInterest.getName() + "isValidated")) {
            listOfPlacesOfInterest.add(placeOfInterest);
        }
    }

    /**
     * Sett all places of interest found to not found
     */
    public void resetAllPlacesFound() {
        listOfPlacesOfInterest.clear();

        this.addAllPlacesOfInterest();

        for(int i = 0 ; i < listOfPlacesOfInterest.size() ; ++i) {
            resetPlaceOfInterestFound(listOfPlacesOfInterest.get(i));
        }
    }

    /**
     * Hard coded edition of all places of interest
     */
    private void addAllPlacesOfInterest() {
        listOfPlacesOfInterest.clear();
        listOfPlacesOfInterest.add(new PlaceOfInterest("City University Hong Kong", 22.33707, 114.172711, R.drawable.city_u));
        listOfPlacesOfInterest.add(new PlaceOfInterest("Festival Walk Ice Ring", 22.3379, 114.174022, R.drawable.festival_walk));
        listOfPlacesOfInterest.add(new PlaceOfInterest("Lion rock", 22.3523, 114.1870, R.drawable.lion_rock));
        listOfPlacesOfInterest.add(new PlaceOfInterest("Kowloon Walled City Park", 22.3321, 114.1903, R.drawable.kowloon_walled_city_park));
        listOfPlacesOfInterest.add(new PlaceOfInterest("Bruce Lee's Statue", 22.294875, 114.175711, R.drawable.bruce_lee));
        listOfPlacesOfInterest.add(new PlaceOfInterest("Bank Of China Tower", 22.2793, 114.1615, R.drawable.bank_of_china_tower));
        listOfPlacesOfInterest.add(new PlaceOfInterest("City University Hall 11", 22.33988, 114.16937, R.drawable.city_u_hall_11));
    }

    public void setPlaceOfInterestFound(PlaceOfInterest placeOfInterest) {
        ApplicationSharedPreference.saveBoolData(placeOfInterest.getName() + "isValidated", Boolean.TRUE);
        listOfPlacesOfInterest.remove(placeOfInterest);
    }

    private void resetPlaceOfInterestFound(PlaceOfInterest placeOfInterest) {
        ApplicationSharedPreference.saveBoolData(placeOfInterest.getName() + "isValidated", Boolean.FALSE);
    }

    public void startNewGoal(PlaceOfInterest newGoal) {
        this.setCurrentGoal(newGoal);
        this.setCurrentNumberOfValidation(SettingsManager.getInstance().getMaxNumberOfValidation());
        this.setCurrentNumberOfCheck(SettingsManager.getInstance().getMaxNumberOfCheck());
        this.setCurrentTimer(SettingsManager.getInstance().getTimer());
    }

    ///////////////// Other methods

}

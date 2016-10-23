package com.xplorer;

import java.util.ArrayList;

/**
 * Created by Rodobros on 2016-10-21.
 */
public class PlacesOfInterestManager {

    private PlacesOfInterestManager() {
        addNewPlaceOfInterest(new PlaceOfInterest("City University Hong Kong", 22.33707, 114.172711, R.drawable.city_u));
        addNewPlaceOfInterest(new PlaceOfInterest("Festival Walk Ice Ring", 22.3379, 114.174022, R.drawable.festival_walk));
        addNewPlaceOfInterest(new PlaceOfInterest("Lion rock", 22.3523, 114.1870, R.drawable.lion_rock));
        addNewPlaceOfInterest(new PlaceOfInterest("Kowloon Walled City Park", 22.3321, 114.1903, R.drawable.kowloon_walled_city_park));
        addNewPlaceOfInterest(new PlaceOfInterest("Bruce Lee's Statue", 22.294875, 114.175711, R.drawable.bruce_lee));
        addNewPlaceOfInterest(new PlaceOfInterest("Bank Of China Tower", 22.2793, 114.1615, R.drawable.bank_of_china_tower));
        addNewPlaceOfInterest(new PlaceOfInterest("City University Hall 11", 22.33988, 114.16937, R.drawable.city_u_hall_11));
    }

    private void addNewPlaceOfInterest(PlaceOfInterest value) {
        if(!ApplicationWithPreference.getBoolData(value.getName() + "isValidated")) {
            listOfPlacesOfInterest.add(value);
        }
    }

    public void setPlaceOfInterestFound(PlaceOfInterest value) {
        ApplicationWithPreference.saveBoolData(value.getName() + "isValidated", Boolean.TRUE);
        listOfPlacesOfInterest.remove(value);
    }

    private void resetPlaceOfInterest(PlaceOfInterest value) {
        ApplicationWithPreference.saveBoolData(value.getName() + "isValidated", Boolean.FALSE);
    }


    public void resetPlacesFound() {
        listOfPlacesOfInterest.clear();
        listOfPlacesOfInterest.add(new PlaceOfInterest("City University Hong Kong", 22.33707, 114.172711, R.drawable.city_u));
        listOfPlacesOfInterest.add(new PlaceOfInterest("Festival Walk Ice Ring", 22.3379, 114.174022, R.drawable.festival_walk));
        listOfPlacesOfInterest.add(new PlaceOfInterest("Lion rock", 22.3523, 114.1870, R.drawable.lion_rock));
        listOfPlacesOfInterest.add(new PlaceOfInterest("Kowloon Walled City Park", 22.3321, 114.1903, R.drawable.kowloon_walled_city_park));
        listOfPlacesOfInterest.add(new PlaceOfInterest("Bruce Lee's Statue", 22.294875, 114.175711, R.drawable.bruce_lee));
        listOfPlacesOfInterest.add(new PlaceOfInterest("Bank Of China Tower", 22.2793, 114.1615, R.drawable.bank_of_china_tower));
        listOfPlacesOfInterest.add(new PlaceOfInterest("City University Hall 11", 22.33988, 114.16937, R.drawable.city_u_hall_11));

        for(int i = 0 ; i < listOfPlacesOfInterest.size() ; ++i) {
            resetPlaceOfInterest(listOfPlacesOfInterest.get(i));
        }

    }


    public static PlacesOfInterestManager getInstance() {
        if(instance == null){
            instance = new PlacesOfInterestManager();
        }
        return instance;
    }

    public  ArrayList<PlaceOfInterest> getListOfPlacesOfInterest() {
        return listOfPlacesOfInterest;
    }

    public void setCurrentGoal(PlaceOfInterest value) {
        currentGoal = value;
    }

    public PlaceOfInterest getCurrentGoal() {
        return currentGoal;
    }

    private ArrayList<PlaceOfInterest> listOfPlacesOfInterest = new ArrayList<PlaceOfInterest>();
    private PlaceOfInterest currentGoal = new PlaceOfInterest("default", 0.0,0.0,1);
    private static PlacesOfInterestManager instance;
}
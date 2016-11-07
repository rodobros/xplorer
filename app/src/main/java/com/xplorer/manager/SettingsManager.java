package com.xplorer.manager;

import com.xplorer.business.Difficulty;
import com.xplorer.business.PreferencesName;
import com.xplorer.business.TypeOfInformation;
import com.xplorer.util.ApplicationSharedPreference;

/**
 * Created by Rodobros on 2016-10-23.
 */
public class SettingsManager {
    private static SettingsManager instance;
    private int validateGoalMinDistance;
    private Difficulty currentDifficulty;
    private int radiusDistance;
    private int maxNumberOfValidation;
    private int maxNumberOfCheck;
    private TypeOfInformation typeOfInformation;
    private int timer;




    private SettingsManager(){
        if(ApplicationSharedPreference.getIntData(PreferencesName.ValidateGoalMinDistance.name()) != -1) {// -1 is the default value
            validateGoalMinDistance = ApplicationSharedPreference.getIntData(PreferencesName.ValidateGoalMinDistance.name()); // in meters
        } else {
            validateGoalMinDistance = 200;
        }

        if(ApplicationSharedPreference.getIntData(PreferencesName.DifficultyLevel.name()) != -1) {// -1 is the default value
            currentDifficulty = Difficulty.values()
                    [ApplicationSharedPreference.getIntData(PreferencesName.DifficultyLevel.name())]; // in difficulty
        } else {
            currentDifficulty = Difficulty.Normal;
        }

        if(ApplicationSharedPreference.getIntData(PreferencesName.RadiusDistance.name()) != -1) {// -1 is the default value
            radiusDistance = ApplicationSharedPreference.getIntData(PreferencesName.RadiusDistance.name()); // in meters
        } else {
            radiusDistance = 1000; // the radius distance in meter
        }

        if(ApplicationSharedPreference.getIntData(PreferencesName.MaxNumberOfValidation.name()) != -1) {// -1 is the default value
            maxNumberOfValidation = ApplicationSharedPreference.getIntData(PreferencesName.MaxNumberOfValidation.name()); // in number
        } else {
            maxNumberOfValidation = 10000; // the radius distance in meter
        }

        if(ApplicationSharedPreference.getIntData(PreferencesName.MaxNumberOfCheck.name()) != -1) {// -1 is the default value
            maxNumberOfCheck = ApplicationSharedPreference.getIntData(PreferencesName.MaxNumberOfCheck.name()); // in number
        } else {
            maxNumberOfCheck = 10000; // the radius distance in meter
        }

        if(ApplicationSharedPreference.getIntData(PreferencesName.TypeOfInformation.name()) != -1) {// -1 is the default value
            typeOfInformation = TypeOfInformation.values()
                    [ApplicationSharedPreference.getIntData(PreferencesName.TypeOfInformation.name())]; // in Type of information
        } else {
            typeOfInformation = TypeOfInformation.Image;
        }

        if(ApplicationSharedPreference.getIntData(PreferencesName.Timer.name()) != -1) {// -1 is the default value
            timer = ApplicationSharedPreference.getIntData(PreferencesName.Timer.name()); // in seconds ?
        } else {
            timer = 100000; // in seconds ?
        }
    }

    public static SettingsManager getInstance() {
        if(instance == null){
            instance = new SettingsManager();
        }
        return instance;
    }


    // GETTERS
    public int getValidateGoalMinDistance(){
        return this.validateGoalMinDistance;
    }

    public Difficulty getCurrentDifficulty(){
        return currentDifficulty;
    }

    public int getRadiusDistance(){
        return this.radiusDistance;
    }

    public int getMaxNumberOfValidation() {
        return maxNumberOfValidation;
    }

    public int getMaxNumberOfCheck() {
        return maxNumberOfCheck;
    }

    public TypeOfInformation getTypeOfInformation() {
        return typeOfInformation;
    }

    public int getTimer() {
        return timer;
    }

    // SETTERS
    public void setValidateGoalMinDistance(Integer value){
        this.validateGoalMinDistance = value;
        ApplicationSharedPreference.saveIntData(PreferencesName.ValidateGoalMinDistance.name(), value);
    }

    public void setRadiusDistance(Integer value){
        this.radiusDistance = value;
        ApplicationSharedPreference.saveIntData(PreferencesName.RadiusDistance.name(), value);
    }

    public void setCurrentDifficulty(Difficulty value){
        currentDifficulty = value;
        ApplicationSharedPreference.saveIntData(PreferencesName.DifficultyLevel.name(), value.ordinal());
    }

    public void setMaxNumberOfValidation(Integer value){
        this.maxNumberOfValidation = value;
        ApplicationSharedPreference.saveIntData(PreferencesName.MaxNumberOfValidation.name(), value);
    }

    public void setMaxNumberOfCheck(Integer value){
        this.maxNumberOfCheck = value;
        ApplicationSharedPreference.saveIntData(PreferencesName.MaxNumberOfCheck.name(), value);
    }

    public void setTypeOfInformation(TypeOfInformation value){
        typeOfInformation = value;
        ApplicationSharedPreference.saveIntData(PreferencesName.TypeOfInformation.name(), value.ordinal());
    }

    public void setTimer(Integer value){
        this.timer = value;
        ApplicationSharedPreference.saveIntData(PreferencesName.Timer.name(), value);
    }
}

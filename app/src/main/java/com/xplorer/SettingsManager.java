package com.xplorer;

/**
 * Created by Rodobros on 2016-10-23.
 */
public class SettingsManager {
    private static SettingsManager instance;
    private int validateGoalMinDistance_;
    private int radiusDistance;
    private Difficulty currentDifficulty_;

    private SettingsManager(){
        if(ApplicationWithPreference.getIntData(PreferencesName.validateGoalMinDistance.name()) != -1) {// -1 is the default value
            validateGoalMinDistance_ = ApplicationWithPreference.getIntData(PreferencesName.validateGoalMinDistance.name()); // in meters
        } else {
            validateGoalMinDistance_ = 200;
        }

        if(ApplicationWithPreference.getIntData(PreferencesName.radiusDistance.name()) != -1) {// -1 is the default value
            radiusDistance = ApplicationWithPreference.getIntData(PreferencesName.radiusDistance.name()); // in meters
        } else {
            radiusDistance = 1000; // the radius distance in meter
        }

        if(ApplicationWithPreference.getIntData(PreferencesName.DifficultyLevel.name()) != -1) {// -1 is the default value
            currentDifficulty_ = Difficulty.values()[ApplicationWithPreference.getIntData(PreferencesName.DifficultyLevel.name())]; // in meters
        } else {
            currentDifficulty_ = Difficulty.Normal;
        }
    }

    public static SettingsManager getInstance() {
        if(instance == null){
            instance = new SettingsManager();
        }
        return instance;
    }

    public int getValidateGoalMinDistance(){
        return this.validateGoalMinDistance_;
    }

    public int getRadiusDistance(){
        return this.radiusDistance;
    }

    public Difficulty getCurrentDifficulty(){
        return currentDifficulty_;
    }

    public void setValidateGoalMinDistance(Integer value){
        this.validateGoalMinDistance_ = value;
        ApplicationWithPreference.saveIntData(PreferencesName.validateGoalMinDistance.name(), value);
    }

    public void setRadiusDistance(Integer value){
        this.radiusDistance = value;
        ApplicationWithPreference.saveIntData(PreferencesName.radiusDistance.name(), value);
    }

    public void setCurrentDifficulty(Difficulty value){
        currentDifficulty_ = value;
        ApplicationWithPreference.saveIntData(PreferencesName.DifficultyLevel.name(), value.ordinal());
    }
}

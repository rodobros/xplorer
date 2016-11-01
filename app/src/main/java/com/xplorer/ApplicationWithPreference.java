package com.xplorer;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Rodobros on 2016-10-23.
 */
public class ApplicationWithPreference extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this.getApplicationContext();
    }

    public static void saveStringData(String name, String value){
        SharedPreferences.Editor e = appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        e.putString(name,value);
        e.commit();
    }

    public static void saveIntData(String name, Integer value){
        SharedPreferences.Editor e = appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        e.putInt(name,value);
        e.commit();
    }

    public static void saveFloatData(String name, Float value){
        SharedPreferences.Editor e = appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        e.putFloat(name,value);
        e.commit();
    }

    public static void saveBoolData(String name, Boolean value){
        SharedPreferences.Editor e = appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        e.putBoolean(name,value);
        e.commit();
    }

    public static String getStringData(String name){
        return appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getString(name,"no data");
    }

    public static Integer getIntData(String name){
        return appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getInt(name, -1);
    }

    public static Float getFloatData(String name){
        return appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getFloat(name, -1f);
    }

    public static Boolean getBoolData(String name){
        return appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getBoolean(name, Boolean.FALSE);
}

    private static Context appContext;
    private static final String SHARED_PREFERENCES_NAME = "prefs";
}

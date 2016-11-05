package com.xplorer;

import android.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.xplorer.business.Difficulty;
import com.xplorer.business.TypeOfInformation;
import com.xplorer.manager.SettingsManager;

public class SettingsActivity extends AppCompatActivity
        implements OnItemSelectedListener {

    Spinner spinnerDifficulty;
    Spinner spinnerRadius;
    Spinner spinnerTypeOfInformation;
    Spinner spinnerTimer;
    EditText editTextNbOfValidation;
    EditText editTextNbOfCheck;
    SettingsManager settingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar a = getActionBar();
        if(a != null) {
            a.setDisplayHomeAsUpEnabled(true);
        }

        settingsManager = SettingsManager.getInstance();

        // Difficulty
        this.spinnerDifficulty = (Spinner) findViewById(R.id.settingsSpinnerDifficulty);
        ArrayAdapter<CharSequence> adapterDifficulty = ArrayAdapter.createFromResource(this,
                R.array.settingsDifficultyList, android.R.layout.simple_spinner_item);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerDifficulty.setAdapter(adapterDifficulty);
        this.spinnerDifficulty.setOnItemSelectedListener(this);
        switch (settingsManager.getCurrentDifficulty()) {
            case Easy:
                this.spinnerDifficulty.setSelection(0);
                break;
            case Normal:
                this.spinnerDifficulty.setSelection(1);
                break;
            case Hard:
                this.spinnerDifficulty.setSelection(2);
                break;
            case Personal:
                this.spinnerDifficulty.setSelection(3);
                break;
        }

        //Radius
        this.spinnerRadius = (Spinner) findViewById(R.id.settingsSpinnerRadius);
        ArrayAdapter<CharSequence> adapterRadius = ArrayAdapter.createFromResource(this,
                R.array.settingsRadiusList, android.R.layout.simple_spinner_item);
        adapterRadius.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerRadius.setAdapter(adapterRadius);
        //this.spinnerRadius.setOnItemSelectedListener(this);
        switch (settingsManager.getRadiusDistance()) {
            case 1000 :
                this.spinnerRadius.setSelection(0);
                break;
            case 2500 :
                this.spinnerRadius.setSelection(1);
                break;
            case 10000 :
                this.spinnerRadius.setSelection(2);
                break;
            case 12742 :
                this.spinnerRadius.setSelection(3);
                break;
            default:
                this.spinnerRadius.setSelection(0);
        }

        // Number of validation
        editTextNbOfValidation = (EditText) findViewById(R.id.settingsNumberOfValidation);
        String strValidation = "" + settingsManager.getMaxNumberOfValidation();
        editTextNbOfValidation.setText(strValidation);

        // Number of check
        editTextNbOfCheck = (EditText) findViewById(R.id.settingsNumberOfCheck);
        String strCheck = "" + settingsManager.getMaxNumberOfCheck();
        editTextNbOfCheck.setText(strCheck);

        // Type of information
        this.spinnerTypeOfInformation = (Spinner) findViewById(R.id.settingsSpinnerTypeOfInformation);
        ArrayAdapter<CharSequence> adapterTypeOfInfo = ArrayAdapter.createFromResource(this,
                R.array.settingsTypeOfInformation, android.R.layout.simple_spinner_item);
        adapterTypeOfInfo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerTypeOfInformation.setAdapter(adapterTypeOfInfo);
        //this.spinnerTypeOfInformation.setOnItemSelectedListener(this);
        switch (settingsManager.getTypeOfInformation()) {
            case TitleWithImage:
                this.spinnerTypeOfInformation.setSelection(0);
                break;
            case Image:
                this.spinnerTypeOfInformation.setSelection(1);
                break;
            case RandomizedImage:
                this.spinnerTypeOfInformation.setSelection(2);
                break;
        }

        // Timer
        this.spinnerTimer = (Spinner) findViewById(R.id.settingsSpinnerTimer);
        ArrayAdapter<CharSequence> adapterTimer = ArrayAdapter.createFromResource(this,
                R.array.settingsTimer, android.R.layout.simple_spinner_item);
        adapterTimer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerTimer.setAdapter(adapterTimer);
        //this.spinnerTimer.setOnItemSelectedListener(this);
        switch (settingsManager.getTimer()) {
            case -1 :
                this.spinnerTimer.setSelection(0);
                break;
            case 3600 :
                this.spinnerTimer.setSelection(1);
                break;
            case 10800 :
                this.spinnerTimer.setSelection(2);
                break;
            case 36000 :
                this.spinnerTimer.setSelection(3);
                break;
        }

        // TODO add item listener on the difficulty this.spinnerDifficulty.setVisibility(View.GONE);
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

    public void onClickSave(View view) {
        // Difficulty
        switch (this.spinnerDifficulty.getSelectedItem().toString()) {
            case "Easy":
                settingsManager.setCurrentDifficulty(Difficulty.Easy);
                break;
            case "Normal":
                settingsManager.setCurrentDifficulty(Difficulty.Normal);
                break;
            case "Hard":
                settingsManager.setCurrentDifficulty(Difficulty.Hard);
                break;
            case "Personal":
                settingsManager.setCurrentDifficulty(Difficulty.Personal);
                break;
        }

        //Radius
        switch (this.spinnerRadius.getSelectedItem().toString()) {
            case "1 KM":
                settingsManager.setRadiusDistance(1000);
                break;
            case "2.5 KM":
                settingsManager.setRadiusDistance(2500);
                break;
            case "10 KM":
                settingsManager.setRadiusDistance(10000);
                break;
            case "Unlimited":
                settingsManager.setRadiusDistance(12742);
                break;
        }

        //Number of Validation
        Integer nbOfValidation = Integer.parseInt(this.editTextNbOfValidation.getText().toString());
        settingsManager.setMaxNumberOfValidation(nbOfValidation);

        //Number of Check
        Integer nbOfCheck = Integer.parseInt(this.editTextNbOfCheck.getText().toString());
        settingsManager.setMaxNumberOfCheck(nbOfCheck);

        // Type of Information
        switch (this.spinnerTypeOfInformation.getSelectedItem().toString()) {
            case "Title + Image" :
                settingsManager.setTypeOfInformation(TypeOfInformation.TitleWithImage);
                break;
            case "Image":
                settingsManager.setTypeOfInformation(TypeOfInformation.Image);
                break;
            case "Randomized Image" :
                settingsManager.setTypeOfInformation(TypeOfInformation.RandomizedImage);
                break;
        }

        //Timer
        switch (this.spinnerTimer.getSelectedItem().toString()) {
            case "None" :
                settingsManager.setTimer(-1);
                break;
            case "1 Hour" :
                settingsManager.setTimer(3600);
                break;
            case "3 Hours" :
                settingsManager.setTimer(10800);
                break;
            case "10 Hours" :
                settingsManager.setTimer(36000);
                break;
        }

        Toast.makeText(this, "Settings saved", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String strValidation ;
        String strCheck;
        switch (this.spinnerDifficulty.getSelectedItem().toString()) {
            case "Easy":
                strValidation = "" + 1000;
                this.editTextNbOfValidation.setText(strValidation);
                strCheck = "" + 1000;
                this.editTextNbOfCheck.setText(strCheck);
                this.spinnerTypeOfInformation.setSelection(0);
                this.spinnerTimer.setSelection(0);
                setEditablePersonalizableFields(false);
                break;
            case "Normal":
                strValidation = "" + 5;
                this.editTextNbOfValidation.setText(strValidation);
                strCheck = "" + 15;
                this.editTextNbOfCheck.setText(strCheck);
                this.spinnerTypeOfInformation.setSelection(1);
                this.spinnerTimer.setSelection(3);
                setEditablePersonalizableFields(false);
                break;
            case "Hard":
                strValidation = "" + 3;
                this.editTextNbOfValidation.setText(strValidation);
                strCheck = "" + 8;
                this.editTextNbOfCheck.setText(strCheck);
                this.spinnerTypeOfInformation.setSelection(2);
                this.spinnerTimer.setSelection(1);
                setEditablePersonalizableFields(false);
                break;
            case "Personal":
                setEditablePersonalizableFields(true);
                break;
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /**
     *  To enable or not the modification of fields :
     *  - editTextNbOfValidation
     *  - editTextNbOfCheck
     *  - spinnerTypeOfInformation
     *  - spinnerTimer
     *
     *  Use when it's on personal difficulty or preset difficulty
     *
     * @param bool if these elements are enabled or not
     */
    private void setEditablePersonalizableFields(boolean bool) {
        this.editTextNbOfValidation.setEnabled(bool);
        this.editTextNbOfCheck.setEnabled(bool);
        this.spinnerTypeOfInformation.setEnabled(bool);
        this.spinnerTimer.setEnabled(bool);

    }

}

package com.spud.sgsclasscountdownapp;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Stephen Ogden on 4/23/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
public class Settings extends AppCompatActivity {

    Button devSettings;

    Switch autoSwitch;

    RadioGroup dayGroup;

    RadioButton button8, buttonA, buttonE;

    private Database database = new Database();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        devSettings = findViewById(R.id.devSettings);

        autoSwitch = findViewById(R.id.override);

        dayGroup = findViewById(R.id.overRideGroup);

        button8 = findViewById(R.id.override8);
        buttonA = findViewById(R.id.overrideA);
        buttonE = findViewById(R.id.overrideE);

        if (database.databaseExists()) {
            autoSwitch.setEnabled(true);
            button8.setChecked(database.getUpdateType().equals(Database.updateType.ManualFullDay));
            buttonA.setChecked(database.getUpdateType().equals(Database.updateType.ManualADay));
            buttonE.setChecked(database.getUpdateType().equals(Database.updateType.ManualEDay));
            autoSwitch.setChecked(database.getUpdateType().equals(Database.updateType.Automatic));
        } else {
            autoSwitch.setChecked(true);
            autoSwitch.setEnabled(false);
        }

        if (autoSwitch.isChecked()) {
            dayGroup.setVisibility(View.INVISIBLE);
        } else {
            dayGroup.setVisibility(View.VISIBLE);
        }

    }

    protected void onResume() {
        super.onResume();
        devSettings.setVisibility(View.GONE);

        if (database.databaseExists()) {
            autoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        dayGroup.setVisibility(View.INVISIBLE);
                    } else {
                        dayGroup.setVisibility(View.VISIBLE);

                    }
                }
            });

            button8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                }
            });

            buttonA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                }
            });

            buttonE.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                }
            });
        }
    }
}

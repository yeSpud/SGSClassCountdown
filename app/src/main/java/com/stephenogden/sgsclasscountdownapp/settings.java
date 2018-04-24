package com.stephenogden.sgsclasscountdownapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Stephen Ogden on 4/23/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
public class settings extends AppCompatActivity {

    Button devSettings;

    Switch autoSwitch;

    RadioGroup dayGroup;

    RadioButton button8, buttonA, buttonE;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        devSettings = findViewById(R.id.devSettings);

        autoSwitch = findViewById(R.id.override);

        dayGroup = findViewById(R.id.overRideGroup);

        button8 = findViewById(R.id.override8);
        buttonA = findViewById(R.id.overrideA);
        buttonE = findViewById(R.id.overrideE);

    }

    protected void onResume() {
        super.onResume();

        devSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(settings.this, developer.class));
            }
        });

        autoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    dayGroup.setVisibility(View.INVISIBLE);
                    developer developer = new developer();
                    try {
                        FileOutputStream fOS = new FileOutputStream(developer.localStorage);
                        fOS.write("Auto".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("Error","Cannot update file");
                    }
                } else {
                    dayGroup.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}

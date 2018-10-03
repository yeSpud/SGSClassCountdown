package com.stephenogden.sgsclasscountdownapp;

import android.content.Context;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

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

        Timer Timer = new Timer();

        devSettings = findViewById(R.id.devSettings);

        autoSwitch = findViewById(R.id.override);

        dayGroup = findViewById(R.id.overRideGroup);

        button8 = findViewById(R.id.override8);
        buttonA = findViewById(R.id.overrideA);
        buttonE = findViewById(R.id.overrideE);

        /*
        button8.setChecked(read_file(Timer.context, developer.localStorage.getName()).startsWith("8"));
        buttonA.setChecked(read_file(Timer.context, developer.localStorage.getName()).startsWith("A"));
        buttonE.setChecked(read_file(Timer.context, developer.localStorage.getName()).startsWith("E"));
        autoSwitch.setChecked(read_file(Timer.context, developer.localStorage.getName()).startsWith("Auto"));
        */

        if (autoSwitch.isChecked()) {
            dayGroup.setVisibility(View.INVISIBLE);
        } else {
            dayGroup.setVisibility(View.VISIBLE);
        }

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
                    try {
                        FileWriter fOS = new FileWriter(developer.localStorage);
                        fOS.write("Auto");
                        fOS.flush();
                        fOS.close();
                        Log.i("Updated file","Auto");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("Error","Cannot update file");
                    }
                } else {
                    dayGroup.setVisibility(View.VISIBLE);
                    try {
                        FileWriter fOS = new FileWriter(developer.localStorage);
                        if (button8.isChecked()) {
                            fOS.write("8");
                            fOS.flush();
                            fOS.close();
                            Log.i("Updated file","8");
                        } else if (buttonA.isChecked()) {
                            fOS.write("A");
                            fOS.flush();
                            fOS.close();
                            Log.i("Updated file", "A");
                        } else if (buttonE.isChecked()) {
                            fOS.write("E");
                            fOS.flush();
                            fOS.close();
                            Log.i("Updated file", "E");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("Error","Cannot update file");
                    }
                }
            }
        });

        button8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    try {
                        FileWriter fOS = new FileWriter(developer.localStorage);
                        fOS.write("8");
                        fOS.flush();
                        fOS.close();
                        Log.i("Updated file", "8");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("Error", "Cannot update file");
                    }
                }
            }
        });

        buttonA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    try {
                        FileWriter fOS = new FileWriter(developer.localStorage);
                        fOS.write("A");
                        fOS.flush();
                        fOS.close();
                        Log.i("Updated file", "A");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("Error", "Cannot update file");
                    }
                }
            }
        });

        buttonE.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    try {
                        FileWriter fOS = new FileWriter(developer.localStorage);
                        fOS.write("E");
                        fOS.flush();
                        fOS.close();
                        Log.i("Updated file", "E");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("Error","Cannot update file");
                    }
                }
            }
        });
    }

    public String read_file(Context context, String filename) {
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
    }
}

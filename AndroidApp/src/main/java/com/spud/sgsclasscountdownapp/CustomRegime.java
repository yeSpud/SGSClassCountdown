package com.spud.sgsclasscountdownapp;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Stephen Ogden on 10/23/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
class CustomRegime {

    @SuppressLint("SdCardPath")
    private File CustomRegimeFile = new File("/data/data/" + this.getClass().getPackage().getName() + "/Custom.json");

    boolean isEmpty() {
        return CustomRegimeFile.length() == 0;
    }

    JSONObject loadCustomRegime() throws CustomRegimeError {
        JSONObject jSON = null;

        // Check if the regime is empty
        if (isEmpty()) {
            throw new CustomRegimeError("The custom regime is empty!");
        } else {

            // https://stackoverflow.com/questions/13814503/reading-a-json-file-in-android
            InputStream inStream = null;
            try {
                inStream = new FileInputStream(CustomRegimeFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                RegimeFiles regime = new RegimeFiles();
                regime.createCustomRegime();
            }

            int size = 0;
            try {
                size = inStream != null ? inStream.available() : 0;
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] buffer = new byte[size];

            try {
                if (inStream != null) {
                    int read = inStream.read(buffer);
                    Log.d("Stream", Integer.toString(read));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                jSON = new JSONObject(new String(buffer, "UTF-8")).getJSONObject("Custom Regime");
            } catch (JSONException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        if (jSON != null) {
            Log.i("CustomRegime", jSON.toString());
        }
        return jSON;
    }

    void writeCustomRegime(ArrayList<ClassTime> classes) {
        // Delete and recreate the regime if one exists
        if (!isEmpty()) {
            RegimeFiles regime = new RegimeFiles();
            regime.deleteCustomRegime();
            regime.createCustomRegime();
        }

        try {
            JSONObject FileContence = new JSONObject();
            JSONObject customRegime = new JSONObject();

            // For each block entered, get the name, and times
            for (int i = 0; i < classes.size(); i++) {
                ClassTime clss = classes.get(i);
                Log.d("AddingClass", clss.block.name());
                // Have to manually add seconds :P
                clss.startTime = clss.startTime + ":00";
                clss.endTime = clss.endTime + ":00";
                customRegime.put(clss.block.name(), new JSONArray().put(0, clss.startTime).put(1, clss.endTime));
            }

            // Add that crap to the custom database
            FileContence.put("Custom Regime", customRegime);

            // Write it to the file
            try {
                FileWriter writer = new FileWriter(CustomRegimeFile);
                writer.write(FileContence.toString(4));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JSONException jSONError) {
            jSONError.printStackTrace();
        }

    }

    boolean isBlockNotInCustomRegime(Block block) {
        boolean returnBool = false;

        try {
            // Load the custom regime
            JSONObject regime = loadCustomRegime();
            Log.d("CustomRegime", regime.toString());

            // If we can get the block name from the regime, then return true
            returnBool = regime.toString().contains(block.name());

        } catch (CustomRegimeError customRegimeError) {
            customRegimeError.printStackTrace();
        }

        Log.i("BlockInCustomRegime", Boolean.toString(returnBool));
        return !returnBool;
    }

}

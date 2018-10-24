package com.spud.sgsclasscountdownapp;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

            // TODO: Finish me

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
        // TODO: Finish me
        
    }

}

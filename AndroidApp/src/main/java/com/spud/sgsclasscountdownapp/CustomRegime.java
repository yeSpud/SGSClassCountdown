package com.spud.sgsclasscountdownapp;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;

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

        // Check if the regime is empty
        if (isEmpty()) {
            throw new CustomRegimeError("The custom regime is empty!");
        } else {
            JSONObject jSON = null;
            // TODO: Finish me
            Log.i("CustomRegime", jSON.toString());
            return jSON;
        }
    }

    void writeCustomRegime() {
        // TODO: Finsish me
    }

}

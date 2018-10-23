package com.spud.sgsclasscountdownapp;

import android.annotation.SuppressLint;

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

}

package com.spud.sgsclasscountdownapp;

import android.util.Log;

/**
 * Created by Stephen Ogden on 10/9/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
public enum DatabaseKeys {
    DatabaseVersion("Database version"),
    UpdateType("Update type"),
    ABlockClassName("A block class name"),
    BBlockClassName("B block class name"),
    CBlockClassName("C block class name"),
    DBlockClassName("D block class name"),
    EBlockClassName("E block class name"),
    FBlockClassName("F block class name"),
    GBlockClassName("G block class name"),
    HBlockClassName("H block class name");
    private String value;

    DatabaseKeys(String value) {
        this.value = value;
    }

    public String getName() {
        Log.i("DatabaseKey", value);
        return value;
    }
}

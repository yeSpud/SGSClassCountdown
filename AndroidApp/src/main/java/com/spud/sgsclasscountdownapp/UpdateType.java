package com.spud.sgsclasscountdownapp;

import android.util.Log;

/**
 * Created by Stephen Ogden on 10/9/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
public enum UpdateType {
    BuiltIn,
    Automatic,
    ManualADay,
    ManualEDay,
    ManualFullDay,
    ManualCustomDay;

    static UpdateType getUpdateType() {
        DatabaseFile database = new DatabaseFile();
        Log.d("UpdateType", database.getUpdateType().name());
        return database.getUpdateType();
    }
}

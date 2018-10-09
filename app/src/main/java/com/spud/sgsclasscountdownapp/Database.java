package com.spud.sgsclasscountdownapp;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Stephen Ogden on 10/3/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
class Database {

    Timer timer = new Timer();

    @SuppressWarnings("FieldCanBeLocal")
    private final int DATABASE_VERSION = 1;
    @SuppressLint("SdCardPath")
    private File databaseFile = new File("/data/data/"+this.getClass().getPackage().getName()+"/database.txt");

    void createNewDatabase() {
        try {
            if (databaseFile.createNewFile()) {
                initialiseDatabase();
            } else {
                if (databaseFile.length() == 0) {
                    initialiseDatabase();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean is_a_thing() {
        Log.w("Database exists", Boolean.toString(databaseFile.exists() && databaseFile.canWrite() && databaseFile.canRead()));
        return databaseFile.exists() && databaseFile.canWrite() && databaseFile.canRead();
    }

    private void initialiseDatabase() {
        try {
            FileWriter writer = new FileWriter(databaseFile);
            writer.write(String.format("%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n",
                    databaseValues.DatabaseVersion.getName(), DATABASE_VERSION,
                    databaseValues.UpdateType.getName(), updateType.BuiltIn.name(),
                    databaseValues.ABlockClassName.getName(), "",
                    databaseValues.BBlockClassName.getName(), "",
                    databaseValues.CBlockClassName.getName(), "",
                    databaseValues.DBlockClassName.getName(), "",
                    databaseValues.EBlockClassName.getName(), "",
                    databaseValues.FBlockClassName.getName(), "",
                    databaseValues.GBlockClassName.getName(), "",
                    databaseValues.HBlockClassName.getName(), ""));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] readFromDatabase() {
        String data[] = new String[10];
        BufferedReader br = null;
        try {
            br = new BufferedReader (new FileReader (databaseFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
           for (int i = 0; i < data.length; i++) {
               assert br != null;
               // https://stackoverflow.com/questions/16285485/remove-characters-before-a-comma-in-a-string
               data[i] = br.readLine().replaceAll(".*:", "");
           }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    int getDatabaseVersion() {
        int version;
        version = Integer.parseInt(readFromDatabase()[0]);
        Log.i("Database version", Integer.toString(version));
        return version;
    }

    updateType getUpdateType() {
        updateType update = updateType.valueOf(readFromDatabase()[1]);
        Log.i("Update type", update.name());
        return update;
    }

    public enum databaseValues {
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
        databaseValues(String value) {
            this.value = value;
        }
        public String getName() {
            return value;
        }
    }

    public enum updateType {
        BuiltIn,
        Automatic,
        ManualADay,
        ManualEDay,
        ManualFullDay,
        ManualCustomDay
    }

    @SuppressWarnings("SameParameterValue")
    // TODO: Fix wiring the builtin value
    void writeToDatabase(int databaseVersion, updateType UpdateType, String aBlockClassName, String bBlockClassName, String cBlockClassName, String dBlockClassName, String eBlockClassName, String fBlockClassName, String gBlockClassName, String hBlockClassName) {
        try {
            FileWriter writer = new FileWriter(databaseFile);
            String data = String.format("%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n",
                    databaseValues.DatabaseVersion.getName(), databaseVersion,
                    databaseValues.UpdateType.getName(), UpdateType.name(),
                    databaseValues.ABlockClassName.getName(), aBlockClassName,
                    databaseValues.BBlockClassName.getName(), bBlockClassName,
                    databaseValues.CBlockClassName.getName(), cBlockClassName,
                    databaseValues.DBlockClassName.getName(), dBlockClassName,
                    databaseValues.EBlockClassName.getName(), eBlockClassName,
                    databaseValues.FBlockClassName.getName(), fBlockClassName,
                    databaseValues.GBlockClassName.getName(), gBlockClassName,
                    databaseValues.HBlockClassName.getName(), hBlockClassName);
            Log.i("Data",data);
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

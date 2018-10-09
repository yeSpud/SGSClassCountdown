package com.spud.sgsclasscountdownapp;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Stephen Ogden on 10/3/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
class Database {

    @SuppressWarnings("FieldCanBeLocal")
    private final int DATABASE_VERSION = 1;
    Timer timer = new Timer();
    @SuppressLint("SdCardPath")
    private File databaseFile = new File("/data/data/" + this.getClass().getPackage().getName() + "/database.txt");

    Database (){
        if (doesNotExist()) {
            createNewDatabase();
        }
    }

    private void createNewDatabase() {
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

    boolean doesNotExist() {
        Log.w("Database is missing", Boolean.toString(!(databaseFile.exists() && databaseFile.canWrite() && databaseFile.canRead())));
        return !(databaseFile.exists() && databaseFile.canWrite() && databaseFile.canRead());
    }

    void initialiseDatabase() {
        try {
            FileWriter writer = new FileWriter(databaseFile);
            writer.write(String.format("%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n",
                    DatabaseKeys.DatabaseVersion.getName(), DATABASE_VERSION,
                    DatabaseKeys.UpdateType.getName(), UpdateType.BuiltIn.name(),
                    DatabaseKeys.ABlockClassName.getName(), "",
                    DatabaseKeys.BBlockClassName.getName(), "",
                    DatabaseKeys.CBlockClassName.getName(), "",
                    DatabaseKeys.DBlockClassName.getName(), "",
                    DatabaseKeys.EBlockClassName.getName(), "",
                    DatabaseKeys.FBlockClassName.getName(), "",
                    DatabaseKeys.GBlockClassName.getName(), "",
                    DatabaseKeys.HBlockClassName.getName(), ""));
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
            br = new BufferedReader(new FileReader(databaseFile));
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

    UpdateType getUpdateType() {
        UpdateType update = UpdateType.valueOf(readFromDatabase()[1]);
        Log.i("Update type", update.name());
        return update;
    }

    void writeToDatabase(int databaseVersion, UpdateType UpdateType, String aBlockClassName, String bBlockClassName, String cBlockClassName, String dBlockClassName, String eBlockClassName, String fBlockClassName, String gBlockClassName, String hBlockClassName) {
        try {
            FileWriter writer = new FileWriter(databaseFile);
            String data = String.format("%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n%s:%s\n",
                    DatabaseKeys.DatabaseVersion.getName(), databaseVersion,
                    DatabaseKeys.UpdateType.getName(), UpdateType.name(),
                    DatabaseKeys.ABlockClassName.getName(), aBlockClassName,
                    DatabaseKeys.BBlockClassName.getName(), bBlockClassName,
                    DatabaseKeys.CBlockClassName.getName(), cBlockClassName,
                    DatabaseKeys.DBlockClassName.getName(), dBlockClassName,
                    DatabaseKeys.EBlockClassName.getName(), eBlockClassName,
                    DatabaseKeys.FBlockClassName.getName(), fBlockClassName,
                    DatabaseKeys.GBlockClassName.getName(), gBlockClassName,
                    DatabaseKeys.HBlockClassName.getName(), hBlockClassName);
            Log.i("Data", data);
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getBlockName(Block block) {

        // Load the database content
        String[] databaseContent = readFromDatabase();

        Log.i("Database content", Arrays.toString(databaseContent));

        // Go through each block provided, and return what that block is called in the database
        switch (block) {
            case ALong:
                return databaseContent[2];
            case BLong:
                return databaseContent[3];
            case CLong:
                return databaseContent[4];
            case DLong:
                return databaseContent[5];
            case ELong:
                return databaseContent[6];
            case FLong:
                return databaseContent[7];
            case GLong:
                return databaseContent[8];
            case HLong:
                return databaseContent[9];
            case ANormal:
                return databaseContent[2];
            case BNormal:
                return databaseContent[3];
            case CNormal:
                return databaseContent[4];
            case DNormal:
                return databaseContent[5];
            case ENormal:
                return databaseContent[6];
            case FNormal:
                return databaseContent[7];
            case GNormal:
                return databaseContent[8];
            case HNormal:
                return databaseContent[9];
            default:
                return "";
        }
    }
}

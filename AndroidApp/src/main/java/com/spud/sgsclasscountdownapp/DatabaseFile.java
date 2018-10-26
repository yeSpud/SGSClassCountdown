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
 * Created by Stephen Ogden on 10/22/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
class DatabaseFile {

    @SuppressWarnings("FieldCanBeLocal")
    private final int DATABASE_VERSION = 1;

    @SuppressLint("SdCardPath")
    private File databaseFile = new File("/data/data/" + this.getClass().getPackage().getName() + "/database.txt");

    DatabaseFile() {
        if (databaseDoesNotExist()) {
            createNewDatabase();
        }
    }

    private boolean databaseDoesNotExist() {
        boolean DNE = !(databaseFile.exists() && databaseFile.canWrite() && databaseFile.canRead());
        Log.w("Database missing", Boolean.toString(DNE));
        return DNE;
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

        // Check if the database is zero bytes in size
        if (databaseFile.length() <= 0) {
            initialiseDatabase();
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

    void writeToDatabase(int databaseVersion, UpdateType UpdateType, String aBlockClassName, String bBlockClassName, String cBlockClassName, String dBlockClassName, String eBlockClassName, String fBlockClassName, String gBlockClassName, String hBlockClassName) {

        // Check if the database is zero bytes in size
        if (databaseFile.length() <= 0) {
            initialiseDatabase();
        }

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

    String getBlockName(BlockNames blockName) {

        // Load the database content
        String[] databaseContent = readFromDatabase();

        Log.i("Database content", Arrays.toString(databaseContent));

        // Go through each blockType provided, and return what that blockType is called in the database
        switch (blockName) {
            case A:
                return databaseContent[2];
            case B:
                return databaseContent[3];
            case C:
                return databaseContent[4];
            case D:
                return databaseContent[5];
            case E:
                return databaseContent[6];
            case F:
                return databaseContent[7];
            case G:
                return databaseContent[8];
            case H:
                return databaseContent[9];
            default:
                return null;
        }
    }

    UpdateType getUpdateTypeFromDatabase() {
        UpdateType update = UpdateType.valueOf(readFromDatabase()[1]);
        Log.i("Update type", update.name());
        return update;
    }


}

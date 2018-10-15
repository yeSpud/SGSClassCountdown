package com.spud.sgsclasscountdownapp;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

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
class TimerFiles {

    @SuppressWarnings("FieldCanBeLocal")
    private final int DATABASE_VERSION = 1;
    Timer timer = new Timer();

    @SuppressLint("SdCardPath")
    private File databaseFile = new File("/data/data/" + this.getClass().getPackage().getName() + "/database.txt"),
            normalRegime = new File("/data/data/" + this.getClass().getPackage().getName() + "/BuiltinRegimes/Normal.json"),
            ARegime = new File("/data/data/" + this.getClass().getPackage().getName() + "/BuiltinRegimes/A.json"),
            ERegime = new File("/data/data/" + this.getClass().getPackage().getName() + "/BuiltinRegimes/E.json");

    TimerFiles() {
        if (databaseDoesNotExist()) {
            createNewDatabase();
        }
        if (builtinNormalRegimeDoesNotExist()) {
            createNormalRegime();
        }
        if (builtinARegimeDoesNotExist()) {
            createARegime();
        }
        if (builtinERegimeDoesNotExist()) {
            createERegime();
        }
    }

    private boolean databaseDoesNotExist() {
        boolean DNE = !(databaseFile.exists() && databaseFile.canWrite() && databaseFile.canRead());
        Log.w("Database missing", Boolean.toString(DNE));
        return DNE;
    }

    private boolean builtinNormalRegimeDoesNotExist() {
        boolean DNE = !(normalRegime.exists() && normalRegime.canRead());
        Log.w("Normal regime missing", Boolean.toString(DNE));
        return DNE;
    }

    private boolean builtinARegimeDoesNotExist() {
        boolean DNE = !(ARegime.exists() && ARegime.canRead());
        Log.w("A regime missing", Boolean.toString(!(ARegime.exists() && ARegime.canRead())));
        return DNE;
    }

    private boolean builtinERegimeDoesNotExist() {
        boolean DNE = !(ERegime.exists() && ERegime.canRead());
        Log.w("E regime missing", Boolean.toString(DNE));
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

    private void createNormalRegime() {
        try {
            if (normalRegime.createNewFile()) {
                writeNormalRegime();
            } else {
                if (normalRegime.length() == 0) {
                    writeNormalRegime();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeNormalRegime() {
        // TODO
    }

    private void createARegime() {
        try {
            if (ARegime.createNewFile()) {
                writeARegime();
            } else {
                if (ARegime.length() == 0) {
                    writeARegime();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeARegime() {
        // TODO
    }

    private void createERegime() {
        try {
            if (ERegime.createNewFile()) {
                writeERegime();
            } else {
                if (ERegime.length() == 0) {
                    writeERegime();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeERegime() {
        JSONObject FileContence = new JSONObject();

        JSONArray eBlockValue = new JSONArray();
        JSONObject eBlock = new JSONObject();
        JSONArray eBlockTimes = new JSONArray();
        eBlockTimes.put("8:20:00").put("9:45:00");
        try {
            eBlock.put("E block", eBlockTimes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eBlockValue.put(eBlock);

        JSONArray fBlockValue = new JSONArray();
        JSONObject fBlock = new JSONObject();
        JSONArray fBlockTimes = new JSONArray();
        fBlockTimes.put("10:00:00").put("11:25:00");
        try {
            fBlock.put("F block", fBlockTimes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        fBlockValue.put(fBlock);

        JSONArray LunchValue = new JSONArray();
        JSONObject Lunch = new JSONObject();
        JSONArray LunchTime = new JSONArray();
        LunchTime.put("11:25:00").put("12:00:00");
        try {
            Lunch.put("Lunch", LunchTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LunchValue.put(Lunch);

        JSONArray gBlockValue = new JSONArray();
        JSONObject gBlock = new JSONObject();
        JSONArray gBlockTimes = new JSONArray();
        gBlockTimes.put("12:05:00").put("13:30:00");
        try {
            gBlock.put("G block", gBlockTimes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        gBlockValue.put(gBlock);

        JSONArray hBlockValue = new JSONArray();
        JSONObject hBlock = new JSONObject();
        JSONArray hBlockTimes = new JSONArray();
        hBlockTimes.put("13:45:00").put("15:10:00");
        try {
            hBlock.put("H block", hBlockTimes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        hBlockValue.put(hBlock);

        JSONObject Version = new JSONObject();
        try {
            Version.put("Version", 1);
        } catch (JSONException e) {
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
        Log.i("TimerFiles version", Integer.toString(version));
        return version;
    }

    UpdateType getUpdateType() {
        UpdateType update = UpdateType.valueOf(readFromDatabase()[1]);
        Log.i("Update type", update.name());
        return update;
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

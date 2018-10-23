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
import java.util.Calendar;

/**
 * Created by Stephen Ogden on 10/3/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
class RegimeFiles {

    Timer timer = new Timer();

    @SuppressLint("SdCardPath")
    private File normalRegime = new File("/data/data/" + this.getClass().getPackage().getName() + "/Normal.json"),
            ARegime = new File("/data/data/" + this.getClass().getPackage().getName() + "/A.json"),
            ERegime = new File("/data/data/" + this.getClass().getPackage().getName() + "/E.json");

    RegimeFiles() {
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

    private boolean builtinNormalRegimeDoesNotExist() {
        boolean DNE = !(normalRegime.exists() && normalRegime.canRead());
        Log.w("Normal regime missing", Boolean.toString(DNE));
        createNormalRegime();
        return DNE;
    }

    private boolean builtinARegimeDoesNotExist() {
        boolean DNE = !(ARegime.exists() && ARegime.canRead());
        Log.w("A regime missing", Boolean.toString(!(ARegime.exists() && ARegime.canRead())));
        createARegime();
        return DNE;
    }

    private boolean builtinERegimeDoesNotExist() {
        boolean DNE = !(ERegime.exists() && ERegime.canRead());
        Log.w("E regime missing", Boolean.toString(DNE));
        createERegime();
        return DNE;
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
        try {
            JSONObject FileContence = new JSONObject();

            // Add the database version
            FileContence.put("Version", "1");

            Log.d("JSON", FileContence.toString());

            JSONObject fullRegime = new JSONObject();

            // Add A block, with its respective start and end times
            fullRegime.put(Block.ANormal.name(), new JSONArray().put(0, "8:20:00").put(1, "9:00:00"));
            // Add B block, with its respective start and end times
            fullRegime.put(Block.BNormal.name(), new JSONArray().put(0, "9:05:00").put(1, "9:45:00"));
            // Add C block, with its respective start and end times
            fullRegime.put(Block.CNormal.name(), new JSONArray().put(0, "10:00:00").put(1, "10:45:00"));
            // Add D block, with its respective start and end times
            fullRegime.put(Block.DNormal.name(), new JSONArray().put(0, "10:50:00").put(1, "11:30:00"));
            // Add E block, with its respective start and end times
            fullRegime.put(Block.ENormal.name(), new JSONArray().put(0, "11:35:00").put(1, "12:15:00"));
            // Add Lunch time, with its respective start and end times
            fullRegime.put(Block.LunchNormal.name(), new JSONArray().put(0, "12:15:00").put(1, "12:55:00"));
            // Add F block, with its respective start and end times
            fullRegime.put(Block.FNormal.name(), new JSONArray().put(0, "13:00:00").put(1, "13:40:00"));
            // Add G block, with its respective start and end times
            fullRegime.put(Block.GNormal.name(), new JSONArray().put(0, "13:45:00").put(1, "14:25:00"));
            // Add H block, with its respective start and end times
            fullRegime.put(Block.HNormal.name(), new JSONArray().put(0, "14:30:00").put(1, "15:10:00"));

            // Add all that crap to the json structure
            FileContence.put("Normal Regime", fullRegime);

            // Write the regime to the file
            try {
                FileWriter writer = new FileWriter(normalRegime);
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
        try {
            JSONObject FileContence = new JSONObject();

            // Add the database version
            FileContence.put("Version", "1");

            JSONObject aRegime = new JSONObject();

            // Add A block, with its respective start and end times
            aRegime.put(Block.ALong.name(), new JSONArray().put(0, "8:20:00").put(1, "9:45:00"));
            // Add A block, with its respective start and end times
            aRegime.put(Block.BLong.name(), new JSONArray().put(0, "10:00:00").put(1, "11:25:00"));
            // Add Lunch time, with its respective start and end times
            aRegime.put(Block.LunchLong.name(), new JSONArray().put(0, "11:25:00").put(1, "12:00:00"));
            // Add C block, with its respective start and end times
            aRegime.put(Block.CLong.name(), new JSONArray().put(0, "12:05:00").put(1, "13:30:00"));
            // Add D block, with its respective start and end times
            aRegime.put(Block.DLong.name(), new JSONArray().put(0, "13:45:00").put(1, "15:10:00"));

            // Add that crap to the jSON database
            FileContence.put("A Regime", aRegime);

            // Write it to the file
            try {
                FileWriter writer = new FileWriter(ARegime);
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
        try {
            JSONObject FileContence = new JSONObject();

            // Add the database version
            FileContence.put("Version", "1");


            JSONObject eRegime = new JSONObject();

            // Add E block, with its respective start and end times
            eRegime.put(Block.ELong.name(), new JSONArray().put(0, "8:20:00").put(1, "9:45:00"));
            // Add F block, with its respective start and end times
            eRegime.put(Block.FLong.name(), new JSONArray().put(0, "10:00:00").put(1, "11:25:00"));
            // Add Lunch time, with its respective start and end times
            eRegime.put(Block.LunchLong.name(), new JSONArray().put(0, "11:25:00").put(1, "12:00:00"));
            // Add G block, with its respective start and end times
            eRegime.put(Block.GLong.name(), new JSONArray().put(0, "12:05:00").put(1, "13:30:00"));
            // Add H block, with its respective start and end times
            eRegime.put(Block.HLong.name(), new JSONArray().put(0, "13:45:00").put(1, "15:10:00"));

            // Add that crap to the jSON database
            FileContence.put("E Regime", eRegime);

            // Write that to the file
            try {
                FileWriter writer = new FileWriter(ERegime);
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

    private JSONObject loadNormalRegime() {
        // https://stackoverflow.com/questions/13814503/reading-a-json-file-in-android
        JSONObject NormalRegime = null;

        InputStream inStream = null;
        try {
            inStream = new FileInputStream(normalRegime);
        } catch (FileNotFoundException e) {
            createNormalRegime();
            e.printStackTrace();
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
            NormalRegime = new JSONObject(new String(buffer, "UTF-8")).getJSONObject("Normal Regime");
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (NormalRegime != null) {
            Log.i("Returning NormalRegime", NormalRegime.toString());
        }
        return NormalRegime;
    }

    private JSONObject loadARegime() {
        // https://stackoverflow.com/questions/13814503/reading-a-json-file-in-android
        JSONObject aRegime = null;

        InputStream inStream = null;
        try {
            inStream = new FileInputStream(ARegime);
        } catch (FileNotFoundException e) {
            createNormalRegime();
            e.printStackTrace();
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
                Log.i("Stream", Integer.toString(read));
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
            aRegime = new JSONObject(new String(buffer, "UTF-8")).getJSONObject("A Regime");
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return aRegime;
    }

    private JSONObject loadERegime() {
        // https://stackoverflow.com/questions/13814503/reading-a-json-file-in-android
        JSONObject eRegime = null;

        InputStream inStream = null;
        try {
            inStream = new FileInputStream(ERegime);
        } catch (FileNotFoundException e) {
            createNormalRegime();
            e.printStackTrace();
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
                Log.i("Stream", Integer.toString(read));
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
            eRegime = new JSONObject(new String(buffer, "UTF-8")).getJSONObject("E Regime");
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return eRegime;
    }

    Block getBlockFromRegime(long currentTimeAsLong) {

        // Declare the return variable
        Block returnBlock = Block.NoBlock;

        // Create a database object
        DatabaseFile database = new DatabaseFile();

        // Create a jsonObject for getting jSon data
        JSONObject fullJson = null;

        // Create an array of all the blocks from the jsonFile
        ArrayList<JSONArray> blockTimes = new ArrayList<>();

        // Load the respective jSON file
        switch (database.getUpdateTypeFromDatabase()) {
            case ManualFullDay:
                fullJson = loadNormalRegime();
                break;
            case ManualADay:
                fullJson = loadARegime();
                break;
            case ManualEDay:
                fullJson = loadERegime();
                break;
            case BuiltIn:
                if (WeekType.getWeekType() == WeekType.Normal) {
                    fullJson = loadNormalRegime();
                } else if (WeekType.getWeekType() == WeekType.Long) {
                    fullJson = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY ? loadARegime() : loadERegime();
                } else //noinspection StatementWithEmptyBody
                    if (WeekType.getWeekType() == WeekType.Custom) {
                    // TODO: Add custom support
                }
                break;
            case ManualCustomDay:
                // TODO: Custom day
                break;
            case Automatic:
                // TODO: Automatic day
                break;
            default:
                fullJson = null;
                break;
        }

        // Load the block times int an array
        // https://stackoverflow.com/questions/30412603/get-jsonarray-key-name
        if (fullJson != null) {
            try {
                Log.d("Block names", fullJson.names().toString());
                for (int a = 0; a < fullJson.names().length(); a++) {
                    blockTimes.add(a, fullJson.getJSONArray(fullJson.names().getString(a)));
                }

                Log.d("Times for blocks", blockTimes.toString());

                Core conversion = new Core();

                // Check if the current time is within a block
                for (int k = 0; k < blockTimes.size(); k++) {
                    String[] startTimeString = blockTimes.get(k).getString(0).split(":");
                    String[] endTimeString = blockTimes.get(k).getString(1).split(":");
                    long startTime = conversion.timeToLong(Integer.parseInt(startTimeString[0]), Integer.parseInt(startTimeString[1]), Integer.parseInt(startTimeString[2]));
                    long endTime = conversion.timeToLong(Integer.parseInt(endTimeString[0]), Integer.parseInt(endTimeString[1]), Integer.parseInt(endTimeString[2]));

                    // Check if its within a block
                    // TODO: Fix it returning DNormal during lunch
                    if (currentTimeAsLong > startTime && currentTimeAsLong < endTime) {
                        // Success!
                        // Set the returned block, and then end the loop
                        returnBlock = Block.valueOf(String.valueOf(fullJson.names().get(blockTimes.lastIndexOf(blockTimes.get(k)))));
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.i("Returning block", returnBlock.name());
        return returnBlock;

    }

    // TODO: fix for overrides
    String[] getTimes(WeekType weekType, Block block) {
        String[] returnString = new String[2];
        JSONObject regime;
        JSONArray times;
        try {

            // Get from database in case of overrides
            DatabaseFile database = new DatabaseFile();

            switch (weekType) {
                case Normal:
                    regime = loadNormalRegime();
                    Log.d("Regime", regime.toString());

                    times = regime.getJSONArray(block.name());

                    if (times != null) {
                        returnString[0] = times.getString(0);
                        returnString[1] = times.getString(1);
                    }
                    break;
                case Long:
                    if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY || database.getUpdateTypeFromDatabase().equals(UpdateType.ManualADay)) {
                        regime = loadARegime();
                        times = regime.getJSONArray(block.name());

                        if (times != null) {
                            returnString[0] = times.getString(0);
                            returnString[1] = times.getString(1);
                        }

                    } else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY || database.getUpdateTypeFromDatabase().equals(UpdateType.ManualEDay)){

                        // Load the E regime
                        regime = loadERegime();

                        times = regime.getJSONArray(block.name());
                        if (times != null) {
                            returnString[0] = times.getString(0);
                            returnString[1] = times.getString(1);
                        }
                    }
                case Custom:
                    // TODO: Add custom case
                    break;

            }
        } catch (JSONException jSONError) {
            jSONError.printStackTrace();
        }
        return returnString;
    }
}

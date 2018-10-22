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
        JSONObject FileContence = new JSONObject();
        try {
            FileContence.put("Version", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("JSON", FileContence.toString());

        JSONObject fullRegime = new JSONObject();

        try {
            fullRegime.put(Block.ANormal.name(), new JSONArray().put(0, "8:20:00").put(1, "9:00:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            fullRegime.put(Block.BNormal.name(), new JSONArray().put(0, "9:05:00").put(1, "9:45:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            fullRegime.put(Block.CNormal.name(), new JSONArray().put(0, "10:00:00").put(1, "10:45:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            fullRegime.put(Block.DNormal.name(), new JSONArray().put(0, "10:50:00").put(1, "11:30:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            fullRegime.put(Block.ENormal.name(), new JSONArray().put(0, "11:35:00").put(1, "12:15:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            fullRegime.put(Block.LunchNormal.name(), new JSONArray().put(0, "12:15:00").put(1, "12:55:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            fullRegime.put(Block.FNormal.name(), new JSONArray().put(0, "13:00:00").put(1, "13:40:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            fullRegime.put(Block.GNormal.name(), new JSONArray().put(0, "13:45:00").put(1, "14:25:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            fullRegime.put(Block.HNormal.name(), new JSONArray().put(0, "14:30:00").put(1, "15:10:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            FileContence.put("Normal Regime", fullRegime);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FileWriter writer = null;
        try {
            writer = new FileWriter(normalRegime);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            assert writer != null;
            writer.write(FileContence.toString(4));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        JSONObject FileContence = new JSONObject();
        try {
            FileContence.put("Version", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject aRegime = new JSONObject();

        try {
            aRegime.put(Block.ALong.name(), new JSONArray().put(0, "8:20:00").put(1, "9:45:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            aRegime.put(Block.BLong.name(), new JSONArray().put(0, "10:00:00").put(1, "11:25:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            aRegime.put(Block.LunchLong.name(), new JSONArray().put(0, "11:25:00").put(1, "12:00:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            aRegime.put(Block.CLong.name(), new JSONArray().put(0, "12:05:00").put(1, "13:30:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            aRegime.put(Block.DLong.name(), new JSONArray().put(0, "13:45:00").put(1, "15:10:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            FileContence.put("A Regime", aRegime);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FileWriter writer = null;
        try {
            writer = new FileWriter(ARegime);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert writer != null;
            writer.write(FileContence.toString(4));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeERegime() {
        JSONObject FileContence = new JSONObject();
        try {
            FileContence.put("Version", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject eRegime = new JSONObject();

        try {
            eRegime.put(Block.ELong.name(), new JSONArray().put(0, "8:20:00").put(1, "9:45:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            eRegime.put(Block.FLong.name(), new JSONArray().put(0, "10:00:00").put(1, "11:25:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            eRegime.put(Block.LunchLong.name(), new JSONArray().put(0, "11:25:00").put(1, "12:00:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            eRegime.put(Block.GLong.name(), new JSONArray().put(0, "12:05:00").put(1, "13:30:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            eRegime.put(Block.HLong.name(), new JSONArray().put(0, "13:45:00").put(1, "15:10:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            FileContence.put("E Regime", eRegime);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FileWriter writer = null;
        try {
            writer = new FileWriter(ERegime);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert writer != null;
            writer.write(FileContence.toString(4));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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

        Block returnBlock = Block.NoBlock;

        // Create a database object
        DatabaseFile database = new DatabaseFile();

        // Create a jsonObject for getting jSon data
        JSONObject fullJson = null;

        // Create an array of all the blocks from the jsonFile
        ArrayList<JSONArray> blockTimes = new ArrayList<>();

        // Load the respective jSON file
        switch (database.getUpdateType()) {
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
                switch (WeekType.getWeekType()) {
                    case Normal:
                        fullJson = loadNormalRegime();
                        break;
                    case Long:
                        fullJson = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY ? loadARegime() : loadERegime();
                        break;
                }
                break;
            case ManualCustomDay:
                // TODO: Custom day
                break;
            case Automatic:
                // TODO: Automatic day
                break;
        }

        // Load the block times int an array
        // https://stackoverflow.com/questions/30412603/get-jsonarray-key-name
        try {
            Log.d("Block names", fullJson != null ? fullJson.names().toString() : null);
            for (int a = 0; a < (fullJson != null ? fullJson.names().length() : 0); a++) {
                blockTimes.add(a, fullJson.getJSONArray(fullJson.names().getString(a)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Times for blocks", blockTimes.toString());

        Core conversion = new Core();

        // Check if the current time is within a block
        for (int k = 0; k < blockTimes.size(); k++) {
            long startTime = 0;
            long endTime = 0;
            try {
                String[] startTimeString = blockTimes.get(k).getString(0).split(":");
                String[] endTimeString = blockTimes.get(k).getString(1).split(":");
                startTime = conversion.timeToLong(Integer.parseInt(startTimeString[0]), Integer.parseInt(startTimeString[1]), Integer.parseInt(startTimeString[2]));
                endTime = conversion.timeToLong(Integer.parseInt(endTimeString[0]), Integer.parseInt(endTimeString[1]), Integer.parseInt(endTimeString[2]));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Check if its within a block
            // TODO: Fix it returning DNormal during lunch
            if (currentTimeAsLong > startTime && currentTimeAsLong < endTime) {
                // Success
                try {
                    // Set the returned block
                    returnBlock = Block.valueOf(String.valueOf(fullJson != null ? fullJson.names().get(blockTimes.lastIndexOf(blockTimes.get(k))) : null));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }

        }

        Log.i("Returning block", returnBlock.name());
        return returnBlock;

    }

    String[] getTimes(WeekType weekType, Block block) {
        String[] returnString = new String[2];
        JSONObject regime;
        JSONArray times = null;
        switch (weekType) {
            case Normal:
                regime = loadARegime();
                Log.d("regime", regime.toString());
                try {
                    times = regime.getJSONArray(block.name()); // TODO: Error here
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (times != null) {
                        returnString[0] = times.getString(0);
                        returnString[1] = times.getString(1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Long:
                switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                    case Calendar.WEDNESDAY:
                        regime = loadARegime();
                        try {
                            times = regime.getJSONArray(block.name());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (times != null) {
                                returnString[0] = times.getString(0);
                                returnString[1] = times.getString(1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case Calendar.THURSDAY:
                        regime = loadERegime();
                        try {
                            times = regime.getJSONArray(block.name());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (times != null) {
                                returnString[0] = times.getString(0);
                                returnString[1] = times.getString(1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                break;
            case Custom:
                // TODO: Add custom case
                break;

        }
        return returnString;
    }

}

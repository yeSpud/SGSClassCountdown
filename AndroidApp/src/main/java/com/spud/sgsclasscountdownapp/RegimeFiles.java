package com.spud.sgsclasscountdownapp;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

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

        Log.e("JSON", FileContence.toString());

        JSONObject fullRegime = new JSONObject();

        try {
            fullRegime.put("A block", new JSONArray().put(0, "8:20:00").put(1, "9:00:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            fullRegime.put("B block", new JSONArray().put(0, "9:05:00").put(1, "9:45:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            fullRegime.put("C block", new JSONArray().put(0, "10:00:00").put(1, "10:45:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            fullRegime.put("D block", new JSONArray().put(0, "10:50:00").put(1, "11:30:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            fullRegime.put("E block", new JSONArray().put(0, "11:35:00").put(1, "12:15:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            fullRegime.put("Lunch", new JSONArray().put(0, "12:15:00").put(1, "12:55:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            fullRegime.put("F block", new JSONArray().put(0, "13:00:00").put(1, "13:40:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            fullRegime.put("G block", new JSONArray().put(0, "13:45:00").put(1, "14:25:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            fullRegime.put("H block", new JSONArray().put(0, "14:30:00").put(1, "15:10:00"));
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
            aRegime.put("A block", new JSONArray().put(0, "8:20:00").put(1, "9:45:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            aRegime.put("B block", new JSONArray().put(0, "10:00:00").put(1, "11:25:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            aRegime.put("Lunch", new JSONArray().put(0, "11:25:00").put(1, "12:00:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            aRegime.put("C block", new JSONArray().put(0, "12:05:00").put(1, "13:30:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            aRegime.put("D block", new JSONArray().put(0, "13:45:00").put(1, "15:10:00"));
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
            eRegime.put("E block", new JSONArray().put(0, "8:20:00").put(1, "9:45:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            eRegime.put("F block", new JSONArray().put(0, "10:00:00").put(1, "11:25:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            eRegime.put("Lunch", new JSONArray().put(0, "11:25:00").put(1, "12:00:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            eRegime.put("G block", new JSONArray().put(0, "12:05:00").put(1, "13:30:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            eRegime.put("H block", new JSONArray().put(0, "13:45:00").put(1, "15:10:00"));
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
            NormalRegime = new JSONObject(new String(buffer, "UTF-8"));
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
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
            aRegime = new JSONObject(new String(buffer, "UTF-8"));
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
            eRegime = new JSONObject(new String(buffer, "UTF-8"));
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return eRegime;
    }

    Block getBlock(long currentTimeAsLong) {

        Block returnBlock = Block.NoBlock;

        // Create a database object
        DatabaseFile database = new DatabaseFile();

        // Create a jsonObject for getting jSon data
        JSONObject fullJson = null;

        // Create an array of all the blocks from the jsonFile
        ArrayList <JSONObject> block = new ArrayList<>();

        // Load the respective jSON file
        Log.w("Database update", database.getUpdateType().name());
        switch (database.getUpdateType()) {
            case ManualFullDay:
                fullJson = loadNormalRegime();
                try {
                    block.add(fullJson.getJSONObject("A block")); // TODO: Error here: org.json.JSONException: Value ["8:20:00","9:00:00"] at A block of type org.json.JSONArray cannot be converted to JSONObject
                    block.add(fullJson.getJSONObject("B block"));
                    block.add(fullJson.getJSONObject("C block"));
                    block.add(fullJson.getJSONObject("D block"));
                    block.add(fullJson.getJSONObject("E block"));
                    block.add(fullJson.getJSONObject("Lunch"));
                    block.add(fullJson.getJSONObject("F block"));
                    block.add(fullJson.getJSONObject("G block"));
                    block.add(fullJson.getJSONObject("H block"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.w("Size of block", Integer.toString(block.size()));
                break;
            case ManualADay:
                fullJson = loadARegime();
                break;
            case ManualEDay:
                fullJson = loadERegime();
                break;
            case BuiltIn:
                // TODO: Built-in
                break;
            case ManualCustomDay:
                // TODO: Custom day
                break;
            case Automatic:
                // TODO: Automatic day
                break;
        }

        ArrayList <JSONArray> blockTimes = new ArrayList<>();

        // Load the start and end times of each block into blockTimes
        for (int i = 0; i < block.size(); i++) {
            blockTimes.add(new JSONArray().put(block.get(i).names()));
            Log.i("Block times", block.get(i).names().toString());
        }
        Log.i("Times for blocks", blockTimes.toString());

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
            if (currentTimeAsLong > startTime && currentTimeAsLong < endTime) {
                // Success
                // TODO: Change to the new block
            }
        }

        return returnBlock;

    }

}

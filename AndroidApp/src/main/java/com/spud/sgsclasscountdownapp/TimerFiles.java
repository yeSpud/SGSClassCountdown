package com.spud.sgsclasscountdownapp;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Stephen Ogden on 10/3/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
class TimerFiles {

    Timer timer = new Timer();

    @SuppressLint("SdCardPath")
    private File normalRegime = new File("/data/data/" + this.getClass().getPackage().getName() + "/Normal.json"),
            ARegime = new File("/data/data/" + this.getClass().getPackage().getName() + "/A.json"),
            ERegime = new File("/data/data/" + this.getClass().getPackage().getName() + "/E.json");

    TimerFiles() {
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
            writer.write(fullRegime.toString(4));
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

}

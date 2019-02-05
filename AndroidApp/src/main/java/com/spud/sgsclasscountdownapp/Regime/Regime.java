package com.spud.sgsclasscountdownapp.Regime;

import com.spud.sgsclasscountdownapp.Activities.Main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Stephen Ogden on 2/4/19.
 */
public class Regime {

    private String name;

    private int[] dateOccurrence;

    private Class[] classes;

    public Regime(String name, int[] day, Class... classes) {
        this.name = name;
        this.dateOccurrence = day;
        this.classes = classes;
    }

    public static Regime loadRegime(String name) throws FileNotFoundException {
        JSONObject json = Regime.readJsonFile(new File(Main.dir + name));

        // Get the name
        String regimeName = json.optString("name");

        // Get the applicable dates
        JSONArray dateArray = json.optJSONArray("dates");
        int[] dates = new int[dateArray.length()];
        for (int i = 0; i < dateArray.length(); i++) {
            try {
                dates[i] = dateArray.getInt(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Get the classes
        JSONArray classArray = json.optJSONArray("classes");
        Class[] classes = new Class[classArray.length()];
        for (int i = 0; i < classArray.length(); i++) {
            try {
                JSONObject ass /* hehe */ = classArray.getJSONObject(i);
                String className = ass.getString("name"), customName = ass.getString("custom name");
                long startTime = Long.parseLong(ass.getString("start time")), endTime = Long.parseLong(ass.getString("end time"));
                classes[i] = new Class(className, startTime, endTime, customName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return new Regime(regimeName, dates, classes);

    }

    private static JSONObject readJsonFile(File file) throws FileNotFoundException {
        // https://stackoverflow.com/questions/13814503/reading-a-json-file-in-android
        java.io.InputStream inStream = new java.io.FileInputStream(file);
        try {
            int size = inStream.available();
            byte[] buffer = new byte[size];
            inStream.read(buffer);
            inStream.close();
            return new JSONObject(new String(buffer, java.nio.charset.StandardCharsets.UTF_8));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            throw new FileNotFoundException();
        }
    }

    public String getName() {
        return this.name;
    }

    public Class[] getClasses() {
        return this.classes;
    }

    public int getClassCount() {
        return this.classes.length;
    }

    public Class getClass(int index) throws IndexOutOfBoundsException {
        if (index >= this.getClassCount()) {
            throw new IndexOutOfBoundsException();
        } else {
            return this.getClasses()[index];
        }
    }

    private int[] getDateOccurence() {
        return this.dateOccurrence;
    }

    public void saveRegime() throws JSONException {
        // For each class in the regime, create a JSON object for the name, start, and end times
        JSONArray classArray = new JSONArray();
        for (Class ass /* lol */ : this.getClasses()) {
            JSONObject butt = new JSONObject();
            butt.putOpt("name", ass.getName(false))
                    .putOpt("start time", Long.toString(ass.getStartTime()))
                    .putOpt("end time", Long.toString(ass.getEndTime()))
                    .putOpt("custom name", ass.getName(true));
            classArray.put(butt);
        }

        // Get the dates that occur
        JSONArray dates = new JSONArray();
        for (int date : this.getDateOccurence()) {
            dates.put(date);
        }

        // Get the name, and then add all that stuff to a large json object
        JSONObject json = new JSONObject();
        json.putOpt("name", this.getName());
        json.putOpt("dates", dates);
        json.putOpt("classes", classArray);

        try {
            java.io.FileWriter writer = new java.io.FileWriter(new File(Main.dir + this.getName()));
            writer.write(json.toString(4));
            writer.flush();
            writer.close();
            // TODO: Save name of file to an overall directory for lookup later
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

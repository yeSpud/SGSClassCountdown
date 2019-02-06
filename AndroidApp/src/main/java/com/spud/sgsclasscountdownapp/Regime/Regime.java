package com.spud.sgsclasscountdownapp.Regime;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;

/**
 * Created by Stephen Ogden on 2/4/19.
 */
public class Regime {

    public static File regimeDatabase = new File(com.spud.sgsclasscountdownapp.Activities.Main.dir.getAbsolutePath() + "regimes.db");
    private String name;
    private int[] dateOccurrence;

    private Class[] classes;

    public Regime(String name, int[] day, Class... classes) {
        this.name = name;
        this.dateOccurrence = day;
        this.classes = classes;
    }

    public static Regime loadRegime(int day) {
        // Open a connection the database
        SQLiteDatabase database = SQLiteDatabase.openDatabase(Regime.regimeDatabase.getAbsolutePath(), null, 0x0000);
        Cursor result = database.rawQuery("SELECT * FROM regimes WHERE occurrence LIKE '" + day + "';", null);

        // Get the name of the regime
        String name = "";
        if (result.moveToFirst()) {
            name = result.getString(result.getColumnIndex("name"));
        } else {
            result.close();
            database.close();
            return null;
        }

        // Get the applicable dates
        int[] dates;
        if (result.moveToFirst()) {
            String[] dateResult;
            dateResult = result.getString(result.getColumnIndex("occurrence")).split(",");
            dates = new int[dateResult.length];
            for (int i = 0; i < dateResult.length; i++) {
                dates[i] = Integer.parseInt(dateResult[i]);
            }
        } else {
            result.close();
            database.close();
            return null;
        }

        // Get the classes
        Class[] classes;
        if (result.moveToFirst()) {
            try {
                JSONArray classArray = new JSONArray(result.getString(result.getColumnIndex("classes")));
                classes = new Class[classArray.length()];
                for (int i = 0; i < classArray.length(); i++) {
                    JSONObject object = classArray.getJSONObject(i);
                    Class a = new Class(object.optString("name"),
                            Long.parseLong(object.optString("start time")),
                            Long.parseLong(object.optString("end time")),
                            object.optString("custom name"));
                    classes[i] = a;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                result.close();
                database.close();
                return null;
            }
        } else {
            result.close();
            database.close();
            return null;
        }

        result.close();
        database.close();
        return new Regime(name, dates, classes);
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

        String classes = classArray.toString();

        // Get the dates in which this occues as a string
        String occurrence = Arrays.toString(this.getDateOccurence());

        // Put this in the regime database
        SQLiteDatabase database = SQLiteDatabase.openDatabase(Regime.regimeDatabase.getAbsolutePath(), null, 0x0000);

        // First, check if the regime is already in the database
        Cursor result = database.rawQuery("SELECT * FROM regimes WHERE name = " + this.getName(), null);
        if (result.getCount() > 0) {
            // Update the data
            database.execSQL("UPDATE regimes SET occurrence = '" + occurrence + "', SET classes = '" + classes + "';");
        } else {
            // Add the regime
            database.execSQL("INSERT INTO regimes (name, occurrence, classes, override) VALUES ('" + this.getName() + "', '" + occurrence + "', '" + classes + "', 'false');");
        }
        result.close();
        database.close();
    }
}

package com.spud.sgsclasscountdownapp.Regime;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;

/**
 * Created by Stephen Ogden on 2/4/19.
 */
public class Regime {

	/**
	 * The regime database file.
	 */
	public static File regimeDatabase = new File(com.spud.sgsclasscountdownapp.Activities.Main.dir.getAbsolutePath() + "regimes.db");

	private String name;
	private int[] dateOccurrence;
	private Class[] classes;

	/**
	 * Regime object constructor.
	 *
	 * @param name    The name of the regime.
	 * @param day     The days in which this regime occurs. This is an array of ints,
	 *                which is determined by an array of Calendar constants, such as
	 *                <code>Calendar.MONDAY</code>
	 * @param classes The classes in the regime.
	 */
	public Regime(String name, int[] day, Class... classes) {
		this.name = name;
		this.dateOccurrence = day;
		this.classes = classes;
	}

	/**
	 * A helper function that loads the regime depending on the day constant that was provided.
	 * Will return null if no regime exists, or if it errors while loading.
	 *
	 * @param day The day constant. Use an argument such as <code>Calendar.MONDAY</code>.
	 * @return A regime object, or null of none exists (or an error occurs).
	 */
	public static Regime loadRegime(int day) {
		// Open a connection the database
		SQLiteDatabase database = SQLiteDatabase.openDatabase(Regime.regimeDatabase.getAbsolutePath(),
				null, 0x0000);
		Cursor result = database.rawQuery("SELECT name, occurrence, classes FROM regimes WHERE occurrence LIKE \"%" +
				day + "%\";", null);

		Log.d("Regime", String.format("Found %d matching format(s)", result.getCount()));

		if (result.moveToFirst()) {
			// Get the name of the regime
			String name = result.getString(result.getColumnIndex("name"));
			Log.d("Regime", "Name of loaded regime: " + name);

			// Get the applicable dates
			int[] dates = Regime.parseDates(result.getString(result.getColumnIndex("occurrence")));

			// Get the classes
			Class[] classes = Regime.parseClasses(result.getString(result.getColumnIndex("classes")));

			result.close();
			database.close();
			return new Regime(name, dates, classes);
		} else {
			result.close();
			database.close();
			return null;
		}
	}

	/**
	 * TODO
	 *
	 * @param datesFromDB
	 * @return
	 */
	public static int[] parseDates(String datesFromDB) {
		String[] dateResult = datesFromDB.replace("[", "").replace("]", "").replace(" ", "").split(",");
		int[] dates = new int[dateResult.length];
		for (int i = 0; i < dateResult.length; i++) {
			dates[i] = Integer.parseInt(dateResult[i]);
		}
		return dates;
	}

	/**
	 * TODO
	 *
	 * @param classesFromDB
	 * @return
	 */
	public static Class[] parseClasses(String classesFromDB) {
		try {
			JSONArray classArray = new JSONArray(classesFromDB);
			Class[] classes = new Class[classArray.length()];
			for (int i = 0; i < classArray.length(); i++) {
				JSONObject object = classArray.getJSONObject(i);
				Class a = new Class(object.optString("name"),
						Long.parseLong(object.optString("start time")),
						Long.parseLong(object.optString("end time")),
						object.optString("custom name"));
				classes[i] = a;
			}
			return classes;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static SQLiteDatabase getDatabase() {
		return SQLiteDatabase.openDatabase(Regime.regimeDatabase.getAbsolutePath(),
				null, 0x0000);
	}

	/**
	 * Gets the name of the regime.
	 *
	 * @return The name of the regime (as a string).
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the classes in the regime.
	 *
	 * @return The classes in the regime (as an array of classes).
	 */
	public Class[] getClasses() {
		return this.classes;
	}

	/**
	 * Returns the number of classes in the regime.
	 *
	 * @return The number of classes in the regime (as an int).
	 */
	public int getClassCount() {
		return this.classes.length;
	}

	/**
	 * Get the class at the current index of the regime.
	 *
	 * @param index The class index in the regime.
	 * @return The class.
	 * @throws IndexOutOfBoundsException Thrown if the index is larger than the number of classes in the regime.
	 */
	public Class getClass(int index) throws IndexOutOfBoundsException {
		if (index >= this.getClassCount()) {
			throw new IndexOutOfBoundsException();
		} else {
			return this.getClasses()[index];
		}
	}

	/**
	 * Gets the dates in which this regime occurs.
	 *
	 * @return The dates (as an int for Calender constants) in which this regime occurs.
	 */
	public int[] getDateOccurrence() {
		return this.dateOccurrence;
	}

	/**
	 * Saves the regime to the database.
	 *
	 * @throws JSONException Thrown if there was an error when parsing the class data.
	 */
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

		// Get the dates in which this occurs as a string
		String occurrence = Arrays.toString(this.getDateOccurrence());

		// Put this in the regime database
		SQLiteDatabase database = Regime.getDatabase();

		// First, check if the regime is already in the database
		Cursor result = database.rawQuery("SELECT * FROM regimes WHERE name = \"" + this.getName() + "\";",
				null);
		if (result.getCount() > 0) {
			// Update the data
			database.execSQL("UPDATE regimes SET occurrence = \"" + occurrence + "\", SET classes = \"" +
					classArray.toString() + " WHERE name = " + this.getName() + ";");
		} else {
			// Add the regime
			database.execSQL("INSERT INTO regimes (name, occurrence, classes, override) VALUES ('" +
					this.getName() + "', '" + occurrence + "', '" + classArray.toString() + "', 'false');");
		}
		result.close();
		database.close();
	}

	/**
	 * TODO
	 */
	public void removeRegime() {
		SQLiteDatabase database = Regime.getDatabase();
		database.execSQL("DELETE FROM regimes WHERE name = \"" + this.name + "\";");
		database.close();
	}
}

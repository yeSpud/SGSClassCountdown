package com.spud.sgsclasscountdownapp.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.spud.sgsclasscountdownapp.R;
import com.spud.sgsclasscountdownapp.Regime.Regime;
import com.spud.sgsclasscountdownapp.Timer;

import java.util.Calendar;

public class Main extends android.support.v7.app.AppCompatActivity {

	public static java.io.File dir;

	public static Regime currentRegeme;

	private static Timer timer = new Timer();

	private android.widget.TextView text, countdown;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set the view to the main XML file
		this.setContentView(R.layout.main);

		// Set the file directory for the app
		Main.dir = this.getFilesDir();

		// Setup the text on the main page
		this.text = this.findViewById(R.id.text);
		this.countdown = this.findViewById(R.id.countdown);

		// Setup the Settings button
		this.findViewById(R.id.gotoSettings).setOnClickListener((event) -> this.startActivity(new android.content.Intent(Main.this, Settings.class)));

		// Enable the timer
		Main.timer.enable = true;


		// Run the timer
		// Main.timer.run();

	}

	protected void onPause() {
		super.onPause();

		// Pause the timer
		Main.timer.enable = false;
	}

	protected void onResume() {
		super.onResume();

		// Get which ever regime is selected (from file)
		// If none is selected, update the view telling the user to create one in settings
		if (Regime.regimeDatabase.exists()) {
			Main.currentRegeme = Regime.loadRegime(this.getOverride());
			if (Main.currentRegeme == null) {
				this.countdown.setVisibility(View.GONE);
				this.text.setText("There doesn't seem to be a schedule for today.\nPlease create a new schedule in settings");
				return;
			}
		} else {
			try {
				if (!Regime.regimeDatabase.createNewFile()) {
					Toast.makeText(this, "An unknown error occurred (-1).\nPlease restart the app", Toast.LENGTH_LONG).show();
					return;
				}
				android.database.sqlite.SQLiteDatabase database = this.openOrCreateDatabase(Regime.regimeDatabase.getAbsolutePath(), MODE_PRIVATE, null);
				database.execSQL("CREATE TABLE regimes (name TEXT NOT NULL UNIQUE, occurrence TEXT NOT NULL UNIQUE, classes TEXT NOT NULL, override TEXT NOT NULL)");
				database.close();

				this.countdown.setVisibility(View.GONE);
				this.text.setText("There doesn't seem to be a schedule for today.\nPlease create a new schedule in settings");
			} catch (java.io.IOException e) {
				Toast.makeText(this, "An unknown error occurred (-2).\nPlease restart the app", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
			return;
		}

		// Resume the timer
		Main.timer.enable = true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Stop the timer
		Main.timer.enable = false;
	}

	public void updateTime(com.spud.sgsclasscountdownapp.Regime.Class currentClass, String time) {
		String header = currentClass.hasCustomName() ? String.format("%s (%s) will be over in:",
				currentClass.getName(false), currentClass.getName(true)) :
				currentClass.getName(false) + " will be over in:";
		this.text.setText(header);
		this.countdown.setText(time);
	}

	private int getOverride() {
		// Check for an override from the database
		SQLiteDatabase database = Regime.getDatabase();
		Cursor result = database.rawQuery("SELECT name, occurrence FROM regimes WHERE override = \"true\";", null);

		// If there is no result, return the day of the week
		if (result.getCount() == 0 || !result.moveToFirst()) {
			Log.d("Override name", "No override");
			return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		}

		Log.d("Override name", result.getString(result.getColumnIndex("name")));

		String o = result.getString(result.getColumnIndex("occurrence"));

		result.close();
		database.close();

		String[] arr = o.replace("[", "").replace("]", "").replace(" ", "").split(",");

		// Return the first occurrence value
		return Integer.parseInt(arr[0]);
	}
}

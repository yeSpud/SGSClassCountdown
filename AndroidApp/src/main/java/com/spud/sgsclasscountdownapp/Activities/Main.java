package com.spud.sgsclasscountdownapp.Activities;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.spud.sgsclasscountdownapp.R;
import com.spud.sgsclasscountdownapp.Regime.Class;
import com.spud.sgsclasscountdownapp.Regime.Regime;
import com.spud.sgsclasscountdownapp.Timer;

import java.util.Calendar;

public class Main extends android.support.v7.app.AppCompatActivity {

	public static java.io.File dir;

	private android.widget.TextView text, countdown;

	private volatile boolean r = false;

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

	}

	protected void onPause() {
		super.onPause();

		this.r = false;
	}

	protected void onResume() {
		super.onResume();

		// Get which ever regime is selected (from file)
		// If none is selected, update the view telling the user to create one in settings
		if (Regime.regimeDatabase.exists()) {

			this.r = true;

			// Start a timer
			this.timer().start();
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
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		this.r = false;
	}

	private String getOverride() {
		// Check for an override from the database
		android.database.sqlite.SQLiteDatabase database = Regime.getDatabase();
		android.database.Cursor result = database.rawQuery("SELECT name FROM regimes WHERE override = \"true\";", null);

		// If there is no result, return the day of the week
		if (result.getCount() == 0 || !result.moveToFirst()) {
			Log.d("Override name", "No override");
			return null;
		}

		Log.d("Override name", result.getString(result.getColumnIndex("name")));

		String o = result.getString(result.getColumnIndex("name"));

		result.close();
		database.close();

		// Return the name of the override
		return o;
	}

	private Thread timer() {
		Thread t = new Thread(() -> {
			Log.w("Timer", "Starting up...");

			while (r && !Thread.interrupted()) {

				// Load a regime
				Regime currentRegime;

				// Check for override
				String override = this.getOverride();
				if (override != null) {
					currentRegime = Regime.loadRegime(this.getOverride());
				} else {
					currentRegime = Regime.loadRegime(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
				}

				
				if (currentRegime != null) {

					// Figure out if there is a class right now
					Class currentClass = Class.getClass(currentRegime, Timer.getCurrentTime());
					if (currentClass != null) {

						// Count down how long it will be until its over
						String header = currentClass.hasCustomName() ? String.format("%s (%s) will be over in:",
								currentClass.getName(false), currentClass.getName(true)) :
								currentClass.getName(false) + " will be over in:",
								remaining = Timer.formatTimeRemaining(Timer.getTimeRemaining(currentClass));

						this.runOnUiThread(() -> {
							this.text.setText(header);
							this.countdown.setVisibility(View.VISIBLE);
							this.countdown.setText(remaining);
						});

					} else {

						// If there isn't, display a message
						this.runOnUiThread(() -> {
							this.countdown.setVisibility(View.GONE);
							this.text.setText("No class currently.");
						});
					}
				} else {

					// No regime currently loaded
					this.runOnUiThread(() -> {
						this.countdown.setVisibility(View.GONE);
						this.text.setText("There doesn't seem to be a schedule for today.\nPlease create a new schedule in settings");
					});
				}

				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Thread.yield();
				Log.d("Timer", "Looping...");
			}

			Log.w("Timer", "Shutting down...");
		});
		t.setName("Timer");
		return t;
	}
}

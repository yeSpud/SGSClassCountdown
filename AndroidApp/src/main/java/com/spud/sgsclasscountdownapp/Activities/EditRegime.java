package com.spud.sgsclasscountdownapp.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.spud.sgsclasscountdownapp.R;
import com.spud.sgsclasscountdownapp.Regime.Regime;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Stephen Ogden on 2/6/19.
 */
public class EditRegime extends android.support.v7.app.AppCompatActivity {

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.editregime);

		// Linear layout to house all the regimes
		LinearLayout view = this.findViewById(R.id.regimesList);

		SQLiteDatabase database = SQLiteDatabase.openDatabase(Regime.regimeDatabase.getAbsolutePath(),
				null, 0x0000);

		// Get all the regimes from the database
		Cursor result = database.rawQuery("SELECT * FROM regimes;", null);

		// Change the display message to the regimes that were retrieved.
		view.findViewById(R.id.nothingEntered).setVisibility(result.getCount() != 0 ? View.GONE : View.VISIBLE);

		// Move the cursor to the first row
		if (result.moveToFirst()) {
			for (int i = 0; i < result.getCount(); i++) {
				view.addView(this.generateRegimeView(new Regime(result.getString(result.getColumnIndex("name")),
						Regime.parseDates(result.getString(result.getColumnIndex("occurrence"))),
						Regime.parseClasses(result.getString(result.getColumnIndex("classes"))))));
				// Move to the next row (break if it cant)
				if (!result.moveToNext()) {
					break;
				}
			}
		}
		database.close();


		// Setup the create new schedule button
		this.findViewById(R.id.newSchedule).setOnClickListener((event) -> this.createNewRegime().show());

		// Setup the save button
		this.findViewById(R.id.back).setOnClickListener((event) -> finish());

	}

	private LinearLayout generateRegimeView(Regime regime) {
		//LinearLayout layout = new LinearLayout(this);
		// TODO
		android.util.Log.w("Need to create a spot for", regime.getName());
		return null;
	}

	// TODO Refresh the view

	// https://developer.android.com/guide/topics/ui/dialogs#java
	private AlertDialog createNewRegime(String name, boolean su, boolean m, boolean tu, boolean w, boolean th, boolean f, boolean sa) {

		AlertDialog.Builder dialog = new AlertDialog.Builder(this);

		// Get the layout inflater
		LayoutInflater inflater = this.getLayoutInflater();

		View creationDialog = inflater.inflate(R.layout.createnewregime, null);

		final EditText regimeName = creationDialog.findViewById(R.id.name);
		final CheckBox sunday = creationDialog.findViewById(R.id.sunday),
				monday = creationDialog.findViewById(R.id.monday),
				tuesday = creationDialog.findViewById(R.id.tuesday),
				wednesday = creationDialog.findViewById(R.id.wednesday),
				thursday = creationDialog.findViewById(R.id.thursday),
				friday = creationDialog.findViewById(R.id.friday),
				saturday = creationDialog.findViewById(R.id.saturday);

		// Prepopulate any variables
		if (!name.equals("")) {
			regimeName.setText(name);
		}
		sunday.setChecked(su);
		monday.setChecked(m);
		tuesday.setChecked(tu);
		wednesday.setChecked(w);
		thursday.setChecked(th);
		friday.setChecked(f);
		saturday.setChecked(sa);

		dialog.setView(creationDialog);
		dialog.setPositiveButton("Add classes", (event, id) -> {
			EditClasses.name = regimeName.getText().toString();
			ArrayList<Integer> dates = new ArrayList<>();
			// Get which dates are checked
			if (sunday.isChecked()) {
				dates.add(Calendar.SUNDAY);
			}
			if (monday.isChecked()) {
				dates.add(Calendar.MONDAY);
			}
			if (tuesday.isChecked()) {
				dates.add(Calendar.TUESDAY);
			}
			if (wednesday.isChecked()) {
				dates.add(Calendar.WEDNESDAY);
			}
			if (thursday.isChecked()) {
				dates.add(Calendar.THURSDAY);
			}
			if (friday.isChecked()) {
				dates.add(Calendar.FRIDAY);
			}
			if (saturday.isChecked()) {
				dates.add(Calendar.SATURDAY);
			}

			EditClasses.dates = new int[dates.size()];
			for (int i = 0; i < dates.size(); i++) {
				EditClasses.dates[i] = dates.get(i);
			}
			this.startActivity(new Intent(EditRegime.this, EditClasses.class));
		}).setNegativeButton(R.string.cancel, null);

		return dialog.create();

	}

	private AlertDialog createNewRegime() {
		return this.createNewRegime("", false, false, false, false, false, false, false);
	}
}

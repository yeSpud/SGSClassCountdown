package com.spud.sgsclasscountdownapp.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spud.sgsclasscountdownapp.R;
import com.spud.sgsclasscountdownapp.Regime.Regime;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Stephen Ogden on 2/6/19.
 */
public class EditRegime extends android.support.v7.app.AppCompatActivity {

	private ArrayList<Regime> regimes = new ArrayList<>();

	private LinearLayout regimeList;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.editregime);

		// Linear layout to house all the regimes
		regimeList = this.findViewById(R.id.regimesList);

		// Setup the create new schedule button
		this.findViewById(R.id.newSchedule).setOnClickListener((event) -> this.createNewRegime().show());

		// Setup the save button
		this.findViewById(R.id.back).setOnClickListener((event) -> finish());

	}

	private void generateRegimeView() {

		Log.d("onResume", "Generating regime...");

		for (int i = 0; i < regimeList.getChildCount(); i++) {
			// Remove all but the add button
			if (!(regimeList.getChildAt(i) instanceof Button)) {
				Log.d("generateRegimeView", String.format("Removing element %d/%d", i, regimeList.getChildCount()));
				regimeList.removeViewAt(i);
			}
		}

		for (Regime r : regimes) {

			Log.d("generateRegimeView", String.format("Generating view for regime %s", r.getName()));

			TextView title = new TextView(this);
			title.setText(r.getName());
			LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			titleParams.setMargins(0, 0, 5, 0);
			title.setLayoutParams(titleParams);

			TextView classCount = new TextView(this);
			classCount.setText(r.getClassCount() == 1 ? "1 class" : r.getClassCount() + " classes");

			Button edit = new Button(this);
			edit.setText("Edit");
			edit.setOnClickListener((e) -> {
				boolean su = false, m = false, tu = false, w = false, th = false, f = false, sa = false;
				for (int i : r.getDateOccurrence()) {
					if (i == Calendar.SUNDAY) {
						su = true;
					} else if (i == Calendar.MONDAY) {
						m = true;
					} else if (i == Calendar.TUESDAY) {
						tu = true;
					} else if (i == Calendar.WEDNESDAY) {
						w = true;
					} else if (i == Calendar.THURSDAY) {
						th = true;
					} else if (i == Calendar.FRIDAY) {
						f = true;
					} else if (i == Calendar.SATURDAY) {
						sa = true;
					}
				}
				this.createNewRegime(r.getName(), su, m, tu, w, th, f, sa).show();
			});

			Button delete = new Button(this);
			delete.setText("Remove");
			delete.setOnClickListener((e) -> {
				regimes.remove(r);
				r.removeRegime();
				this.generateRegimeView();
			});

			LinearLayout l = new LinearLayout(this);
			l.addView(title, 0);
			l.addView(classCount, 1);
			l.addView(edit, 2);
			l.addView(delete, 3);

			regimeList.addView(l, regimeList.getChildCount() - 1);
		}

	}

	protected void onResume() {
		super.onResume();

		this.regimes.clear();

		SQLiteDatabase database = SQLiteDatabase.openDatabase(Regime.regimeDatabase.getAbsolutePath(),
				null, 0x0000);

		// Get all the regimes from the database
		Cursor result = database.rawQuery("SELECT * FROM regimes;", null);

		// Move the cursor to the first row
		if (result.moveToFirst()) {
			for (int i = 0; i < result.getCount(); i++) {
				regimes.add(new Regime(result.getString(result.getColumnIndex("name")),
						Regime.parseDates(result.getString(result.getColumnIndex("occurrence"))),
						Regime.parseClasses(result.getString(result.getColumnIndex("classes")))));
				// Move to the next row (break if it cant)
				if (!result.moveToNext()) {
					break;
				}
			}
		}
		database.close();

		this.generateRegimeView();
	}

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

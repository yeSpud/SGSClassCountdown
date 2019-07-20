package com.spud.ClassCountdown.Activities;

import android.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spud.ClassCountdown.R;
import com.spud.ClassCountdown.Regime.Regime;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Stephen Ogden on 2/6/19.
 */
public class EditRegime extends android.support.v7.app.AppCompatActivity {

	private ArrayList<Regime> regimes = new ArrayList<>();

	private LinearLayout regimeList;

	private View createRegimeName;

	@android.annotation.SuppressLint("InflateParams")
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.editregime);

		// Linear layout to house all the regimes
		regimeList = this.findViewById(R.id.regimesList);

		// Get the layout inflater
		android.view.LayoutInflater inflater = this.getLayoutInflater();

		this.createRegimeName = inflater.inflate(R.layout.createnewregime, null);

		// Setup the create new schedule button
		this.findViewById(R.id.newSchedule).setOnClickListener((event) -> {
			AlertDialog createNewRegimeName = this.createNewRegime();
			createNewRegimeName.setView(this.createRegimeName);
			createNewRegimeName.show();
		});

		// Setup the save button
		this.findViewById(R.id.back).setOnClickListener((event) -> this.finish());

	}

	private void generateRegimeView() {

		regimeList.removeAllViews();

		for (Regime r : regimes) {

			android.util.Log.d("generateRegimeView", String.format("Generating view for regime %s", r.getName()));

			TextView title = this.createTextView();
			title.setText(r.getName());

			TextView classCount = this.createTextView();
			classCount.setText(r.getClassCount() == 1 ? "1 class" : r.getClassCount() + " classes");

			Button edit = new Button(this);
			edit.setText(R.string.edit);
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

				AlertDialog createNewRegimeName = this.createNewRegime(r.getName(), su, m, tu, w, th, f, sa);
				createNewRegimeName.setView(this.createRegimeName);
				createNewRegimeName.show();

			});
			edit.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

			Button delete = new Button(this);
			delete.setText(R.string.delete);
			delete.setOnClickListener((e) -> {
				regimes.remove(r);
				r.removeRegime();
				this.generateRegimeView();
			});
			delete.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

			LinearLayout l = new LinearLayout(this);
			l.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			l.setOrientation(LinearLayout.HORIZONTAL);
			l.addView(title, 0);
			l.addView(classCount, 1);
			l.addView(edit, 2);
			l.addView(delete, 3);

			regimeList.addView(l);
		}

	}

	protected void onResume() {
		super.onResume();

		this.regimes.clear();

		android.database.sqlite.SQLiteDatabase database = Regime.getDatabase();

		// Get all the regimes from the database
		android.database.Cursor result = database.rawQuery("SELECT * FROM regimes;", null);

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
		result.close();
		database.close();

		this.generateRegimeView();
	}

	// https://developer.android.com/guide/topics/ui/dialogs#java
	private AlertDialog createNewRegime(String name, boolean su, boolean m, boolean tu, boolean w, boolean th, boolean f, boolean sa) {

		AlertDialog.Builder dialog = new AlertDialog.Builder(this);

		dialog.setTitle(R.string.enter_schedule_name);

		final android.widget.EditText regimeName = this.createRegimeName.findViewById(R.id.name);

		// Prepopulate any variables
		if (!name.equals("")) {
			regimeName.setText(name);
		}

		dialog.setPositiveButton(R.string.next, (event, id) -> {
			ActivityHelper.killView(this.createRegimeName);
			AlertDialog occurrenceDialog = this.createOccurrenceDialog(regimeName.getText().toString(), su, m, tu, w, th, f, sa);
			occurrenceDialog.show();
		}).setNegativeButton(R.string.cancel, (event, id) -> ActivityHelper.killView(this.createRegimeName));

		return dialog.create();

	}

	private AlertDialog createOccurrenceDialog(String name, boolean su, boolean m, boolean tu, boolean w, boolean th, boolean f, boolean sa) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);

		ArrayList<Integer> selectedDates = new ArrayList<>();
		// Prepopulate the array
		if (su) {
			selectedDates.add(Calendar.SUNDAY);
		}
		if (m) {
			selectedDates.add(Calendar.MONDAY);
		}
		if (tu) {
			selectedDates.add(Calendar.TUESDAY);
		}
		if (w) {
			selectedDates.add(Calendar.WEDNESDAY);
		}
		if (th) {
			selectedDates.add(Calendar.THURSDAY);
		}
		if (f) {
			selectedDates.add(Calendar.FRIDAY);
		}
		if (sa) {
			selectedDates.add(Calendar.SATURDAY);
		}

		dialog.setTitle(R.string.select_the_day_this_schedule_occurs);

		dialog.setMultiChoiceItems(new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"},
				new boolean[]{su, m, tu, w, th, f, sa}, (dialog1, which, isChecked) -> {
					// The calendar instance starts at 1
					if (isChecked) {
						selectedDates.add(which + 1);
					} else if (selectedDates.contains(which + 1)) {
						selectedDates.remove(which + 1);
					}
				});

		dialog.setPositiveButton(R.string.edit_classes, (event, id) -> {

			EditClasses.name = name;

			if (selectedDates.size() != 0) {
				EditClasses.dates = new int[selectedDates.size()];
				for (int i = 0; i < selectedDates.size(); i++) {
					EditClasses.dates[i] = selectedDates.get(i);
				}
				this.startActivity(new android.content.Intent(EditRegime.this, EditClasses.class));
			}
		}).setNegativeButton(R.string.back, (event, id) -> {
			AlertDialog createNewRegimeName = this.createNewRegime(name, su, m, tu, w, th, f, sa);
			createNewRegimeName.setView(this.createRegimeName);
			createNewRegimeName.show();
		});

		return dialog.create();
	}

	private AlertDialog createNewRegime() {
		return this.createNewRegime("", false, false, false, false, false, false, false);
	}

	private TextView createTextView() {
		TextView t = new TextView(this);
		t.setGravity(android.view.Gravity.CENTER_VERTICAL);
		t.setTextColor(android.graphics.Color.WHITE);
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		p.setMargins(0, 0, 20, 0);
		t.setLayoutParams(p);
		return t;
	}

}

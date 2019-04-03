package com.spud.sgsclasscountdownapp.Activities;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.spud.sgsclasscountdownapp.R;
import com.spud.sgsclasscountdownapp.Regime.Class;
import com.spud.sgsclasscountdownapp.Timer;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Stephen Ogden on 2/6/19.
 */
public class EditClasses extends android.support.v7.app.AppCompatActivity {

	public static String name;
	public static int[] dates;

	private LinearLayout classList;

	private ArrayList<Class> classes = new ArrayList<>();

	protected void onCreate(android.os.Bundle bundle) {
		super.onCreate(bundle);

		this.setContentView(R.layout.editclasses);

		// Setup the cancel button to just finish the activity
		this.findViewById(R.id.cancel).setOnClickListener((event) -> this.finish());

		// Update the header to also display the name of the regime
		((TextView) this.findViewById(R.id.classHeaderText)).setText("Adding classes for " + EditClasses.name);

		// Find the class view layout
		classList = this.findViewById(R.id.classList);

		// TODO Get current classes from database, and put them into a class array
		this.generateClasses();

		// Find and setup the add class button
		classList.findViewById(R.id.addClass).setOnClickListener((event) -> this.editClasses("", Timer.getCurrentTime(), Timer.getCurrentTime(), "").show());

		// Setup the save button
		this.findViewById(R.id.save).setOnClickListener((event -> {
			// TODO
			// Save the classes array to the database
			this.finish();
		}));

	}

	// https://developer.android.com/guide/topics/ui/dialogs#java
	private AlertDialog editClasses(String name, long startTime, long endTime, String customName) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);

		// Get the layout inflater
		LayoutInflater inflater = this.getLayoutInflater();

		final View creationDialog = inflater.inflate(R.layout.classname, null);

		// Prepopulate any variables
		final EditText officialName = creationDialog.findViewById(R.id.className);
		if (!name.equals("")) {
			officialName.setText(name);
		}

		final EditText customNameText = creationDialog.findViewById(R.id.customClassName);
		if (!customName.equals("")) {
			customNameText.setText(customName);
		}

		// Start time
		final TimePicker start = creationDialog.findViewById(R.id.startTime);
		if (Build.VERSION.SDK_INT < 23) {
			start.setCurrentHour(Timer.getHour(startTime));
			start.setCurrentMinute(Timer.getMinute(startTime));
		} else {
			start.setHour(Timer.getHour(startTime));
			start.setMinute(Timer.getMinute(startTime));
		}


		// End time
		final TimePicker end = creationDialog.findViewById(R.id.endTime);
		if (Build.VERSION.SDK_INT < 23) {
			end.setCurrentHour(Timer.getHour(endTime));
			end.setCurrentMinute(Timer.getMinute(endTime));
		} else {
			end.setHour(Timer.getHour(endTime));
			end.setMinute(Timer.getMinute(endTime));
		}


		dialog.setView(creationDialog);
		dialog.setPositiveButton("Create class", (event, id) -> {
			long sT, eT;
			if (Build.VERSION.SDK_INT < 23) {
				sT = start.getCurrentHour() * 3600 + start.getCurrentMinute() * 60;
				eT = end.getCurrentHour() * 3600 + end.getCurrentMinute() * 60;
			} else {
				sT = start.getHour() * 3600 + start.getMinute() * 60;
				eT = end.getHour() * 3600 + end.getMinute() * 60;
			}

			Class newClass = new Class(officialName.getText().toString(), sT, eT, customNameText.getText().toString());

			this.classes.add(newClass);

			this.generateClasses();
		}).setNegativeButton(R.string.cancel, null);

		return dialog.create();
	}

	private void generateClasses() {

		for (int i = 0; i < classList.getChildCount(); i++) {
			// Remove all but the add button
			if (!(classList.getChildAt(i) instanceof Button)) {
				classList.removeViewAt(i);
			}
		}

		for (Class c : classes) {
			TextView title = new TextView(this);
			title.setText(c.hasCustomName() ? String.format(Locale.ENGLISH, "%s (%s)", c.getName(false), c.getName(true)) : c.getName(false));
			title.setTextColor(Color.WHITE);
			title.setTextSize(15);
			LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			titleParams.setMargins(0, 0, 20, 0);
			title.setLayoutParams(titleParams);

			TextView time = new TextView(this);
			int startTimeHour = Timer.getHour(c.getStartTime()), startTimeMinute = Timer.getMinute(c.getStartTime()) % 60,
					endTimeHour = Timer.getHour(c.getEndTime()), endTimeMinute = Timer.getMinute(c.getEndTime()) % 60;

			if (DateFormat.is24HourFormat(this)) {
				time.setText(String.format(Locale.ENGLISH, "%d:%02d - %d:%02d", startTimeHour, startTimeMinute, endTimeHour, endTimeMinute));
			} else {
				boolean startPM = startTimeHour >= 12;
				startTimeHour = startTimeHour % 12;
				String start = String.format(Locale.ENGLISH, "%d:%02d %s", startTimeHour, startTimeMinute, startPM ? "PM" : "AM");

				boolean endPM = endTimeHour >= 12;
				endTimeHour = (endTimeHour % 12);
				String end = String.format(Locale.ENGLISH, "%d:%02d %s", endTimeHour, endTimeMinute, endPM ? "PM" : "AM");

				time.setText(String.format(Locale.ENGLISH, "%s - %s", start, end));
			}
			time.setTextColor(Color.WHITE);
			time.setTextSize(15);

			Button edit = new Button(this);
			edit.setText("Edit class");
			edit.setTextSize(15);
			edit.setOnClickListener((e) -> {
				classes.remove(c);
				this.editClasses(c.getName(false), c.getStartTime(), c.getEndTime(), c.hasCustomName() ? c.getName(true) : "").show();
			});

			Button delete = new Button(this);
			delete.setText("Remove class");
			delete.setTextSize(15);
			delete.setOnClickListener((e) -> {
				classes.remove(c);
				this.generateClasses();
			});

			LinearLayout classDetails = new LinearLayout(this);
			classDetails.setOrientation(LinearLayout.HORIZONTAL);
			classDetails.addView(title, 0);
			classDetails.addView(time, 1);
			classDetails.addView(edit, 2);
			classDetails.addView(delete, 3);

			classList.addView(classDetails, classList.getChildCount() - 1);

		}
	}
}

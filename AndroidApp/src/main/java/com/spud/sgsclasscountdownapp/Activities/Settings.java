package com.spud.sgsclasscountdownapp.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.RadioButton;

import com.spud.sgsclasscountdownapp.R;
import com.spud.sgsclasscountdownapp.Regime.Regime;

import java.util.ArrayList;

/**
 * Created by Stephen Ogden on 4/23/18.
 */
public class Settings extends android.support.v7.app.AppCompatActivity {

	private ArrayList<String> regimeNames = new ArrayList<>();

	private android.widget.RadioGroup buttons;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.settings);

		this.buttons = this.findViewById(R.id.updateGroup);

		// Setup back button
		this.findViewById(R.id.back).setOnClickListener((event) -> {
			// Set all overrides to false
			SQLiteDatabase database = Regime.getDatabase();
			database.execSQL("UPDATE regimes SET override = \"false\"");
			// Set the single override based on which radiobutton is checked
			RadioButton checkedButton = this.findViewById(this.buttons.getCheckedRadioButtonId());
			String checkedButtonName = checkedButton.getText().toString();
			if (!checkedButtonName.equals("Automatic")) {
				Log.d("New override", checkedButtonName);
				database.execSQL("UPDATE regimes SET override = \"true\" WHERE name = \"" + checkedButtonName + "\";");
			}
			database.close();
			this.finish();
		});

		// Setup the edit regimes button
		this.findViewById(R.id.editSchedules).setOnClickListener((event) ->
				this.startActivity(new android.content.Intent(Settings.this, EditRegime.class)));

		// Setup the dialog box for the restore button
		this.findViewById(R.id.Reset).setOnClickListener((event) ->
				new FactoryResetDialog().show(this.getFragmentManager(), "Are you sure you want to do that?"));
	}

	@Override
	protected void onResume() {
		super.onResume();

		this.generateRegimes();
		this.handleOverrides();
	}

	private RadioButton generateRadioButton(String name) {
		RadioButton button = new RadioButton(this);
		button.setTextColor(android.graphics.Color.WHITE);
		button.setText(name);
		return button;
	}

	private void generateRegimes() {

		// Clear the regime list
		this.regimeNames.clear();

		for (int i = 0; i < this.buttons.getChildCount(); i++) {
			RadioButton b = (RadioButton) this.buttons.getChildAt(i);
			if (!b.getText().toString().equals("Automatic")) {
				this.buttons.removeViewAt(i);
			}
		}

		SQLiteDatabase database = Regime.getDatabase();

		// Get all the regime names from the database
		Cursor result = database.rawQuery("SELECT name FROM regimes;", null);

		// Move the cursor to the first row
		if (result.moveToFirst()) {
			for (int i = 0; i < result.getCount(); i++) {
				// Add the regime name to the list
				this.regimeNames.add(result.getString(result.getColumnIndex("name")));

				// Move to the next row (break if it cant)
				if (!result.moveToNext()) {
					break;
				}
			}
		}
		result.close();
		database.close();

		// Generate a new button for each new regime
		for (String s : this.regimeNames) {
			RadioButton b = this.generateRadioButton(s);
			this.buttons.addView(b);
		}
	}

	private void handleOverrides() {
		// Check for overrides
		String overrideName;

		SQLiteDatabase database = Regime.getDatabase();

		// Get all the regime names from the database that have override
		Cursor result = database.rawQuery("SELECT name FROM regimes WHERE override = \"true\";", null);
		if (result.getCount() != 0 && result.moveToFirst()) {
			overrideName = result.getString(result.getColumnIndex("name"));
			Log.d("Name of override", overrideName);
		} else {
			// Just set the automatic button to be enabled
			((RadioButton) this.buttons.getChildAt(0)).setChecked(true);
			Log.d("Name of override", "Automatic");
			result.close();
			database.close();
			return;
		}

		result.close();
		database.close();

		// Check if the button name equals the override name. If it does, break early
		for (int i = 0; i < this.buttons.getChildCount(); i++) {
			RadioButton b = (RadioButton) this.buttons.getChildAt(i);
			if (b.getText().toString().equals(overrideName)) {
				b.setChecked(true);
				break;
			}
		}
	}
}

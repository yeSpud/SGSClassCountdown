package com.spud.sgsclasscountdownapp.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.spud.sgsclasscountdownapp.R;
import com.spud.sgsclasscountdownapp.Regime.Regime;

/**
 * Created by Stephen Ogden on 4/23/18.
 */
public class Settings extends android.support.v7.app.AppCompatActivity {

    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.settings);

        RadioGroup group = this.findViewById(R.id.updateGroup);

        RadioButton checkedButton = this.findViewById(group.getCheckedRadioButtonId());

        SQLiteDatabase database = SQLiteDatabase.openDatabase(Regime.regimeDatabase.getAbsolutePath(),
                null, 0x0000);
        // Get all the regime names from the database
        Cursor result = database.rawQuery("SELECT name FROM regimes;", null);

        // Move the cursor to the first row
        if (result.moveToFirst()) {
            for (int i = 0; i < result.getCount(); i++) {
                // Add the to the radio group
                group.addView(this.generateRadioButton(result.getString(result.getColumnIndex("name"))));
                // Move to the next row (break if it cant)
                if (!result.moveToNext()) {
                    break;
                }
            }
        }
        database.close();


        // Setup back button
        this.findViewById(R.id.back).setOnClickListener((event) -> {
            //this.setCurrentRegime(); // Set the regime based on name for future lookup
            this.finish();
        });

        // Setup the edit regimes button
        this.findViewById(R.id.editSchedules).setOnClickListener((event) ->
                this.startActivity(new android.content.Intent(Settings.this, EditRegime.class)));

        // Setup the dialog box for the restore button
        this.findViewById(R.id.Reset).setOnClickListener((event) ->
                new FactoryResetDialog().show(this.getFragmentManager(), "Are you sure you want to do that?"));
    }

    private void setCurrentRegime(String name) {
        // TODO
    }

    private RadioButton generateRadioButton(String name) {
        RadioButton button = new RadioButton(this);
        button.setTextColor(Color.WHITE);
        button.setText(name);
        return button;
    }

}

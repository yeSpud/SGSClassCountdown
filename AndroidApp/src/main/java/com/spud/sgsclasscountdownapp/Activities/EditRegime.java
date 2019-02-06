package com.spud.sgsclasscountdownapp.Activities;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.spud.sgsclasscountdownapp.R;
import com.spud.sgsclasscountdownapp.Regime.Regime;

/**
 * Created by Stephen Ogden on 2/6/19.
 */
public class EditRegime extends android.support.v7.app.AppCompatActivity {

    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.editregime);

        // TODO: Dynamically set the regimes based on what is stored in the database
        SQLiteDatabase database = SQLiteDatabase.openDatabase(Regime.regimeDatabase.getAbsolutePath(),
                null, 0x0000);
        // Get all the regimes from the database
        Cursor result = database.rawQuery("SELECT * FROM regimes;", null);

        // Change the display message to the regimes that were retrieved.
        this.findViewById(R.id.nothingEntered).setVisibility(result.getCount() != 0 ? View.GONE : View.VISIBLE);

        // Move the cursor to the first row
        if (result.moveToFirst()) {

            for (int i = 0; i < result.getCount(); i++) {
                Regime currentRegime = new Regime(result.getString(result.getColumnIndex("name")),
                        Regime.parseDates(result.getString(result.getColumnIndex("occurrence"))),
                        Regime.parseClasses(result.getString(result.getColumnIndex("classes"))));
                this.generateRegimeView(currentRegime);

                // Move to the next row (break if it cant)
                if (!result.moveToNext()) {
                    break;
                }
            }
        }


        // Setup the create new schedule button
        this.findViewById(R.id.newSchedule).setOnClickListener((event) -> this.createNewRegime().show());

        // Setup the save button
        this.findViewById(R.id.back).setOnClickListener((event) -> finish()); // TODO: Save the regimes as well

    }

    private LinearLayout generateRegimeView(Regime regime) {
        //LinearLayout layout = new LinearLayout(this);
        // TODO
        android.util.Log.w("Need to create a spot for", regime.getName());
        return null;
    }

    // https://developer.android.com/guide/topics/ui/dialogs#java
    private AlertDialog createNewRegime(String name, boolean su, boolean m, boolean tu, boolean w, boolean th, boolean f, boolean sa) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        final View creationDialog = inflater.inflate(R.layout.createnewregime, null);

        // Prepopulate any variables
        if (!name.equals("")) {
            ((EditText) creationDialog.findViewById(R.id.name)).setText(name);
        }

        ((CheckBox) creationDialog.findViewById(R.id.sunday)).setChecked(su);
        ((CheckBox) creationDialog.findViewById(R.id.monday)).setChecked(m);
        ((CheckBox) creationDialog.findViewById(R.id.tuesday)).setChecked(tu);
        ((CheckBox) creationDialog.findViewById(R.id.wednesday)).setChecked(w);
        ((CheckBox) creationDialog.findViewById(R.id.thursday)).setChecked(th);
        ((CheckBox) creationDialog.findViewById(R.id.friday)).setChecked(f);
        ((CheckBox) creationDialog.findViewById(R.id.saturday)).setChecked(sa);

        dialog.setView(creationDialog);
        dialog.setPositiveButton("Add classes", (event, id) -> {
            // TODO: pass the name and date into the class page
        }).setNegativeButton("Cancel", null);

        return dialog.create();

    }

    private AlertDialog createNewRegime() {
        return this.createNewRegime("", false, false, false, false, false, false, false);
    }
}

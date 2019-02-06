package com.spud.sgsclasscountdownapp.Activities;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.spud.sgsclasscountdownapp.R;

/**
 * Created by Stephen Ogden on 4/23/18.
 */
public class Settings extends android.support.v7.app.AppCompatActivity {

    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.settings);

        // TODO: Create radio buttons based on different regimes
        // If none exist, hide the auto one, and display a message telling the user to create a regime
        RadioGroup group = this.findViewById(R.id.updateGroup);

        try {
            RadioButton checkedButton = this.findViewById(group.getCheckedRadioButtonId());
        } catch (Exception e) {
            // Just assume automatic is selected
        }


        // Setup back button
        findViewById(R.id.back).setOnClickListener((event) -> {
            //this.setCurrentRegime(); // Set the regime based on name for future lookup
            this.finish();
        });

        // Setup the dialog box for the restore button
        findViewById(R.id.Reset).setOnClickListener((event) -> new FactoryResetDialog().show(this.getFragmentManager(), "Are you sure you want to do that?"));
    }

    private void setCurrentRegime(String name) {
        // TODO
    }

    private RadioButton generateRadioButton(String name) {
        // TODO
        return null;
    }

}

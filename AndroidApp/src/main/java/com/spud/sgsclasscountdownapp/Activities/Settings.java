package com.spud.sgsclasscountdownapp.Activities;

import com.spud.sgsclasscountdownapp.R;

/**
 * Created by Stephen Ogden on 4/23/18.
 */
public class Settings extends android.support.v7.app.AppCompatActivity {

    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.settings);

        // Setup back button
        findViewById(R.id.back).setOnClickListener((event) -> finish()); // TODO: Write options

        // Setup the dialog box for the restore button
        findViewById(R.id.Reset).setOnClickListener((event) -> new FactoryResetDialog().show(this.getFragmentManager(), "Are you sure you want to do that?"));
    }

}

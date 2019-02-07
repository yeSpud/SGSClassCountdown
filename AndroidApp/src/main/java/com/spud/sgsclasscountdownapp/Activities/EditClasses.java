package com.spud.sgsclasscountdownapp.Activities;

import android.widget.TextView;

import com.spud.sgsclasscountdownapp.R;

/**
 * Created by Stephen Ogden on 2/6/19.
 */
public class EditClasses extends android.support.v7.app.AppCompatActivity {

    public static String name;
    public static int[] dates;

    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);

        this.setContentView(R.layout.editclasses);

        // Setup the cancel button to just finish the activity
        this.findViewById(R.id.cancel).setOnClickListener((event) -> this.finish());

        // Update the header to also display the name of the regime
        ((TextView) this.findViewById(R.id.classHeaderText)).setText("Adding classes for " + EditClasses.name);


    }

}

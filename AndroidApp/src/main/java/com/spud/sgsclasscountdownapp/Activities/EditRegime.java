package com.spud.sgsclasscountdownapp.Activities;

import com.spud.sgsclasscountdownapp.R;

/**
 * Created by Stephen Ogden on 2/6/19.
 */
public class EditRegime extends android.support.v7.app.AppCompatActivity {

    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.editregime);

        // TODO

        this.findViewById(R.id.back).setOnClickListener((event) -> finish()); // TODO: Save the regimes as well

    }
}

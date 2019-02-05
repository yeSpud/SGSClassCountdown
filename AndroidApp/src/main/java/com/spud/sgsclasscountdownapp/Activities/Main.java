package com.spud.sgsclasscountdownapp.Activities;

import com.spud.sgsclasscountdownapp.R;
import com.spud.sgsclasscountdownapp.Timer;

import java.io.File;

public class Main extends android.support.v7.app.AppCompatActivity {

    public static File dir;

    private static Timer timer = new Timer();

    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the view to the main XML file
        this.setContentView(R.layout.main);

        Main.dir = this.getFilesDir();

        Main.timer.enable = true;

        // Find the blockType, countdown, and noClass header text fields from the XML file

        // Setup the Settings button
        this.findViewById(R.id.gotoSettings).setOnClickListener((event) -> this.startActivity(new android.content.Intent(Main.this, Settings.class)));

    }

    protected void onPause() {
        super.onPause();

        // Pause the timer
        Main.timer.enable = false;
    }

    protected void onResume() {
        super.onResume();

        // Resume the timer
        Main.timer.enable = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Stop the timer
        Main.timer.enable = false;
    }
}

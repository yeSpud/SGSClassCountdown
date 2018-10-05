package com.spud.sgsclasscountdownapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Stephen Ogden on 4/23/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
public class Settings extends AppCompatActivity {

    RadioGroup override;

    RadioButton automatic, builtin, manual, override8, overrideA, overrideE;

    private Database database = new Database();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        override = findViewById(R.id.overrideGroup);

        automatic = findViewById(R.id.AutomaticUpdate);
        builtin = findViewById(R.id.Builtin);
        manual = findViewById(R.id.Manual);
        override8 = findViewById(R.id.override8);
        overrideA = findViewById(R.id.overrideA);
        overrideE = findViewById(R.id.overrideE);

        // If the database does not exist, disable set the override buttons
        manual.setEnabled(database.databaseExists());
        override.setEnabled(database.databaseExists());

        // Check if there is a network connection available for the automatic portion
        automatic.setEnabled(isNetworkAvailable());

    }

    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // TODO: Write options to database
    }

    // https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

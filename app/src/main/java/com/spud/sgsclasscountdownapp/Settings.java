package com.spud.sgsclasscountdownapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
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

        automatic.setChecked(database.getUpdateType().equals(UpdateType.Automatic));
        builtin.setChecked(database.getUpdateType().equals(UpdateType.BuiltIn));
        manual.setChecked(database.getUpdateType().equals(UpdateType.ManualADay) ||
                database.getUpdateType().equals(UpdateType.ManualEDay) ||
                database.getUpdateType().equals(UpdateType.ManualFullDay) ||
                database.getUpdateType().equals(UpdateType.ManualCustomDay));

        if (manual.isChecked()) {
            override8.setChecked(database.getUpdateType().equals(UpdateType.ManualFullDay));
            overrideA.setChecked(database.getUpdateType().equals(UpdateType.ManualADay));
            overrideE.setChecked(database.getUpdateType().equals(UpdateType.ManualEDay));
        } else {
            for (int i = 0; i < override.getChildCount(); i++) {
                ((RadioButton) override.getChildAt(i)).setChecked(false);
                (override.getChildAt(i)).setEnabled(false);
            }
        }

        // If the database does not exist, disable set the override buttons
        manual.setEnabled(!database.doesNotExist());
        override.setEnabled(database.doesNotExist());

        // Setup back button
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, Timer.class));
                finish();
            }
        });

        manual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for (int i = 0; i < override.getChildCount(); i++) {
                    override.getChildAt(i).setEnabled(b);
                }
            }
        });

        // Setup the dialog box for the restore button
        findViewById(R.id.Reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FactoryResetDialog dialog = new FactoryResetDialog();
                dialog.show(getFragmentManager(), "Are you sure you want to do that?");
            }
        });

        findViewById(R.id.classNames).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateDialog().show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check if there is a network connection available for the automatic portion
        automatic.setEnabled(isNetworkAvailable());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Database database = new Database();
        //noinspection UnusedAssignment - Complaines about not being initalized if I make it uninitalized, which it says it can be...
        UpdateType updateType = null;

        if (builtin.isChecked()) {
            updateType = UpdateType.BuiltIn;
        } else if (automatic.isChecked()) {
            updateType = UpdateType.Automatic;
        } else if (manual.isChecked()) {
            if (overrideE.isChecked()) {
                updateType = UpdateType.ManualEDay;
            } else if (overrideA.isChecked()) {
                updateType = UpdateType.ManualADay;
            } else if (override8.isChecked()) {
                updateType = UpdateType.ManualFullDay;
            } else {
                updateType = UpdateType.BuiltIn;
            }
        } else {
            updateType = UpdateType.BuiltIn;
        }


        database.writeToDatabase(database.getDatabaseVersion(),
                updateType,
                database.getBlockName(Block.ANormal),
                database.getBlockName(Block.BNormal),
                database.getBlockName(Block.CNormal),
                database.getBlockName(Block.DNormal),
                database.getBlockName(Block.ENormal),
                database.getBlockName(Block.FNormal),
                database.getBlockName(Block.GNormal),
                database.getBlockName(Block.HNormal));

    }

    // https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // https://developer.android.com/guide/topics/ui/dialogs#java
    @SuppressLint("InflateParams")
    public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // https://stackoverflow.com/questions/10313382/how-to-get-elementsfindviewbyid-for-a-layout-which-is-dynamically-loadedsetvi
        final View view = inflater.inflate(R.layout.classnames, null);

        ((EditText) view.findViewById(R.id.ABlockName)).setText(database.getBlockName(Block.ANormal));
        ((EditText) view.findViewById(R.id.BBlockName)).setText(database.getBlockName(Block.BNormal));
        ((EditText) view.findViewById(R.id.CBlockName)).setText(database.getBlockName(Block.CNormal));
        ((EditText) view.findViewById(R.id.DBlockName)).setText(database.getBlockName(Block.DNormal));
        ((EditText) view.findViewById(R.id.EBlockName)).setText(database.getBlockName(Block.ENormal));
        ((EditText) view.findViewById(R.id.FBlockName)).setText(database.getBlockName(Block.FNormal));
        ((EditText) view.findViewById(R.id.GBlockName)).setText(database.getBlockName(Block.GNormal));
        ((EditText) view.findViewById(R.id.HBlockName)).setText(database.getBlockName(Block.HNormal));

        view.findViewById(R.id.ABlockReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) view.findViewById(R.id.ABlockName)).setText("");
            }
        });

        view.findViewById(R.id.BBlockReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) view.findViewById(R.id.BBlockName)).setText("");
            }
        });

        view.findViewById(R.id.CBlockReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) view.findViewById(R.id.CBlockName)).setText("");
            }
        });

        view.findViewById(R.id.DBlockReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) view.findViewById(R.id.DBlockName)).setText("");
            }
        });

        view.findViewById(R.id.EBlockReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) view.findViewById(R.id.EBlockName)).setText("");
            }
        });

        view.findViewById(R.id.FBlockReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) view.findViewById(R.id.FBlockName)).setText("");
            }
        });

        view.findViewById(R.id.GBlockReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) view.findViewById(R.id.GBlockName)).setText("");
            }
        });

        view.findViewById(R.id.HBlockReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) view.findViewById(R.id.HBlockName)).setText("");
            }
        });

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO: Save the names to the database
                        database.writeToDatabase(database.getDatabaseVersion(),
                                database.getUpdateType(),
                                ((EditText) view.findViewById(R.id.ABlockName)).getText().toString(),
                                ((EditText) view.findViewById(R.id.BBlockName)).getText().toString(),
                                ((EditText) view.findViewById(R.id.CBlockName)).getText().toString(),
                                ((EditText) view.findViewById(R.id.DBlockName)).getText().toString(),
                                ((EditText) view.findViewById(R.id.EBlockName)).getText().toString(),
                                ((EditText) view.findViewById(R.id.FBlockName)).getText().toString(),
                                ((EditText) view.findViewById(R.id.GBlockName)).getText().toString(),
                                ((EditText) view.findViewById(R.id.HBlockName)).getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }
}

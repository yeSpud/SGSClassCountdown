package com.spud.sgsclasscountdownapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Stephen Ogden on 4/23/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
public class Settings extends AppCompatActivity {

    RadioGroup override;

    RadioButton automatic, builtin, manual, override8, overrideA, overrideE, CustomRegimeButton;

    private ImageView background;

    private DatabaseFile database = new DatabaseFile();

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
        CustomRegimeButton = findViewById(R.id.Custom);

        CustomRegimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomRegime custom = new CustomRegime();
                if (custom.isEmpty()) {
                    regimeEditorDialog().show();
                }
            }
        });

        automatic.setChecked(database.getUpdateTypeFromDatabase().equals(UpdateType.Automatic));
        builtin.setChecked(database.getUpdateTypeFromDatabase().equals(UpdateType.BuiltIn));
        manual.setChecked(database.getUpdateTypeFromDatabase().equals(UpdateType.ManualADay) ||
                database.getUpdateTypeFromDatabase().equals(UpdateType.ManualEDay) ||
                database.getUpdateTypeFromDatabase().equals(UpdateType.ManualFullDay) ||
                database.getUpdateTypeFromDatabase().equals(UpdateType.ManualCustomDay));

        if (manual.isChecked()) {
            override8.setChecked(database.getUpdateTypeFromDatabase().equals(UpdateType.ManualFullDay));
            overrideA.setChecked(database.getUpdateTypeFromDatabase().equals(UpdateType.ManualADay));
            overrideE.setChecked(database.getUpdateTypeFromDatabase().equals(UpdateType.ManualEDay));
            CustomRegimeButton.setChecked(database.getUpdateTypeFromDatabase().equals(UpdateType.ManualCustomDay));
        } else {
            for (int i = 0; i < override.getChildCount(); i++) {
                ((RadioButton) override.getChildAt(i)).setChecked(false);
                (override.getChildAt(i)).setEnabled(false);
            }
        }

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
                editClassNamesDialog().show();
            }
        });

        // Set up the regime editor to show when the user clicks the custom schedule button
        findViewById(R.id.customSchedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regimeEditorDialog().show();
            }
        });

        if (background != null) {
            ((BitmapDrawable) background.getDrawable()).getBitmap().recycle();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check if there is a network connection available for the automatic portion
        //automatic.setEnabled(isNetworkAvailable());
        // TODO: Finish automatic updates!
        // Until then, it will be disabled
        automatic.setEnabled(false);

        // Setup the background (Unless out of RAM)
        // https://stackoverflow.com/questions/10200256/out-of-memory-error-imageview-issue
        try {
            background = findViewById(R.id.settingsbackgroundImage);
            background.setImageResource(R.drawable.settingsbackground);
            background.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } catch (OutOfMemoryError noRam) {
            Log.e("Background generation", "Out of RAM!");
            background = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseFile database = new DatabaseFile();
        //noinspection UnusedAssignment - Complaines about not being initalized if I make it uninitalized, which it says it can be...
        UpdateType updateType = null;

        // TODO: Add it so when custom is checked, but no regime has been set, automatically open the editor

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
            } else if (CustomRegimeButton.isChecked()) {
                updateType = UpdateType.ManualCustomDay;
            } else {
                // Fall back
                updateType = UpdateType.BuiltIn;
            }
        } else {
            // Fall back
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

        if (background != null) {
            background.setImageDrawable(null);
        }

    }

    // https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // https://developer.android.com/guide/topics/ui/dialogs#java
    public Dialog editClassNamesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // https://stackoverflow.com/questions/10313382/how-to-get-elementsfindviewbyid-for-a-layout-which-is-dynamically-loadedsetvi
        // https://stackoverflow.com/questions/26404951/avoid-passing-null-as-the-view-root-warning-when-inflating-view-for-use-by-ale/26596164
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.classnames, null);

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
                        database.writeToDatabase(database.getDatabaseVersion(),
                                database.getUpdateTypeFromDatabase(),
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

    public Dialog regimeEditorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // https://stackoverflow.com/questions/10313382/how-to-get-elementsfindviewbyid-for-a-layout-which-is-dynamically-loadedsetvi
        // https://stackoverflow.com/questions/26404951/avoid-passing-null-as-the-view-root-warning-when-inflating-view-for-use-by-ale/26596164
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.customregime, null);

        // TODO: If the custom regime file isnt empty, update the times to that stored in the file
        // TODO: If the time is 24-hour time, be sure to to set the display to 12-hour time if they have it set that way

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setTitle("Create custom schedule");
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO: Write the regime
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CustomRegimeButton.setChecked(false);
                    }
                });


        return builder.create();
    }
}

package com.spud.sgsclasscountdownapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

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

        final CustomRegime customRegime = new CustomRegime();

        final CheckBox NoABlock = view.findViewById(R.id.NoABlock),
                NoBBlock = view.findViewById(R.id.NoBBlock),
                NoCBlock = view.findViewById(R.id.NoCBlock),
                NoDBlock = view.findViewById(R.id.NoDBlock),
                NoLunch = view.findViewById(R.id.NoLunch),
                NoEBlock = view.findViewById(R.id.NoEBlock),
                NoFBlock = view.findViewById(R.id.NoFBlock),
                NoGBlock = view.findViewById(R.id.NoGBlock),
                NoHBlock = view.findViewById(R.id.NoHBlock);

        final EditText ABlockStart = view.findViewById(R.id.ABlockStart), ABlockEnd = view.findViewById(R.id.ABlockEnd),
                BBlockStart = view.findViewById(R.id.BBlockStart), BBlockEnd = view.findViewById(R.id.BBlockEnd),
                CBlockStart = view.findViewById(R.id.CBlockStart), CBlockEnd = view.findViewById(R.id.CBlockEnd),
                DBlockStart = view.findViewById(R.id.DBlockStart), DBlockEnd = view.findViewById(R.id.DBlockEnd),
                LunchStart = view.findViewById(R.id.LunchStart), LunchEnd = view.findViewById(R.id.LunchEnd),
                EBlockStart = view.findViewById(R.id.EBlockStart), EBlockEnd = view.findViewById(R.id.EBlockEnd),
                FBlockStart = view.findViewById(R.id.FBlockStart), FBlockEnd = view.findViewById(R.id.FBlockEnd),
                GBlockStart = view.findViewById(R.id.GBlockStart), GBlockEnd = view.findViewById(R.id.GBlockEnd),
                HBlockStart = view.findViewById(R.id.HBlockStart), HBlockEnd = view.findViewById(R.id.HBlockEnd);

        // If the custom regime file isn't empty, set the check boxes to whether or not the block is in the regime
        if (!customRegime.isEmpty()) {
            NoABlock.setChecked(customRegime.isBlockNotInCustomRegime(Block.ANormal));
            NoBBlock.setChecked(customRegime.isBlockNotInCustomRegime(Block.BNormal));
            NoCBlock.setChecked(customRegime.isBlockNotInCustomRegime(Block.CNormal));
            NoDBlock.setChecked(customRegime.isBlockNotInCustomRegime(Block.DNormal));
            NoLunch.setChecked(customRegime.isBlockNotInCustomRegime(Block.LunchNormal));
            NoEBlock.setChecked(customRegime.isBlockNotInCustomRegime(Block.ENormal));
            NoFBlock.setChecked(customRegime.isBlockNotInCustomRegime(Block.FNormal));
            NoGBlock.setChecked(customRegime.isBlockNotInCustomRegime(Block.GNormal));
            NoHBlock.setChecked(customRegime.isBlockNotInCustomRegime(Block.HNormal));
        }

        // Initialize the edit-texts based on whether or not the button is checked
        ABlockStart.setEnabled(!NoABlock.isChecked());
        ABlockEnd.setEnabled(!NoABlock.isChecked());
        BBlockStart.setEnabled(!NoBBlock.isChecked());
        BBlockEnd.setEnabled(!NoBBlock.isChecked());
        CBlockStart.setEnabled(!NoCBlock.isChecked());
        CBlockEnd.setEnabled(!NoCBlock.isChecked());
        DBlockStart.setEnabled(!NoDBlock.isChecked());
        DBlockEnd.setEnabled(!NoDBlock.isChecked());
        LunchStart.setEnabled(!NoLunch.isChecked());
        LunchEnd.setEnabled(!NoLunch.isChecked());
        EBlockStart.setEnabled(!NoEBlock.isChecked());
        EBlockEnd.setEnabled(!NoEBlock.isChecked());
        FBlockStart.setEnabled(!NoFBlock.isChecked());
        FBlockEnd.setEnabled(!NoFBlock.isChecked());
        GBlockStart.setEnabled(!NoGBlock.isChecked());
        GBlockEnd.setEnabled(!NoGBlock.isChecked());
        HBlockStart.setEnabled(!NoHBlock.isChecked());
        HBlockEnd.setEnabled(!NoHBlock.isChecked());

        // If the custom regime file isn't empty, set the correct start and end times
        if (!customRegime.isEmpty()) {
            RegimeFiles regime = new RegimeFiles();

            if (!customRegime.isBlockNotInCustomRegime(Block.ANormal)) {
                // https://stackoverflow.com/questions/15268489/replacing-last-character-in-a-string-with-java
                ABlockStart.setText(regime.getTimesFromRegime(WeekType.Custom, Block.ANormal)[0].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.ANormal)[0].length() - 3));
                ABlockEnd.setText(regime.getTimesFromRegime(WeekType.Custom, Block.ANormal)[1].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.ANormal)[1].length() - 3));
            }
            // TODO: Error after A block for getting time from database
            if (!customRegime.isBlockNotInCustomRegime(Block.BNormal)) {
                // https://stackoverflow.com/questions/15268489/replacing-last-character-in-a-string-with-java
                ABlockStart.setText(regime.getTimesFromRegime(WeekType.Custom, Block.BNormal)[0].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.BNormal)[0].length() - 3));
                ABlockEnd.setText(regime.getTimesFromRegime(WeekType.Custom, Block.BNormal)[1].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.BNormal)[1].length() - 3));
            }

            if (!customRegime.isBlockNotInCustomRegime(Block.CNormal)) {
                // https://stackoverflow.com/questions/15268489/replacing-last-character-in-a-string-with-java
                ABlockStart.setText(regime.getTimesFromRegime(WeekType.Custom, Block.CNormal)[0].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.CNormal)[0].length() - 3));
                ABlockEnd.setText(regime.getTimesFromRegime(WeekType.Custom, Block.CNormal)[1].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.CNormal)[1].length() - 3));
            }

            if (!customRegime.isBlockNotInCustomRegime(Block.DNormal)) {
                // https://stackoverflow.com/questions/15268489/replacing-last-character-in-a-string-with-java
                ABlockStart.setText(regime.getTimesFromRegime(WeekType.Custom, Block.DNormal)[0].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.DNormal)[0].length() - 3));
                ABlockEnd.setText(regime.getTimesFromRegime(WeekType.Custom, Block.DNormal)[1].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.DNormal)[1].length() - 3));
            }

            if (!customRegime.isBlockNotInCustomRegime(Block.LunchNormal)) {
                // https://stackoverflow.com/questions/15268489/replacing-last-character-in-a-string-with-java
                ABlockStart.setText(regime.getTimesFromRegime(WeekType.Custom, Block.LunchNormal)[0].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.LunchNormal)[0].length() - 3));
                ABlockEnd.setText(regime.getTimesFromRegime(WeekType.Custom, Block.LunchNormal)[1].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.LunchNormal)[1].length() - 3));
            }

            if (!customRegime.isBlockNotInCustomRegime(Block.ENormal)) {
                // https://stackoverflow.com/questions/15268489/replacing-last-character-in-a-string-with-java
                ABlockStart.setText(regime.getTimesFromRegime(WeekType.Custom, Block.ENormal)[0].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.ENormal)[0].length() - 3));
                ABlockEnd.setText(regime.getTimesFromRegime(WeekType.Custom, Block.ENormal)[1].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.ENormal)[1].length() - 3));
            }

            if (!customRegime.isBlockNotInCustomRegime(Block.FNormal)) {
                // https://stackoverflow.com/questions/15268489/replacing-last-character-in-a-string-with-java
                ABlockStart.setText(regime.getTimesFromRegime(WeekType.Custom, Block.FNormal)[0].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.FNormal)[0].length() - 3));
                ABlockEnd.setText(regime.getTimesFromRegime(WeekType.Custom, Block.FNormal)[1].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.FNormal)[1].length() - 3));
            }

            if (!customRegime.isBlockNotInCustomRegime(Block.GNormal)) {
                // https://stackoverflow.com/questions/15268489/replacing-last-character-in-a-string-with-java
                ABlockStart.setText(regime.getTimesFromRegime(WeekType.Custom, Block.GNormal)[0].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.GNormal)[0].length() - 3));
                ABlockEnd.setText(regime.getTimesFromRegime(WeekType.Custom, Block.GNormal)[1].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.GNormal)[1].length() - 3));
            }

            if (!customRegime.isBlockNotInCustomRegime(Block.HNormal)) {
                // https://stackoverflow.com/questions/15268489/replacing-last-character-in-a-string-with-java
                ABlockStart.setText(regime.getTimesFromRegime(WeekType.Custom, Block.HNormal)[0].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.HNormal)[0].length() - 3));
                ABlockEnd.setText(regime.getTimesFromRegime(WeekType.Custom, Block.HNormal)[1].substring(0, regime.getTimesFromRegime(WeekType.Custom, Block.HNormal)[1].length() - 3));
            }

        }

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        ABlockStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        ABlockStart.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("A block starts at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();
            }
        });

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        ABlockEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        ABlockEnd.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("A block ends at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();

            }
        });

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        BBlockStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        BBlockStart.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("B block starts at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();
            }
        });

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        BBlockEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        BBlockEnd.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("B block ends at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();

            }
        });

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        CBlockStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        CBlockStart.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("C block starts at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();
            }
        });

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        CBlockEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        CBlockEnd.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("C block ends at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();

            }
        });

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        DBlockStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        DBlockStart.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("D block starts at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();
            }
        });

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        DBlockEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        DBlockEnd.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("D block ends at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();

            }
        });

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        LunchStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        LunchStart.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Lunch starts at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();
            }
        });

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        LunchEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        LunchEnd.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Lunch ends at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();

            }
        });

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        EBlockStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        EBlockStart.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("E block starts at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();
            }
        });

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        EBlockEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        EBlockEnd.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("E block ends at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();

            }
        });

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        FBlockStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        FBlockStart.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("F block starts at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();
            }
        });

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        FBlockEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        FBlockEnd.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("F block ends at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();

            }
        });

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        GBlockStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        GBlockStart.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("G block starts at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();
            }
        });

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        GBlockEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        GBlockEnd.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("G block ends at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();

            }
        });

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        HBlockStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        HBlockStart.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("H block starts at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();
            }
        });

        // https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
        HBlockEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = String.format("%s:%s", selectedHour, selectedMinute);
                        Log.d("SettingCustomTime", time);
                        HBlockEnd.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("H block ends at:");
                // https://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button
                // https://stackoverflow.com/questions/3125879/how-to-make-a-listener-for-a-datepickers-cancel-button-in-android
                mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do stuff
                            }
                        });
                mTimePicker.show();

            }
        });

        NoABlock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ABlockStart.setEnabled(!isChecked);
                ABlockEnd.setEnabled(!isChecked);
            }
        });

        NoBBlock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                BBlockStart.setEnabled(!isChecked);
                BBlockEnd.setEnabled(!isChecked);
            }
        });

        NoCBlock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CBlockStart.setEnabled(!isChecked);
                CBlockEnd.setEnabled(!isChecked);
            }
        });

        NoDBlock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DBlockStart.setEnabled(!isChecked);
                DBlockEnd.setEnabled(!isChecked);
            }
        });

        NoLunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LunchStart.setEnabled(!isChecked);
                LunchEnd.setEnabled(!isChecked);
            }
        });

        NoEBlock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EBlockStart.setEnabled(!isChecked);
                EBlockEnd.setEnabled(!isChecked);
            }
        });

        NoFBlock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FBlockStart.setEnabled(!isChecked);
                FBlockEnd.setEnabled(!isChecked);
            }
        });

        NoGBlock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GBlockStart.setEnabled(!isChecked);
                GBlockEnd.setEnabled(!isChecked);
            }
        });

        NoHBlock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HBlockStart.setEnabled(!isChecked);
                HBlockEnd.setEnabled(!isChecked);
            }
        });

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setTitle("Create custom schedule");
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        // Create an array of times for classes
                        ArrayList<ClassTime> classes = new ArrayList<>();

                        // Get the custom regime times
                        if (!NoABlock.isChecked()) {
                            ClassTime clss = new ClassTime();
                            clss.block = Block.ANormal;
                            clss.startTime = ABlockStart.getText().toString();
                            clss.endTime = ABlockEnd.getText().toString();
                            classes.add(clss);
                        }

                        if (!NoBBlock.isChecked()) {
                            ClassTime clss = new ClassTime();
                            clss.block = Block.BNormal;
                            clss.startTime = BBlockStart.getText().toString();
                            clss.endTime = BBlockEnd.getText().toString();
                            classes.add(clss);
                        }

                        if (!NoCBlock.isChecked()) {
                            ClassTime clss = new ClassTime();
                            clss.block = Block.CNormal;
                            clss.startTime = CBlockStart.getText().toString();
                            clss.endTime = CBlockEnd.getText().toString();
                            classes.add(clss);
                        }

                        if (!NoDBlock.isChecked()) {
                            ClassTime clss = new ClassTime();
                            clss.block = Block.DNormal;
                            clss.startTime = DBlockStart.getText().toString();
                            clss.endTime = DBlockEnd.getText().toString();
                            classes.add(clss);
                        }

                        if (!NoLunch.isChecked()) {
                            ClassTime clss = new ClassTime();
                            clss.block = Block.LunchNormal;
                            clss.startTime = LunchStart.getText().toString();
                            clss.endTime = LunchEnd.getText().toString();
                            classes.add(clss);
                        }

                        if (!NoEBlock.isChecked()) {
                            ClassTime clss = new ClassTime();
                            clss.block = Block.ENormal;
                            clss.startTime = EBlockStart.getText().toString();
                            clss.endTime = EBlockEnd.getText().toString();
                            classes.add(clss);
                        }

                        if (!NoFBlock.isChecked()) {
                            ClassTime clss = new ClassTime();
                            clss.block = Block.FNormal;
                            clss.startTime = FBlockStart.getText().toString();
                            clss.endTime = FBlockEnd.getText().toString();
                            classes.add(clss);
                        }

                        if (!NoGBlock.isChecked()) {
                            ClassTime clss = new ClassTime();
                            clss.block = Block.GNormal;
                            clss.startTime = GBlockStart.getText().toString();
                            clss.endTime = GBlockEnd.getText().toString();
                            classes.add(clss);
                        }

                        if (!NoHBlock.isChecked()) {
                            ClassTime clss = new ClassTime();
                            clss.block = Block.HNormal;
                            clss.startTime = HBlockStart.getText().toString();
                            clss.endTime = HBlockEnd.getText().toString();
                            classes.add(clss);
                        }

                        customRegime.writeCustomRegime(classes);
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

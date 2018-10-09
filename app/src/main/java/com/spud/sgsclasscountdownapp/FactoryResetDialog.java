package com.spud.sgsclasscountdownapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Stephen Ogden on 10/9/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
// https://developer.android.com/guide/topics/ui/dialogs#java
public class FactoryResetDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to restore to default settings? This will close the app")
                .setPositiveButton("Yes, Im sure", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Database database = new Database();
                        database.initialiseDatabase();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

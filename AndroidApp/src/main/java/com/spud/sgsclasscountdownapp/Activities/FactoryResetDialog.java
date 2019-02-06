package com.spud.sgsclasscountdownapp.Activities;

import android.app.AlertDialog;
import android.util.Log;

import java.io.File;

/**
 * Created by Stephen Ogden on 10/9/18.
 */
// https://developer.android.com/guide/topics/ui/dialogs#java
public class FactoryResetDialog extends android.app.DialogFragment {
    @Override
    public android.app.Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to restore to default settings?\nThis will close the app.")
                .setPositiveButton("Yes, Im sure", (dialog, id) -> {
                    // TODO: Remove all the created files
                    File[] filesList = Main.dir.getParentFile().listFiles();
                    for (File file : filesList) {
                        if (file.isFile()) {
                            Log.w("Files to remove", file.getName());
                        }
                    }
                    System.exit(0);
                })
                .setNegativeButton("No", (dialog, id) -> {
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

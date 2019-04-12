package com.spud.sgsclasscountdownapp.Activities;

/**
 * Created by Stephen Ogden on 10/9/18.
 */
// https://developer.android.com/guide/topics/ui/dialogs#java
public class FactoryResetDialog extends android.app.DialogFragment {
	public android.app.Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		return new android.app.AlertDialog.Builder(this.getActivity())
				.setMessage("Are you sure you want to restore to default settings?\nThis will close the app.")
				.setPositiveButton("Yes, Im sure", (dialog, id) -> {
					for (java.io.File file : Main.dir.getParentFile().listFiles()) {
						if (file.isFile()) {
							if (!file.delete()) {
								android.util.Log.w("Cannot delete file", file.getAbsolutePath());
							}
						}
					}
					System.exit(0);
				}).setNegativeButton("No", (dialog, id) -> {
					// Do nothing
				}).create();
	}
}

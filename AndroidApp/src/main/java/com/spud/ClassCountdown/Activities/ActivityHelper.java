package com.spud.ClassCountdown.Activities;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Spud on 2019-07-19 for the project: SGSClassCountdown.
 * <p>
 * For the license, view the file titled LICENSE at the root of the project
 */
class ActivityHelper {

	static void killView(View view) {
		try {

			// Get the views parent
			ViewGroup parent = (ViewGroup) view.getParent();

			// Remove the view from its parent
			parent.removeView(view);

			// Log success
			Log.d("KillView", "Killed child successfully!");

		} catch (NullPointerException NPE) {

			// Log that the view is null
			Log.w("KillView", "View is null!");
			NPE.printStackTrace();
		} catch (Exception e) {

			// Final catch all
			Log.e("KillView", "Unhandled exception!");
			e.printStackTrace();
		}
	}

}

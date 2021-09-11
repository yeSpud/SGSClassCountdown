package com.spud.ClassCountdown.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Spud on 2019-07-19 for the project: SGSClassCountdown.
 * <p>
 * For the license, view the file titled LICENSE at the root of the project
 */
class ActivityHelper {

	/**
	 * TODO Documentation
	 *
	 * @param view
	 */
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

	/**
	 * TODO Documentation
	 *
	 * @param activity
	 * @return
	 */
	static TextView createTextView(AppCompatActivity activity) {
		TextView t = new TextView(activity);
		t.setTextColor(Color.WHITE);
		t.setTextSize(12f);
		t.setGravity(android.view.Gravity.CENTER_VERTICAL);
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		p.setMargins(0, 0, 20, 0);
		t.setLayoutParams(p);
		return t;
	}

}

package com.spud.ClassCountdown.Layout;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Stephen Ogden on 4/8/19.
 */
@Deprecated
public class RegimeLayout extends LinearLayout {

	public TextView title, classCount;

	public Button edit, delete;

	public RegimeLayout(Context context) {
		super(context);

		LinearLayout root = (LinearLayout) this.getRootView();

		this.title = (TextView) root.getChildAt(0);
		this.classCount = (TextView) root.getChildAt(1);
		this.edit = (Button) root.getChildAt(2);
		this.delete = (Button) root.getChildAt(3);

	}


}

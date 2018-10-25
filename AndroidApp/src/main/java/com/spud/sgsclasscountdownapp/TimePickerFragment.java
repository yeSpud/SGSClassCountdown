package com.spud.sgsclasscountdownapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Stephen Ogden on 10/24/18.
 * FTC 6128 | 7935
 * FRC 1595
 */

// https://developer.android.com/guide/topics/ui/controls/pickers#TimePickerFragment
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public Dialog onCreateDialog() {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        // TODO: Set the text fields on the settings page
    }
}
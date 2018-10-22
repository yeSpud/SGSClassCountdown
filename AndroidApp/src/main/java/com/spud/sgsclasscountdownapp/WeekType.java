package com.spud.sgsclasscountdownapp;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by Stephen Ogden on 10/9/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
enum WeekType {
    Weekend,
    Normal,
    Long,
    Custom;

    static WeekType getWeekType() {

        // TODO: Check if override is in place before referring to built-in
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        Log.i("getWeekType", Integer.toString(dayOfWeek));
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return WeekType.Weekend;
            case Calendar.MONDAY:
                return WeekType.Normal;
            case Calendar.TUESDAY:
                return WeekType.Normal;
            case Calendar.WEDNESDAY:
                return WeekType.Long;
            case Calendar.THURSDAY:
                return WeekType.Long;
            case Calendar.FRIDAY:
                return WeekType.Normal;
            case Calendar.SATURDAY:
                return WeekType.Weekend;
            default:
                return WeekType.Weekend;
        }
    }

}
